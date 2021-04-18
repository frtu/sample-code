package com.github.frtu.coroutine.configuration

import com.github.frtu.persistence.r2dbc.configuration.BaseR2dbcConfiguration
import io.r2dbc.spi.ConnectionFactory
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

@Configuration
@EnableR2dbcRepositories
@EnableTransactionManagement
class R2dbcDatabaseConfig : BaseR2dbcConfiguration() {
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
        populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("./db/migration/V0_1_1__partition-email.sql")))
        initializer.setDatabasePopulator(populator)
        return initializer
    }
}