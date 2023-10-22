package com.github.frtu.samples.coroutine.flow

import kotlinx.coroutines.flow.AbstractFlow
import kotlinx.coroutines.flow.FlowCollector

class MyFlow(private val list: List<Int>) : AbstractFlow<Int>() {
    override suspend fun collectSafely(collector: FlowCollector<Int>) {
        for (item in list) {
            collector.emit(item)
        }
    }
}

class SampleCollector<T>() : FlowCollector<T> {
    override suspend fun emit(value: T) {
        println(value)
    }
}

suspend fun main(args: Array<String>) {
    val flowBuilder = MyFlow(listOf(4, 5, 6))
//    flowBuilder.collect(SampleCollector())
}