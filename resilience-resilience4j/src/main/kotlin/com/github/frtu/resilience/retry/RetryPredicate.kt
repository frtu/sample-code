package com.github.frtu.resilience.retry

import com.github.frtu.coroutine.webclient.WebClientResponse
import org.slf4j.LoggerFactory
import java.util.function.Predicate

class RetryPredicate : Predicate<WebClientResponse> {
    /**
     * @return true if find false and should be retried
     */
    override fun test(webClientResponse: WebClientResponse): Boolean = webClientResponse.reponseBody?.let { content ->
        val result = content.contains("false")
        logger.debug("content:$content result:$result")
        result
    } ?: true

    private val logger = LoggerFactory.getLogger(this::class.java)
}