package test

import com.github.frtu.sample.workflow.temporal.subscription.config.AllSubscriptionConfig
import com.github.frtu.sample.workflow.temporal.subscription.domain.SubscriptionEvent
import com.github.frtu.sample.workflow.temporal.subscription.domain.service.SubscriptionHandler
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(AllSubscriptionConfig::class)
class SubscriptionWorkflowApplication {
    @Bean
    fun initializer(subscriptionHandler: SubscriptionHandler): CommandLineRunner = CommandLineRunner {
        subscriptionHandler.handle(SubscriptionEvent("message"))
    }
}

fun main(args: Array<String>) {
    runApplication<SubscriptionWorkflowApplication>(*args)
}