package com.github.frtu.coroutine.persistence

import com.github.frtu.coroutine.persistence.backport.EmailRepositoryDatabaseClient
import io.r2dbc.spi.Statement
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.util.*

@Repository
class EmailRepositoryBatch(private val client: DatabaseClient) {
    suspend fun count(): String? =
        client.sql("SELECT COUNT(*) FROM ${Email.TABLE_NAME}")
            .fetch()
            .one()
            .map { row -> row[0].toString() }
            .awaitFirstOrNull()

    // see: https://github.com/spring-projects/spring-data-r2dbc/issues/259
    // and https://stackoverflow.com/questions/62514094/how-to-execute-multiple-inserts-in-batch-in-r2dbc
    suspend fun saveAll(data: List<Email>): Flux<UUID> {
        return client.inConnectionMany { connection ->
            val statement: Statement = connection.createStatement(
                """INSERT INTO ${Email.TABLE_NAME} 
                                (id, creation_time, "data") 
                                VALUES($1, $2, $3::JSON)
                """.trimMargin()
            ).returnGeneratedValues("safasaid")

            for (entity in data) {
                val id = entity.identity!!
                statement
                    .bind(0, id)
                    .bind(1, entity.creationTime)
                    .bind(2, EmailRepositoryDatabaseClient.objectMapper.writeValueAsString(entity))
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
}