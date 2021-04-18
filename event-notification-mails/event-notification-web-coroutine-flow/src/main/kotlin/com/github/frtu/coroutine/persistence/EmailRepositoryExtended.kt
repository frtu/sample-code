package com.github.frtu.coroutine.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.frtu.coroutine.persistence.Email.Companion.TABLE_NAME
import com.github.frtu.persistence.coroutine.query.Query
import com.sun.tools.javac.comp.Flow
import kotlinx.coroutines.reactive.awaitFirstOrNull
// => Should be using R2dbcEntityTemplate & DatabaseClient since spring-boot 2.4 (and spring 5.3 and spring-data-r2dbc 1.2)
// But keeping retro compatibility with old framework
// import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.relational.core.query.Criteria
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class EmailExtendedRepository(private val client: DatabaseClient) {
    suspend fun count(): Long? =
        client.execute("SELECT COUNT(*) FROM $TABLE_NAME").`as`(Long::class.java)
            .fetch().one()
            .awaitFirstOrNull()

    suspend fun findById(id: UUID): Email? {
        val query = Query.query(Criteria.where("id").`is`(id))

        // Build the SQL
        val bindedExecuteSpec = client.execute("SELECT * FROM $TABLE_NAME ${query.build()}").let {
            // Bind the parameters => ATTENTION MAKE SURE TO USE THE NEWLY CREATED AND RETURNED bindedExecuteSpec
            query.bind(it)
        }
        // Regular retrieve & map object
        return bindedExecuteSpec.`as`(Email::class.java)
            .fetch().one()
            .awaitFirstOrNull()
    }

    suspend fun findAll(): Flow<Email> =
        client.select().from(TABLE_NAME).`as`(Email::class.java)
            .fetch().all()
            .asFlow()

    suspend fun save(entity: Email): UUID {
        val objectMapper = ObjectMapper()
        val id = entity.id
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