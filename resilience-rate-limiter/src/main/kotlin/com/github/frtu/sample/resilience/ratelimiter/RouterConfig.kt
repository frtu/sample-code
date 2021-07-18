package com.github.frtu.sample.resilience.ratelimiter

import kotlinx.coroutines.reactive.asFlow
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.json

@Configuration
class RouterConfig {
    @Bean
    fun route(emailContentSource: EmailContentSource): RouterFunction<*> = coRouter {
        // https://docs.spring.io/spring-framework/docs/current/kdoc-api/spring-framework/org.springframework.web.reactive.function.server/-co-router-function-dsl/index.html
        accept(APPLICATION_JSON).nest {
            GET("/v2/emails") { serverRequest ->
                ok().json().bodyAndAwait(
                    emailContentSource.send().asFlow()
                )
            }
        }
    }
}
