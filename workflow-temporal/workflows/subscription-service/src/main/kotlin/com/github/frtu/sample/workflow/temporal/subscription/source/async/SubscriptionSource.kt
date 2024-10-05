package com.github.frtu.sample.workflow.temporal.subscription.source.async

import com.github.frtu.logs.core.RpcLogger.requestBody
import com.github.frtu.logs.core.StructuredLogger
import com.github.frtu.sample.workflow.temporal.subscription.config.KafkaConfiguration
import com.github.frtu.sample.workflow.temporal.subscription.domain.SubscriptionEvent
import com.github.frtu.sample.workflow.temporal.subscription.domain.service.SubscriptionHandler
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

@Service
class SubscriptionSource(private val subscriptionHandler: SubscriptionHandler) {
    @ServiceActivator(inputChannel = KafkaConfiguration.KAFKA_INPUT)
    fun message(message: Message<*>) {
        structuredLogger.debug(requestBody(message))
        subscriptionHandler.handle(SubscriptionEvent(message.payload.toString()))
    }

    private val structuredLogger = StructuredLogger.create(this::class.java)
}