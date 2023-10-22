package com.github.frtu.samples.coroutine.flow

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList

/**
 * Cold Flow are replay-able many times
 */
suspend fun coldFlowOf() {
    val flowA = flowOf(1, 2, 3)

    println("Play it once :")
    val list = flowA.toList()
    println(list)

    println("Play it another time :")
    val list1 = flowA.toList()
    println(list1)
}

suspend fun main(args: Array<String>) {
    coldFlowOf()
}