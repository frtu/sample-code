package com.github.frtu.sample.integration.service

import com.github.frtu.sample.integration.config.KafkaConfiguration
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

@Service
class MessageConsumer {
    @ServiceActivator(inputChannel = KafkaConfiguration.KAFKA_INPUT)
    fun print(message: Message<*>) {
        println("Received: ${message.payload}")
    }
}