package com.github.frtu.mail

import com.github.frtu.mail.channel.EmailResultChannel
import com.github.frtu.mail.channel.EmailSourceChannel
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener


@SpringBootApplication
@EnableBinding(EmailSourceChannel::class, EmailResultChannel::class)
class ApplicationChannel {
    @StreamListener(EmailSourceChannel.EMAIL_SOURCE)
    fun handle(message: String) {
        println("Received: $message")
    }
}

fun main(args: Array<String>) {
    runApplication<ApplicationChannel>(*args)
}