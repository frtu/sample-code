package com.github.frtu.sample.workflow.temporal.email.activity

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class EmailSinkActivityImpl : EmailSinkActivity {
    @Value("\${application.topic.domain-source}")
    lateinit var outputSource: String

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, Email>

    override fun emit(email: Email) {
        logger.info("Sending to topic:$outputSource message:$email")
        kafkaTemplate.send(outputSource, email)
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}