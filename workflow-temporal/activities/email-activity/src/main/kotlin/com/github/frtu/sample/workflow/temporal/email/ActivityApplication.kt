package com.github.frtu.sample.workflow.temporal.email

import com.github.frtu.sample.workflow.temporal.activity.email.Email
import com.github.frtu.sample.workflow.temporal.activity.email.EmailSinkActivityImpl
import java.util.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {
    @Bean
    fun initializer(emailSinkActivityImpl: EmailSinkActivityImpl): CommandLineRunner = CommandLineRunner {
        val subscriptionId = UUID.randomUUID()
        emailSinkActivityImpl.emit(
            Email(
                subject = "Confirmation of Subscription ID : $subscriptionId",
                content = "data",
                id = subscriptionId,
            )
        )
    }
}

fun main(args: Array<String>) {
    System.getProperties()["server.port"] = 18080;
    runApplication<Application>(*args)
}