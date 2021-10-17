package com.github.frtu.resilience.web

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
    fun blockingCreate() = ResponseEntity.status(HttpStatus.OK).body(resilientBridge.blockingPost(UUID.randomUUID()).reponseBody)
}