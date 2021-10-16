package com.github.frtu.resilience.web

import com.github.frtu.coroutine.webclient.SuspendableWebClient
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

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

    internal val logger = LoggerFactory.getLogger(this::class.java)
}