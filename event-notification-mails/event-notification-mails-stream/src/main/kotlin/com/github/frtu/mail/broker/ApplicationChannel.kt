package com.github.frtu.mail.broker

import com.github.frtu.mail.broker.consumer.MessageConsumer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.IntegrationComponentScan
import org.springframework.integration.config.EnableIntegration

@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
class ApplicationChannel {
    @Bean
    fun consumer(): MessageConsumer = MessageConsumer()
}

fun main(args: Array<String>) {
    runApplication<ApplicationChannel>(*args)
}
