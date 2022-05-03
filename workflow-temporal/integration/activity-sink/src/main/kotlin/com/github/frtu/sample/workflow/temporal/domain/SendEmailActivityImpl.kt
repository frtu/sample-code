package com.github.frtu.sample.workflow.temporal.domain

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

import com.github.frtu.sample.workflow.temporal.activity.Email
import com.github.frtu.sample.workflow.temporal.activity.SendEmailActivity
import java.util.*

@Service
class SendEmailActivityImpl : SendEmailActivity {
    @Value("\${application.topic.domain-source}")
    lateinit var outputSource: String

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, String>

    fun send(message: String) {
        emit(Email(data = message, identity = UUID.randomUUID()))
    }

    override fun emit(email: Email) {
        logger.info("Sending to topic:$outputSource message:$email")
        kafkaTemplate.send(outputSource, email.data)
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}