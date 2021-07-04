package com.github.frtu.mail.broker

import com.github.frtu.mail.broker.consumer.MessageConsumer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ApplicationChannel {
    @Bean
    fun consumer(): MessageConsumer = MessageConsumer()
}

fun main(args: Array<String>) {
    runApplication<ApplicationChannel>(*args)
}
