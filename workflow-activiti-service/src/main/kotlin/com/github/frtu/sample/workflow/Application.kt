package com.github.frtu.sample.workflow

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {
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
    SpringApplication.run(Application::class.java, *args)
}