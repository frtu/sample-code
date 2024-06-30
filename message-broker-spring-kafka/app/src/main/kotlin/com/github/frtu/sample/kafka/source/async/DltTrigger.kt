package com.github.frtu.sample.kafka.source.async

interface DltTrigger {
    fun received(data: TriggerData)
}