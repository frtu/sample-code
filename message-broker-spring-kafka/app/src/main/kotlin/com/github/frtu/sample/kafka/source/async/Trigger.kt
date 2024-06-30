package com.github.frtu.sample.kafka.source.async

interface Trigger {
    fun received(data: TriggerData)
}