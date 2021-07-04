package com.github.frtu.mail.broker.consumer

import com.github.frtu.mail.broker.channel.EmailResultChannel
import com.github.frtu.mail.broker.channel.EmailSourceChannel
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Service

@Service
@EnableBinding(EmailSourceChannel::class, EmailResultChannel::class)
class MessageConsumer {
    @StreamListener(EmailSourceChannel.EMAIL_SOURCE)
    fun handle(message: String) {
        println("Received: $message")
    }
}