package com.github.frtu.sample.workflow.events.processors

import org.springframework.stereotype.Component

@Component
class LogProcessor {
    fun process() {
        println("Running LogProcessor")
    }
}