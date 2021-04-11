package com.github.frtu.coroutine.web

import com.github.frtu.coroutine.persistence.Email
import com.github.frtu.coroutine.persistence.EmailExtendedRepository
import com.github.frtu.coroutine.persistence.EmailRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class EmailRestCoroutinesController(val repository: EmailRepository, val repositoryExtended: EmailExtendedRepository) {
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
    suspend fun suspendingId(@PathVariable id: UUID): Email? {
        return repositoryExtended.getUsingSql(id)
    }

    @GetMapping("/v1/emails/after/{id}")
    suspend fun suspendingAfterId(@PathVariable id: UUID): Flow<Email> {
        return repository.afterThisId(id)
    }
}