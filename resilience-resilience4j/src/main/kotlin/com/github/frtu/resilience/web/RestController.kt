package com.github.frtu.resilience.web

import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/blocking")
class RestController(private val resilientBridge: ResilientBridge) {
    @GetMapping("")
    fun blocking() = ResponseEntity.status(HttpStatus.OK).body(resilientBridge.blockingQuery())

    @GetMapping("/create")
    fun blockingCreate() = try {
        val blockingPost = resilientBridge.blockingPost(UUID.randomUUID())
        ResponseEntity.status(HttpStatus.OK).body(blockingPost.reponseBody)
    } catch (e: CallNotPermittedException) {
        logger.warn(e.message, e)
        throw e
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}