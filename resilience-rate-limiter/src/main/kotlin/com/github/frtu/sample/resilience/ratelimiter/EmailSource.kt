package com.github.frtu.sample.resilience.ratelimiter

import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class EmailContentSource {
    @RateLimiter(name="send", fallbackMethod = "sendFallback")
    fun send(): Mono<String> {
        val message = "message"
        logger.info("Sending message:$message")
        return Mono.just(message)
    }

    fun sendFallback(throwable: Throwable): Mono<String> {
        logger.error("Error ${throwable.message}")
        return Mono.just("sendFallback")
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}