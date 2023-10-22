package com.github.frtu.samples.coroutine.flow

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf

suspend fun main(args: Array<String>) {
    val flowOf = flowOf(4, 5, 6)
    println(flowOf.first())
    println(flowOf.first { it > 5 })
    println(flowOf.firstOrNull { it > 10 })
}