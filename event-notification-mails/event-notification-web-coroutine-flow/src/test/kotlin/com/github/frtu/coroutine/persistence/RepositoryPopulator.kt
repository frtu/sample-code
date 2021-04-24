package com.github.frtu.coroutine.persistence

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.frtu.coroutine.persistence.backport.EmailRepositoryDatabaseClient
import com.github.frtu.persistence.r2dbc.configuration.JsonR2dbcConfiguration
import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.PreviousDatabaseClientBuilderFactory
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
// DEPRECATED in spring-data:1.2.x
//import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager
//import org.springframework.data.r2dbc.connectionfactory.init.CompositeDatabasePopulator
//import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer
//import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator
import java.time.LocalDateTime
import java.util.*

@Configuration
@EnableR2dbcRepositories
class RepositoryPopulatorConfig : JsonR2dbcConfiguration() {
//    @Bean
//    fun previousDatabaseClient(): DatabaseClient {
//        return PreviousDatabaseClientBuilderFactory().build()
//    }

    @Bean
    fun initDatabase(repository: EmailRepository): CommandLineRunner {
        val objectMapper = ObjectMapper()
        return CommandLineRunner { args: Array<String?>? ->
            runBlocking {
                val dateTime = LocalDateTime.now()
                for (days in 0..24 step 2) {
                    println(
                        repository.save(
                            Email(
                                EmailDetail(
                                    "rndfred@163.com", "Mail subject",
                                    "Lorem ipsum dolor sit amet.", "SENT"
                                ),
                                UUID.randomUUID(),
                                dateTime.plusDays(days.toLong())
                            )
                        )
                    )
                }
            }
        }
    }

    @Bean
    fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        val populator = CompositeDatabasePopulator()
        populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("./db/migration/V0_1_0__table-email.sql")))
        populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("./db/migration/V0_1_1__partition-email.sql")))
        initializer.setDatabasePopulator(populator)
        return initializer
    }
}

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
