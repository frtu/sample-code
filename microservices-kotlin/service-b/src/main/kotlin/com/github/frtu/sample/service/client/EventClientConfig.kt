package com.github.frtu.sample.service.client

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.actuate.metrics.AutoTimer
import org.springframework.boot.actuate.metrics.web.reactive.client.DefaultWebClientExchangeTagsProvider
import org.springframework.boot.actuate.metrics.web.reactive.client.MetricsWebClientFilterFunction
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class EventClientConfig {
    @ConstructorBinding
    @ConfigurationProperties(prefix = "client.events")
    data class ServiceCall(
        var baseUrl: String,
        var metricName: String?
    )

    @Bean
    fun webClient(
        webClientBuilder: WebClient.Builder,
        serviceCall: ServiceCall,
        meterRegistry: MeterRegistry
    ): WebClient {
        val builder = webClientBuilder.baseUrl(serviceCall.baseUrl)

        serviceCall.metricName?.let {
            builder.filter(
                MetricsWebClientFilterFunction(
                    meterRegistry,
                    DefaultWebClientExchangeTagsProvider(),
                    serviceCall.metricName,
                    AutoTimer.ENABLED
                )
            )
        }
        return builder.build()
    }
}