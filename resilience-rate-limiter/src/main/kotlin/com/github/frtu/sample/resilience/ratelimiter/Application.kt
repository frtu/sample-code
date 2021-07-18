package com.github.frtu.sample.resilience.ratelimiter

import com.github.frtu.sample.resilience.ratelimiter.redis.SimpleRedisRateLimiter
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@EnableConfigurationProperties(AppProperties::class)
@SpringBootApplication
//@EnableR2dbcRepositories
class Application {
    @Bean
    fun call(simpleRedisRateLimiter: SimpleRedisRateLimiter): CommandLineRunner {
        return CommandLineRunner {
            val uniqueKey = "resource2"
            for (i in 0..10) {
                if (!simpleRedisRateLimiter.isLimitReached(uniqueKey)) {
                    logger.info("Called !")
                } else {
                    logger.warn("Limited reached for resource [$uniqueKey]")
                }
            }
        }
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}