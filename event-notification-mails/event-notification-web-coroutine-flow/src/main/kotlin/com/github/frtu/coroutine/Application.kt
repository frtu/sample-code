package com.github.frtu.coroutine

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.frtu.coroutine.persistence.Email
import com.github.frtu.coroutine.persistence.EmailDetail
import com.github.frtu.coroutine.persistence.EmailRepository
import com.github.frtu.coroutine.web.EmailRestCoroutinesController
import com.github.frtu.logs.config.LogConfigAll
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.mapping.Column
import java.util.*

@Import(LogConfigAll::class)
@EnableConfigurationProperties(AppProperties::class)
@SpringBootApplication
class Application {
    @Bean
    fun init(service: EmailRestCoroutinesController, repository: EmailRepository, template: R2dbcEntityTemplate): CommandLineRunner {
        val objectMapper = ObjectMapper()
        return CommandLineRunner { args: Array<String?>? ->
            runBlocking {
                println(
                    service.save(
                        Email(
                            objectMapper.writeValueAsString(
                                EmailDetail(
                                    "rndfred@gmail.com", "Mail subject",
                                    "Lorem ipsum dolor sit amet.", "SENT"
                                )
                            ),
                            UUID.randomUUID()
                        )
                    )
                )
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
