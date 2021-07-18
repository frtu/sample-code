package com.github.frtu.sample.resilience.ratelimiter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(AppProperties::class)
@SpringBootApplication
//@EnableR2dbcRepositories
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}