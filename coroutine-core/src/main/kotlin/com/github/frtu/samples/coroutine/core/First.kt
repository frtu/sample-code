package com.github.frtu.samples.coroutine.core

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf

suspend fun main(args: Array<String>) {
    val flowOf = flowOf(4, 5, 6)
    val firstOrNull = flowOf.firstOrNull()
    println(firstOrNull)
}