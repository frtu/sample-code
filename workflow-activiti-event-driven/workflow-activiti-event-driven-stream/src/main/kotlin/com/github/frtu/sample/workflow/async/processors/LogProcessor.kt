package com.github.frtu.sample.workflow.async.processors

import org.springframework.stereotype.Component

@Component
class LogProcessor : Processor<String, Unit> {
    fun process() {
        process("Running LogProcessor")
    }

    override fun process(input: String) {
        println("${input}")
    }
}