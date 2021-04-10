package com.github.frtu.mail.service

import com.github.frtu.mail.model.Email
import com.github.frtu.mail.model.EmailRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/emails")
class EmailController(private val emailService: EmailService, private val repository: EmailRepository) {
    @GetMapping
    fun findAll() = repository.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID) =
        repository.findById(id) //.orElseThrow { IllegalArgumentException("id:[$id] doesn't exist!") }

}