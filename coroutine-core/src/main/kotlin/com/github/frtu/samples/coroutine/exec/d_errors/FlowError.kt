package com.github.frtu.samples.coroutine.exec.d_errors

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val flowError = FlowError()
    flowError.runWithErrors()
}

class FlowError {
    suspend fun runWithErrors() {
        val dataFlow = flowOf(1, 2, 3)
        dataFlow
            .map {
                if (it % 3 == 0) {
                    error("Raise error on $it")
                } else it
            }
            .catch { e -> throw e } // propagate exception
            .onCompletion { println("ended") }
            .collect { println(it) }
    }
}
