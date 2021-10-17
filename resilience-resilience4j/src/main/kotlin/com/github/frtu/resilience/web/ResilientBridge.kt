package com.github.frtu.resilience.web

import com.github.frtu.coroutine.webclient.SuspendableWebClient
import com.github.frtu.coroutine.webclient.WebClientResponse
import io.github.resilience4j.retry.annotation.Retry
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.UUID

@Component
class ResilientBridge(private val suspendableWebClient: SuspendableWebClient) {
    fun blockingQuery(): String {
        logger.debug("start")
        val result = runBlocking {
            nonBlockingQuery().toList(mutableListOf())
        }.joinToString(",")
        logger.debug("end")
        return result
    }

    suspend fun nonBlockingQuery() = suspendableWebClient.get("/")

    @Retry(name = "bridge")
    fun blockingPost(requestId: UUID): WebClientResponse {
        logger.debug("start ${ZonedDateTime.now()}")
        val result = runBlocking {
            nonBlockingPost(requestId)
        }
        logger.debug("end ${ZonedDateTime.now()}")
        return result
    }

    @Retry(name = "bridge")
    suspend fun nonBlockingPost(requestId: UUID): WebClientResponse {
        var response = WebClientResponse(HttpStatus.PROCESSING, null)
        suspendableWebClient.post(
            url = "/",
            requestId = requestId,
            requestBody = "{'key':'value'}",
            responseCallback = { webClientResponse ->
                response = webClientResponse
            }
        )
        return response
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}