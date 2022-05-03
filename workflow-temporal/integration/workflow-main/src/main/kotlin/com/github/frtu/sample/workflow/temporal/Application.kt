package com.github.frtu.sample.workflow.temporal

import com.github.frtu.sample.workflow.temporal.domain.SubscriptionEvent
import com.github.frtu.sample.workflow.temporal.domain.SubscriptionHandler
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@EnableConfigurationProperties(AppProperties::class)
@SpringBootApplication
class Application {
    @Bean
    fun initializer(subscriptionHandler: SubscriptionHandler): CommandLineRunner =
        CommandLineRunner { args: Array<String?> ->
            subscriptionHandler.handle(SubscriptionEvent("message"))
        }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}