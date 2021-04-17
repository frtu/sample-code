package com.github.frtu.samples.coroutine.core

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowBuilders(private val list: List<Int>) : AbstractFlow<Int>() {
    override suspend fun collectSafely(collector: FlowCollector<Int>) {
        for (item in list) {
            collector.emit(item)
        }
    }

    fun buildFlowOf(): Flow<Int> {
        return flowOf(1, 2, 3)
    }

    fun buildFlowAs(): Flow<Int> {
        return listOf(7, 8, 9).asFlow()
    }

    fun buildChannel(other: Flow<Int>): Flow<Int> = channelFlow {
        // collect from one coroutine and send it
        launch {
            collect { send(it) }
        }
        // collect and send from this coroutine, too, concurrently
        other.collect { send(it) }
    }
}

// Only termination function need to have suspend
suspend fun main(args: Array<String>) {
    val flowBuilder = FlowBuilders(listOf(4, 5, 6))

    val flowAs = flowBuilder.buildFlowAs()
    println("flowAs :")
    println(flowAs.toList())

    val flowOf = flowBuilder.buildFlowOf()
    println("flowOf :")
    println(flowOf.toList())

    val flowChannel = flowBuilder.buildChannel(flowOf)
    println("flowChannel :")
    println(flowChannel.toList())
}