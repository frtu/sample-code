package com.github.frtu.coroutine.web

import com.github.frtu.coroutine.persistence.EmailDetail
import com.github.frtu.logs.core.RpcLogger
import com.github.frtu.logs.core.RpcLogger.phase
import com.github.frtu.logs.core.RpcLogger.server
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
open class RouterConfigTest {
    internal var mockWebServer = MockWebServer()

    private val logger = LoggerFactory.getLogger(RouterConfigTest::class.java)
    private val rpcLogger = RpcLogger.create(logger)

    @Autowired
    lateinit var webTestClient: WebTestClient

    @LocalServerPort
    var port: Int = 0

    @BeforeAll
    fun setup() {
        mockWebServer = MockWebServer()
        logger.debug("=== ${this.javaClass.simpleName} starting mockWebServer:${mockWebServer}")

        mockWebServer.start()
    }

    @Test
    fun route() {
        rpcLogger.debug(server(), phase("SERVER_START:${mockWebServer.port}"))
        //--------------------------------------
        // 1. Prepare server data & Init client
        //--------------------------------------
        val responseBody = """{"message":"response"}"""
        mockWebServer.enqueue(
            MockResponse()
                .setBody(responseBody)
                .addHeader("Content-Type", "application/json")
        )


        val emailDetail = EmailDetail(
            "titi@163.com", "Mail subject",
            "Lorem ipsum dolor sit amet.", "SENT"
        )

        webTestClient.post().uri("/api/repos")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just<EmailDetail>(emailDetail), EmailDetail::class.java)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            //https://github.com/json-path/JsonPath
            .jsonPath("$.name").isNotEmpty()
            .jsonPath("$.name").isEqualTo("test-webclient-repository")
    }
}