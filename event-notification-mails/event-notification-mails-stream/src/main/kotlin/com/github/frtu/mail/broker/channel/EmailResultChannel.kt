package com.github.frtu.mail.channel

import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel

interface EmailResultChannel {
    companion object {
        const val EMAIL_RESULT = "email-result"
    }

    @Output(EMAIL_RESULT)
    fun outputEmailSource(): MessageChannel

    @Input(EMAIL_RESULT)
    fun inputEmailSource(): SubscribableChannel
}