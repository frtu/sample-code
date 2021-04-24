package com.github.frtu.spring.boot

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

// Mapping config to object
@EnableConfigurationProperties(AppProperties::class)
class SampleBootConfig {
    data class Bean1(val key1: String)
    data class Bean2(val key1: String)
    data class Bean3(val key1: String)
    data class Bean4(val key1: String)

    @Bean
    fun bean1(appProperties: AppProperties): Bean1 = Bean1(appProperties.key1)

    @Bean
    @ConditionalOnProperty(prefix="application.config", name=["enabled"], havingValue = "true")
    fun bean2(appProperties: AppProperties): Bean2 = Bean2(appProperties.key1)

    @Bean
    @ConditionalOnExpression("!'\${application.config.to-be-override}'.isEmpty()")
    fun bean3(appProperties: AppProperties): Bean3 = Bean3(appProperties.key1)

    @Bean
    @ConditionalOnExpression("#{ '\${application.config.mode}' != 'TEST_OVERRIDE_VALUE' }")
    fun bean4(appProperties: AppProperties): Bean4 = Bean4(appProperties.key1)
}