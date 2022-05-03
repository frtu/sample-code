package com.github.frtu.sample.workflow.temporal.activity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDateTime
import java.util.*

@JsonPropertyOrder("id", "receiver", "subject", "content", "createdAt", "updatedAt")
@JsonIgnoreProperties(
    value = ["createdAt", "updatedAt"],
    allowGetters = true
)
data class Email(
    var data: String = "{}",

    var identity: UUID? = null,

    val creationTime: LocalDateTime = LocalDateTime.now(),

    var updateTime: LocalDateTime = creationTime
)
{
    constructor(
        value: Any,
        identity: UUID? = null,
        creationTime: LocalDateTime = LocalDateTime.now(),
        updateTime: LocalDateTime = creationTime
    ) : this(objectMapper.writeValueAsString(value), identity, creationTime, updateTime)

    companion object {
        const val TABLE_NAME = "email"
        val objectMapper = ObjectMapper()
    }
}
