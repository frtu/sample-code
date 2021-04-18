package com.github.frtu.coroutine.persistence

import com.github.frtu.coroutine.exception.DataNotExist
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
        val criteriaIterator = searchParams.filter { it.key != null && it.value != null }
            .map { where("data->>'${it.key}'").`is`(it.value) }
            .asSequence().iterator()

        var criteria : Criteria? = null
        if (criteriaIterator.hasNext()) {
            criteria = criteriaIterator.next()
            while (criteriaIterator.hasNext()) {
                criteria = criteria?.and(criteriaIterator.next())
            }
        }
        return template
            .select(Email::class.java)
            .matching(Query.query(criteria!!))
            .all().asFlow()
    }

    suspend fun findById(id: UUID): Email? = template
        .selectOne(
            Query.query(where("id").`is`(id)), Email::class.java
        ).awaitFirstOrNull() ?: throw DataNotExist(id.toString())

    suspend fun update(id: UUID, email: Email): UUID? = template
        .update(
            Query.query(where("id").`is`(id)),
            Update.update("email", "rndfred@163.com"),
            Email::class.java
        )
        .map { email.id }
        .awaitFirstOrNull() ?: throw DataNotExist(id.toString())

    suspend fun save(email: Email): UUID? = template
        .insert(Email::class.java)
        .using(email)
        .map { email.id }
        .awaitFirstOrNull()

    suspend fun deleteById(id: UUID): Int? = template
        .delete(
            Query.query(where("id").`is`(id)), Email::class.java
        ).awaitFirstOrNull() ?: throw DataNotExist(id.toString())
}