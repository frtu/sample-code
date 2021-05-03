package com.github.frtu.persistence.r2dbc

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcRepositories
class R2dbcConfiguration : AbstractR2dbcConfiguration() {
    @Value("\${application.persistence.url}")
    lateinit var persistenceUrl: String

    override fun connectionFactory(): ConnectionFactory = ConnectionFactories.get(persistenceUrl)

    internal fun h2ConnectionFactory(): ConnectionFactory {
        H2ConnectionFactory.inMemory("test");
        return H2ConnectionFactory(
            H2ConnectionConfiguration.builder()
                .inMemory("test")
//                .file("./test")
//                .username("sa")
//                .password("password")
                .build()
        )
    }

    internal fun postgresConnectionFactory(): ConnectionFactory {
        return PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("notification")
                .username("user_admin")
                .password("pass")
                .build()
        )
    }
}