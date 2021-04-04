package com.github.frtu.sample.service

import com.github.frtu.logs.config.LogConfigAll
import com.github.frtu.sample.service.events.entity.Event
import com.github.frtu.sample.service.events.entity.EventRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Import(LogConfigAll::class)
//@EnableConfigurationProperties(AppProperties::class)
@SpringBootApplication
@EnableJpaAuditing
//@EnableR2dbcRepositories
class Application {
//    @Bean
//    fun init(repository: EventRepository): CommandLineRunner? {
//        return CommandLineRunner { args: Array<String?>? ->
//            repository.save(Event("key", 1.0F, UUID.randomUUID().toString()))
//        }
//    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}