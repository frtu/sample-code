package com.github.frtu.sample.workflow.temporal.email

import com.github.frtu.sample.workflow.temporal.email.activity.Email
import com.github.frtu.sample.workflow.temporal.email.activity.EmailSinkActivityImpl
import com.github.frtu.sample.workflow.temporal.email.config.TemporalConfig
import java.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(TemporalConfig::class)
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
    System.getProperties()["server.port"] = 8083;
    runApplication<Application>(*args)
}