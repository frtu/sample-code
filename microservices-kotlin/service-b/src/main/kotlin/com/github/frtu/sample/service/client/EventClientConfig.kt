package com.github.frtu.sample.service.client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class EventClientConfig {
    @ConstructorBinding
    @ConfigurationProperties(prefix = "service")
    data class ServiceCall(var baseUrl: String)

    @Bean
    fun webClient(webClientBuilder: WebClient.Builder, serviceCall: ServiceCall): WebClient {
        return webClientBuilder
            .baseUrl(serviceCall.baseUrl)
            .build()
    }
}