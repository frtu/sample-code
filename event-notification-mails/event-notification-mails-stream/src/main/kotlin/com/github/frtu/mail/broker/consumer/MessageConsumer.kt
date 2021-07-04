package com.github.frtu.mail.broker.consumer

import com.github.frtu.mail.broker.channel.EmailResultChannel
import com.github.frtu.mail.broker.channel.EmailSourceChannel
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Service

@Service
@EnableBinding(EmailSourceChannel::class, EmailResultChannel::class)
class MessageConsumer {
    @StreamListener(EmailSourceChannel.EMAIL_SOURCE)
    fun handle(message: String) {
        logger.info("Received: $message type:${message.javaClass}")
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}