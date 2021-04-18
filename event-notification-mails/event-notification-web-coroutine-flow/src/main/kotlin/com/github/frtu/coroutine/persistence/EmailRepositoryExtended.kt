package com.github.frtu.coroutine.persistence

import com.github.frtu.persistence.coroutine.query.Query
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
    suspend fun findById(id: UUID): Email? {
        val query = Query.query(Criteria.where("id").`is`(id))

        // Build the SQL
        val bindedExecuteSpec = client.execute("SELECT * FROM email ${query.build()}").let {
            // Bind the parameters => ATTENTION MAKE SURE TO USE THE NEWLY CREATED AND RETURNED bindedExecuteSpec
            query.bind(it)
        }
        // Regular retrieve & map object
        return bindedExecuteSpec.`as`(Email::class.java)
            .fetch()
            .one()
            .awaitFirstOrNull()
    }

    suspend fun getUsingSql(id: UUID): Email? =
        client.execute("SELECT * FROM email WHERE id = $1")
            .bind(0, id)
            .`as`(Email::class.java)
            .fetch()
            .one()
            .awaitFirstOrNull()
}