package com.github.frtu.resilience.web

import com.github.frtu.coroutine.webclient.SuspendableWebClient
import com.github.frtu.logs.core.RpcLogger
import com.github.frtu.logs.core.RpcLogger.requestBody
import com.github.frtu.logs.core.RpcLogger.responseBody
import com.github.frtu.logs.core.RpcLogger.uri
import io.netty.handler.codec.http.HttpResponseStatus
import kotlinx.coroutines.reactive.asFlow
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.net.URI
import java.util.UUID
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Flux
import javax.annotation.PreDestroy

@Configuration
class RouterConfig {
    @Bean
    fun route(resilientBridge: ResilientBridge): RouterFunction<*> = coRouter {
        GET("/") { serverRequest ->
            ok().json().bodyAndAwait(resilientBridge.query())
        }
    }

    @Bean
    fun suspendableWebClient(mockWebServer: MockWebServer): SuspendableWebClient =
        SuspendableWebClient.create("http://localhost:${mockWebServer.port}")

    @Configuration
    class MockServerConfig {
        lateinit var mockWebServer: MockWebServer

        @Bean
        fun mockWebServer(): MockWebServer {
            mockWebServer = MockWebServer()
            logger.debug("=== ${this.javaClass.simpleName} starting mockWebServer:${mockWebServer}")

            mockWebServer.start()

            for (index in 1..10) {
                // Simulate 2 errors
                for (index in 1..2) {
                    mockWebServer.enqueue(MockResponse().setResponseCode(HttpResponseStatus.SERVICE_UNAVAILABLE.code()))
                }
                // Before returning the correct response
                val responseBody = """{"message":"response"}"""
                mockWebServer.enqueue(
                    MockResponse()
                        .setBody(responseBody)
                        .addHeader("Content-Type", "application/json")
                )
            }
            return mockWebServer
        }

        @PreDestroy
        @Throws(Exception::class)
        fun onDestroy() {
            logger.debug("=== ${this.javaClass.simpleName} stopping mockWebServer")
            mockWebServer.shutdown()
        }

        internal val logger = LoggerFactory.getLogger(this::class.java)
    }
}
