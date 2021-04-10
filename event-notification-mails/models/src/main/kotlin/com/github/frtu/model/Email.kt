package com.github.frtu.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.util.*

@JsonPropertyOrder("id", "receiver", "subject", "content", "createdAt", "updatedAt")
@JsonIgnoreProperties(
    value = ["createdAt", "updatedAt"],
    allowGetters = true
)
data class Email(
    var id: UUID? = null,
    var receiver: String? = null,
    var subject: String? = null,
    var content: String? = null,
    var status: String? = null,
    val creationTime: Date = Date.from(java.time.ZonedDateTime.now().toInstant()),
    var updateTime: Date = creationTime
)
