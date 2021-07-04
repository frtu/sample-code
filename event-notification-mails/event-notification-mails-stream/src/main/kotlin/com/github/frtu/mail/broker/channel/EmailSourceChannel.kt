package com.github.frtu.mail.broker.channel

import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel

interface EmailSourceChannel {
    companion object {
        const val EMAIL_SOURCE = "email-source"
    }

    @Output(EMAIL_SOURCE)
    fun outputEmailSource(): MessageChannel

    @Input(EMAIL_SOURCE)
    fun inputEmailSource(): SubscribableChannel
}