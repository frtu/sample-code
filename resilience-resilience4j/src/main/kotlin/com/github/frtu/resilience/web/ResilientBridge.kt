package com.github.frtu.resilience.web

import com.github.frtu.coroutine.webclient.SuspendableWebClient
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class ResilientBridge(private val suspendableWebClient: SuspendableWebClient) {
    fun query() = runBlocking {
        return@runBlocking suspendableWebClient.get("/")
    }
}