package com.github.frtu.mail.broker

import org.springframework.integration.support.MessageBuilder
import org.springframework.messaging.Message
import org.springframework.stereotype.Service
import reactor.core.publisher.Sinks

@Service
class MessageProducerService {
    private val unicastProcessor = Sinks.many().unicast().onBackpressureBuffer<Message<DummyBean>>()

    fun getProducer() = unicastProcessor

    suspend fun sendMessage(messageBean: DummyBean) {
        val message = MessageBuilder.withPayload(messageBean).build()
        unicastProcessor.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST)
    }
}