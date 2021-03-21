package com.github.frtu.sample.service.client

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

@RestController
@RequestMapping("/events")
class EventController(private val client: WebClient) {
    @GetMapping("/")
    suspend fun findAll() =
        client.get()
            .uri("/events")
            .accept(MediaType.APPLICATION_JSON)
            .awaitExchange()
            .awaitBody<Any>()
}