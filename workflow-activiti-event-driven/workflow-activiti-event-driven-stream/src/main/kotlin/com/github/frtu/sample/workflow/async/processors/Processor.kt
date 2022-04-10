package com.github.frtu.sample.workflow.async.processors

interface Processor<IN, OUT> {
    fun process(input: IN): OUT
}