package com.github.frtu.mail.service

import com.github.frtu.mail.model.Email
import com.github.frtu.mail.model.EmailRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class EmailService(val emailRepository: EmailRepository) {
    fun search(text: String, pageRequest: PageRequest): Page<Email> {
        val entities = emailRepository.search(text, pageRequest)
        val resultList = entities.content
        return PageImpl(resultList, entities.pageable, entities.totalElements)
    }
}