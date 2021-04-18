package com.github.frtu.coroutine.web

import com.github.frtu.coroutine.persistence.Email
import com.github.frtu.coroutine.persistence.EmailExtendedRepository
import com.github.frtu.coroutine.persistence.EmailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.select
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class EmailRestCoroutinesController(
    val repository: EmailRepository,
    val template: R2dbcEntityTemplate,
    val repositoryExtended: EmailExtendedRepository
) {
    @GetMapping("/v1/emails")
    suspend fun suspendingEndpointAll(): Flow<Email> {
//        return listOf(
//            Email(
//                UUID.randomUUID(), "rndfred@gmail.com", "Mail subject",
//                "Lorem ipsum dolor sit amet.", "SENT"
//            )
//        ).asFlow()
        return repository.findAll()
    }

    @GetMapping("/v1/emails/{id}")
    suspend fun suspendingId(@PathVariable id: UUID): Email? =
//        template.select(Email::class.java)
//        .matching(Query.query(
//            where("id").`is`(id).and(
//                where("status").`is`("INIT"))
//            )
//        )
//        .first().awaitFirstOrNull()
        repositoryExtended.findById(id)

    @GetMapping("/v1/emails/after/{id}")
    suspend fun suspendingAfterId(@PathVariable id: UUID): Flow<Email> {
        return repository.afterThisId(id)
    }
}