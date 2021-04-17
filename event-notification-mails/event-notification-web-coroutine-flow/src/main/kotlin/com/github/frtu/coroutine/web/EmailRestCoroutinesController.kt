package com.github.frtu.coroutine.web

import com.github.frtu.coroutine.persistence.Email
import com.github.frtu.coroutine.persistence.EmailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.data.relational.core.query.Update.update
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class EmailRestCoroutinesController(
    val repository: EmailRepository,
    val template: R2dbcEntityTemplate
) {
    @GetMapping("/v1/emails")
    suspend fun findAll(): Flow<Email> = template
        .select(Email::class.java)
        .all().asFlow()

    @GetMapping("/v1/emails/{id}")
    suspend fun findById(@PathVariable id: UUID): Email? = template
        .selectOne(
            query(where("id").`is`(id)), Email::class.java
        ).awaitFirstOrNull()

    @PostMapping("/v1/emails")
    suspend fun save(email: Email): UUID? = template
        .insert(Email::class.java)
        .using(email)
        .map { email.id }
        .awaitFirstOrNull()

    @GetMapping("/v1/emails/after/{id}")
    suspend fun suspendingAfterId(@PathVariable id: UUID): Flow<Email> {
        return repository.afterThisId(id)
    }
}