package com.github.frtu.mail.service

import com.github.frtu.mail.model.EmailRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/emails")
class EmailController(private val repository: EmailRepository) {
    @GetMapping
    fun findAll() = repository.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID) =
        repository.findById(id).orElseThrow { IllegalArgumentException("id:[$id] doesn't exist!") }
}