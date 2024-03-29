package com.github.frtu.sample.integration

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.integration.config.EnableIntegration

@EnableConfigurationProperties(AppProperties::class)
@SpringBootApplication
@EnableIntegration
//@EnableR2dbcRepositories
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
    runApplication<Application>(*args)
}