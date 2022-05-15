package com.github.frtu.sample.workflow.temporal.reminder.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.util.*

data class ReminderEvent(
    @JsonProperty("userId")
    val userId: UUID,
    @JsonProperty("data")
    val data: String,
    @JsonProperty("type")
    val type: String = "subscription",
    @JsonProperty("id")
    var id: UUID = UUID.randomUUID(),
    @JsonProperty("timeMillis")
    val timeMillis: Long = Instant.now().toEpochMilli(),
    @JsonProperty("metadata")
    val metadata: Map<String, Any> = emptyMap(),
)