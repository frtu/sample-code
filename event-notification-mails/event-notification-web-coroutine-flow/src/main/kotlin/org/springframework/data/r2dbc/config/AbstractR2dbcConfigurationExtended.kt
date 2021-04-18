package org.springframework.data.r2dbc.config

import io.r2dbc.spi.ConnectionFactory

/**
 * Allow to skip the mandatory method {@link #connectionFactory()}
 */
open class AbstractR2dbcConfigurationExtended : AbstractR2dbcConfiguration() {
    override fun connectionFactory(): ConnectionFactory {
        return super.lookupConnectionFactory()
    }
}