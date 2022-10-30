package com.github.frtu.samples.coroutine.exec.d_errors

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    runWithErrors()
}

suspend fun runWithErrors() {
    val dataFlow = flowOf(1, 2, 3)
    dataFlow
        .map {
            when {
                it % 3 == 2 -> {
                    throw IllegalArgumentException("At $it, exception can be raised & pass")
                }
                it % 3 == 0 -> {
                    error("Raise error on $it")
                }
                else -> it
            }
        }
        .catch { e ->
            println(e.message)
            when (e) {
                is IllegalArgumentException -> println("Skip IllegalArgumentException")
                else -> throw e
            }
        } // propagate exception
        .onCompletion { println("ended") }
        .collect { println(it) }
}
