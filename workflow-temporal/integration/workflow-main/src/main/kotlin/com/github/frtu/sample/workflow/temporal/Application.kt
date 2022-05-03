package com.github.frtu.sample.workflow.temporal

import com.github.frtu.sample.workflow.temporal.service.MessageConsumer
import io.temporal.client.WorkflowClient
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.worker.WorkerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.context.annotation.Bean

@EnableConfigurationProperties(AppProperties::class)
@SpringBootApplication
@EnableBinding(Sink::class)
//@EnableR2dbcRepositories
class Application {
    @Bean
    fun initializer(messageConsumer: MessageConsumer): CommandLineRunner = CommandLineRunner { args: Array<String?> ->
        messageConsumer.message("message")
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}