package com.github.frtu.spring.core

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class BasicConfig {
    @Value("\${config.no.override:default_value}")
    lateinit var configModeNoOverride: String

    @Value("\${config.test:default_value}")
    lateinit var configModeFile: String

    @Value("\${config.test.file:default_value}")
    lateinit var configModeTestFile: String

    @Value("\${config.test.annotation:default_value}")
    lateinit var configModeTestAnnotation: String
}