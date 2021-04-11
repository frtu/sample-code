package com.github.frtu.coroutine.persistence

import kotlinx.coroutines.reactive.awaitFirstOrNull
// => Should be using R2dbcEntityTemplate & DatabaseClient since spring-boot 2.4 (and spring 5.3 and spring-data-r2dbc 1.2)
// But keeping retro compatibility with old framework
// import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class EmailExtendedRepository(private val client: DatabaseClient) {
    suspend fun getUsingSql(id: UUID): Email? =
        client.execute("SELECT * FROM email WHERE id = $1")
            .bind(0, id)
            .`as`(Email::class.java)
            .fetch()
            .one()
            .awaitFirstOrNull()
}