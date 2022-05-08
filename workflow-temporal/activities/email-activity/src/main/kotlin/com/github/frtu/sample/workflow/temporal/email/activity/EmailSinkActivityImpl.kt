package com.github.frtu.sample.workflow.temporal.email.activity

import com.github.frtu.logs.core.StructuredLogger
import com.github.frtu.logs.core.StructuredLogger.key
import com.github.frtu.logs.core.StructuredLogger.message
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
        structuredLogger.info(key("topic", outputSource), message("Sending message:$email"))
        kafkaTemplate.send(outputSource, email)
    }

    private val structuredLogger = StructuredLogger.create(this::class.java)
}