package com.github.frtu.sample.workflow.temporal.subscription.source.sync

import com.github.frtu.logs.core.RpcLogger.*
import com.github.frtu.sample.workflow.temporal.subscription.domain.SubscriptionEvent
import com.github.frtu.sample.workflow.temporal.subscription.domain.service.SubscriptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.coRouter
import java.net.URI

@Configuration
class RouterConfig(private val subscriptionHandler: SubscriptionHandler) {
    internal val rpcLogger = create(this::class.java)

    @Bean
    fun route(): RouterFunction<*> = coRouter {
        val uriPath = "/v1/subscriptions"
        POST(uriPath) { serverRequest ->
            val subscriptionEvent = serverRequest.awaitBody<SubscriptionEvent>()
            val createdId = subscriptionHandler.handle(subscriptionEvent)
            rpcLogger.info(uri(uriPath), requestBody(subscriptionEvent), responseBody(createdId))
            created(URI.create("$uriPath/$createdId")).buildAndAwait()
        }
    }
}
