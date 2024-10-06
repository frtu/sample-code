package com.github.frtu.sample.workflow.temporal.subscription

import com.github.frtu.sample.workflow.temporal.subscription.domain.SubscriptionEvent
import com.github.frtu.sample.workflow.temporal.subscription.domain.service.SubscriptionHandler
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