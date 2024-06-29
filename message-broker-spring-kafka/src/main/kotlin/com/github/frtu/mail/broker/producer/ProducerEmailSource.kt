package com.github.frtu.mail.broker.producer

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class ProducerEmailSource {
    @Value("\${application.channel.email-source.topic}")
    lateinit var outputSource: String

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, ByteArray>

    fun send(message: String) {
        logger.info("Sending to topic:$outputSource message:$message")
        kafkaTemplate.send(outputSource, message.toByteArray())
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}