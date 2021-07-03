package com.github.frtu.coroutine.persistence.backport

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.frtu.coroutine.persistence.Email
import com.github.frtu.coroutine.persistence.Email.Companion.TABLE_NAME
import io.r2dbc.spi.Statement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
// => Should be using R2dbcEntityTemplate & DatabaseClient since spring-boot 2.4 (and spring 5.3 and spring-data-r2dbc 1.2)
// But keeping retro compatibility with old framework
//import org.springframework.r2dbc.core.DatabaseClient
//import org.springframework.r2dbc.core.ConnectionAccessor
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.ConnectionAccessor
import org.springframework.stereotype.Repository
import java.util.*
import reactor.core.publisher.Flux

/**
 * Work with the previous DatabaseClient, but cannot get instance using the new version
 */
//@Repository
class EmailRepositoryDatabaseClient(
    private val client: DatabaseClient,
    private val connectionAccessor: ConnectionAccessor
) {
    companion object {
        val objectMapper = ObjectMapper()
    }

    suspend fun count(): Long? =
        client.execute("SELECT COUNT(*) FROM $TABLE_NAME").`as`(Long::class.java)
            .fetch().one()
            .awaitFirstOrNull()

    suspend fun findById(id: UUID): Email? {
        // Build the SQL
        val bindedExecuteSpec = client.execute("SELECT * FROM $TABLE_NAME")
        // Regular retrieve & map object
        return bindedExecuteSpec.`as`(Email::class.java)
            .fetch().one()
            .awaitFirstOrNull()
    }

    suspend fun findAll(): Flow<Email> =
        client.select().from(TABLE_NAME).`as`(Email::class.java)
            .fetch().all()
            .asFlow()

    // see: https://github.com/spring-projects/spring-data-r2dbc/issues/259
    // and https://stackoverflow.com/questions/62514094/how-to-execute-multiple-inserts-in-batch-in-r2dbc
    suspend fun saveAll(data: List<Email>): Flux<UUID> {
        return connectionAccessor.inConnectionMany { connection ->
            val statement: Statement = connection.createStatement(
                """INSERT INTO $TABLE_NAME 
                                (id, creation_time, "data") 
                                VALUES(:id, :creation_time, :data::JSON)
                """.trimMargin()
            ).returnGeneratedValues("id")

            for (entity in data) {
                val id = entity.identity!!
                statement
                    .bind("id", id)
                    .bind("creation_time", entity.creationTime)
                    .bind("data", entity.data)
                    .add()
            }

            Flux.from(statement.execute())
                .flatMap { result ->
                    result.map { row, rowMetadata ->
                        row.get(
                            "id",
                            UUID::class.java
                        )
                    }
                }
        }
    }

    suspend fun save(entity: Email): UUID {
        val id = entity.identity!!
        client.execute(
            """INSERT INTO $TABLE_NAME 
                            (id, creation_time, "data") 
                            VALUES(:id, :creation_time, :data::JSON)
            """.trimMargin()
        )
            .bind("id", id)
            .bind("creation_time", entity.creationTime)
            .bind("data", objectMapper.writeValueAsString(entity))
            .fetch().rowsUpdated()
            .awaitFirstOrNull()
        return id
    }

    suspend fun getUsingSql(id: UUID): Email? =
        client.execute("SELECT * FROM email WHERE id = $1")
            .bind(0, id)
            .`as`(Email::class.java)
            .fetch()
            .one()
            .awaitFirstOrNull()
}