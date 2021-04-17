package com.github.frtu.coroutine.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.frtu.coroutine.persistence.Email
import com.github.frtu.coroutine.persistence.EmailDetail
import com.github.frtu.coroutine.persistence.EmailRepository
import com.github.frtu.persistence.coroutine.configuration.JsonR2dbcConfiguration
import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*

@Configuration
@EnableR2dbcRepositories
@EnableTransactionManagement
class R2dbcDatabaseConfig : JsonR2dbcConfiguration() {
    @Bean
    fun transactionManager(connectionFactory: ConnectionFactory): ReactiveTransactionManager {
        return R2dbcTransactionManager(connectionFactory)
    }

    @Bean
    fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        val populator = CompositeDatabasePopulator()
        populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("./db/migration/V0_1_0__table-email.sql")))
//        populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("./db/migration/V0_1_1__data-email.sql")))
        initializer.setDatabasePopulator(populator)
        return initializer
    }

    @Bean
    fun initDatabase(repository: EmailRepository): CommandLineRunner {
        val objectMapper = ObjectMapper()
        return CommandLineRunner { args: Array<String?>? ->
            runBlocking {
                println(
                    repository.save(
                        Email(
                            objectMapper.writeValueAsString(
                                EmailDetail(
                                    "rndfred@163.com", "Mail subject",
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