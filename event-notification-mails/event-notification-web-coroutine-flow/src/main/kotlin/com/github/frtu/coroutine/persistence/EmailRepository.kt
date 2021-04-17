package com.github.frtu.coroutine.persistence

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.data.relational.core.query.Update
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class EmailRepository(val template: R2dbcEntityTemplate) {
    suspend fun findAll(): Flow<Email> = template
        .select(Email::class.java)
        .all().asFlow()

    suspend fun findAll(searchParams: Map<String, String>): Flow<Email> {
        val criteria = where("data->>'receiver'").`is`(searchParams["receiver"]!!)
        return template
            .select(Email::class.java)
            .matching(Query.query(criteria))
            .all().asFlow()
    }

    suspend fun findById(id: UUID): Email? = template
        .selectOne(
            Query.query(Criteria.where("id").`is`(id)), Email::class.java
        ).awaitFirstOrNull()

    suspend fun update(id: UUID, email: Email): UUID? = template
        .update(
            Query.query(Criteria.where("id").`is`(id)),
            Update.update("email", "rndfred@163.com"),
            Email::class.java
        )
        .map { email.id }
        .awaitFirstOrNull()

    suspend fun save(email: Email): UUID? = template
        .insert(Email::class.java)
        .using(email)
        .map { email.id }
        .awaitFirstOrNull()

    suspend fun deleteById(id: UUID): Int? = template
        .delete(
            Query.query(Criteria.where("id").`is`(id)), Email::class.java
        ).awaitFirstOrNull()
}