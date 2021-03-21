package com.github.frtu.sample.service

import com.github.frtu.logs.config.LogConfigAll
import com.github.frtu.sample.service.client.EventClientConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(LogConfigAll::class)
@EnableConfigurationProperties(EventClientConfig.ServiceCall::class)
@SpringBootApplication
//@EnableR2dbcRepositories
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}