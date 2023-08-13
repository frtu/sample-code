package com.github.frtu.samples.coroutine.exec.b_scope

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

val coroutineScope = CoroutineScope(Job() + Dispatchers.Default)

// https://proandroiddev.com/from-rxjava-2-to-kotlin-flow-threading-8618867e1955
fun main() = runBlocking { // this: CoroutineScope
    parallelLaunchInScope()
    println("Done")
}

// Concurrently executes both sections
suspend fun scoping() { // this: CoroutineScope
    flowOf(1, 2, 3)
        .flowOn(Dispatchers.IO) // All operators before it run in this dispatcher
        .map { it + 1 }
        .flowOn(Dispatchers.Default)
        .collect { println(it) } // suspending fun
    println("Hello")
}

suspend fun parallelLaunchInScope() = coroutineScope { // this: CoroutineScope
    flowOf(1, 2, 3)
        .onEach {
            println(it)
            delay(400)
        }.launchIn(this)

    flowOf(1, 2, 3)
        .onEach {
            println("second $it")
            delay(100)
        }.launchIn(coroutineScope)
}

suspend fun parallelFlowOnScope() = coroutineScope { // this: CoroutineScope
    launch {
        flow {
            // flowOf may make it run on the outer context
            flowOf(1, 2, 3)
                .collect {
                    // flow { emit() } allow to make sure it run on the specified scope
                    emit(it)
                }
        }
            .flowOn(Dispatchers.IO) // All previous operators run in this dispatcher
            .collect {
                println(it)
                delay(400)
            }
    }
    launch {
        flowOf(1, 2, 3)
            .flowOn(Dispatchers.IO)
            .collect {
                println("second $it")
                delay(200)
            }
    }
}
