package com.github.frtu.sample.service.client

import io.micrometer.core.annotation.Timed
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

@RestController
@RequestMapping("/events")
class EventController(private val client: WebClient) {
    @GetMapping("/")
    @Timed
    suspend fun findAll() =
        client.get()
            .uri("/events")
            .accept(MediaType.APPLICATION_JSON)
            .awaitExchange()
            .awaitBody<Any>()
}