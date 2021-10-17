package com.github.frtu.resilience.config

import io.github.resilience4j.retry.RetryRegistry
import io.github.resilience4j.retry.event.RetryOnRetryEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class RetryConfig {
    @Autowired
    lateinit var registry: RetryRegistry

    @PostConstruct
    fun postConstruct() {
        registry
            .retry("bridge")
            .eventPublisher
            .onRetry { retryOnRetryEvent: RetryOnRetryEvent -> logger.warn("---RETRYING--> $retryOnRetryEvent") }
    }

    private val logger = LoggerFactory.getLogger(this::class.java)
}