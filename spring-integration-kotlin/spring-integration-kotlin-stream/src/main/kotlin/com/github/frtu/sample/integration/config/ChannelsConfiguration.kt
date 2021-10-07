package com.github.frtu.sample.integration.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.MessageChannels

/**
 * @see <a href="https://spring.io/blog/2020/04/07/spring-tips-the-spring-integration-kotlin-dsl">spring-integration-kotlin-dsl</a>
 */
@Configuration
class ChannelsConfiguration {
    @Bean
    fun txt() = MessageChannels.direct().get()

    @Bean
    fun csv() = MessageChannels.direct().get()

    @Bean
    fun errors() = MessageChannels.direct().get()
}