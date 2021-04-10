package com.github.frtu.coroutine.web

import com.github.frtu.model.Email
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.renderAndAwait
import java.util.*

@Configuration
class Router {
    @Bean
    fun routes(handlers: Handlers) = coRouter {
        GET("/", handlers::index)
    }
}

@Component
class Handlers(builder: WebClient.Builder) {
    private val email = Email(UUID.randomUUID(), "rndfred@gmail.com","Mail subject",
        "Lorem ipsum dolor sit amet.", "SENT")

    suspend fun index(request: ServerRequest) =
        ServerResponse
            .ok()
            .renderAndAwait("index", mapOf("emails" to listOf(email)))
}
