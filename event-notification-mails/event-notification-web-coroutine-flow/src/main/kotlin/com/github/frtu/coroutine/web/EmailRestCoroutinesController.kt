package com.github.frtu.coroutine.web

import com.github.frtu.coroutine.persistence.EmailRepository
import com.github.frtu.coroutine.persistence.Email
import com.github.frtu.coroutine.persistence.EmailDetail
import com.github.frtu.coroutine.persistence.IEmailRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class EmailRestCoroutinesController(
    val repository: IEmailRepository,
    val emailRepository: EmailRepository
) {
//    @GetMapping("/v1/emails")
//    suspend fun findAll(): Flow<Email> = emailService.findAll()

    @GetMapping("/v1/emails")
    suspend fun searchByQueryParams(@RequestParam searchParams: Map<String, String>): Flow<Email> =
        emailRepository.findAll(searchParams)

    @GetMapping("/v1/emails/{id}")
    suspend fun findById(@PathVariable id: UUID): Email? = emailRepository.findById(id)

    @PutMapping("/v1/emails/{id}")
    suspend fun update(@RequestBody email: EmailDetail, @PathVariable id: UUID): UUID? =
        emailRepository.update(id, email)

    @PostMapping("/v1/emails")
    suspend fun save(@RequestBody email: EmailDetail): UUID? = emailRepository.save(email)

    @DeleteMapping("/v1/emails/{id}")
    suspend fun deleteById(@PathVariable id: UUID): Int? = emailRepository.deleteById(id)

    @GetMapping("/v1/emails/after/{id}")
    suspend fun suspendingAfterId(@PathVariable id: UUID): Flow<Email> {
        return repository.afterThisId(id)
    }
}

