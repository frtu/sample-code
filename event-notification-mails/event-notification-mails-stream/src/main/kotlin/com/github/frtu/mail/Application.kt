package com.github.frtu.mail

import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import com.github.frtu.logs.config.LogConfigAll
import com.github.frtu.mail.model.Email
import com.github.frtu.mail.model.EmailRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.cloud.stream.annotation.EnableBinding
import java.util.*

@Import(LogConfigAll::class)
@EnableConfigurationProperties(AppProperties::class)
@SpringBootApplication
@EnableJpaAuditing
//@EnableR2dbcRepositories
@EnableBinding(AppProcessor::class)
class Application {
    @Bean
    fun init(repository: EmailRepository): CommandLineRunner {
        return CommandLineRunner { args: Array<String?>? ->
            repository.save(Email(UUID.randomUUID(), "rndfred@163.com", "subject",
                "<html><body>Here is a cat picture! <img src='cid:id101'/><body></html>", "INIT"))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}