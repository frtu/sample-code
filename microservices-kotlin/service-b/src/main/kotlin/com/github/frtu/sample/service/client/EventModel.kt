package com.github.frtu.sample.service.client

import java.util.*

data class EventModel(
    val name: String,
    val value: Float,
    val id: String = UUID.randomUUID().toString(),
    val eventTime: Long = System.currentTimeMillis() / 1000L
)