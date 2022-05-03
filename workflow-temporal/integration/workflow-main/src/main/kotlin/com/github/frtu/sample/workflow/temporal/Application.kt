package com.github.frtu.sample.workflow.temporal

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.context.annotation.Bean

@EnableConfigurationProperties(AppProperties::class)
@SpringBootApplication
@EnableBinding(Sink::class)
//@EnableR2dbcRepositories
class Application {
    @StreamListener(Sink.INPUT)
    fun handle(message: String) {
        println("Received: $message")
    }

    @Bean
    fun initializer(): CommandLineRunner =
        CommandLineRunner { args: Array<String?> ->
            for (arg in args) {
                logger.info(arg)
            }
        }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}