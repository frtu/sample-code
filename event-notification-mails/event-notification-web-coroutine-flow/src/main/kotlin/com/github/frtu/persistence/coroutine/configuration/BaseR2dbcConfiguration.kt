package com.github.frtu.persistence.coroutine.configuration

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.ApplicationContext
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

/**
 * Base implementation for {@link AbstractR2dbcConfiguration} by using already created {@link ConnectionFactory}.
 *
 * Don't forget to annotate child class with {@link EnableR2dbcRepositories} & {@link Configuration, that will be the real config.
 */
class BaseR2dbcConfiguration : AbstractR2dbcConfiguration() {
    override fun connectionFactory(): ConnectionFactory {
        return appContext.getBean(ConnectionFactory::class.java)
    }

    lateinit var appContext: ApplicationContext
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        appContext = applicationContext
        super.setApplicationContext(applicationContext)
    }
}