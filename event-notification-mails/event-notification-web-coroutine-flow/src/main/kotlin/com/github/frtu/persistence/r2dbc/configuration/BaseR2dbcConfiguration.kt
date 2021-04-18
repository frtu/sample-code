package com.github.frtu.persistence.r2dbc.configuration

import org.springframework.data.r2dbc.config.AbstractR2dbcConfigurationExtended

/**
 * Base implementation for {@link AbstractR2dbcConfiguration} by using already created {@link ConnectionFactory}.
 *
 * Don't forget to annotate child class with {@link EnableR2dbcRepositories} & {@link Configuration, that will be the real config.
 */
class BaseR2dbcConfiguration : AbstractR2dbcConfigurationExtended()