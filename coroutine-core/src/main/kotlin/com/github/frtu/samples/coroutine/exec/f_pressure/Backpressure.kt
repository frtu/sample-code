package com.github.frtu.samples.coroutine.exec.f_pressure

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        backpressureBuffered()
    }
    print("\nCollected in $time ms")
}

// All is sequential and takes emmitter 5 times : (1s * 5 + 3s * 5)
suspend fun backpressureSameThread() = emitter()
    .collect {
        print("\nCollect $it starts (${currTime() - start}ms) ")
        delay(3000)
        println("Collect $it ends (${currTime() - start}ms) ")
    }

// All is sequential and takes emmitter : 1s + (3s * 5)
suspend fun backpressureDifferentThread() = emitter()
    .flowOn(Dispatchers.Default)
    .collect {
        print("\nCollect $it starts (${currTime() - start}ms) ")
        delay(3000)
        println("Collect $it ends (${currTime() - start}ms) ")
    }

// SUSPEND just backpressure after x iteration
// DROP_LATEST or DROP_OLDEST can collect x and discard latest or oldest
suspend fun backpressureBuffered() = emitter()
    .buffer(2, BufferOverflow.DROP_OLDEST)
//    .conflate() // EQUIVALENT TO .buffer(0, BufferOverflow.DROP_OLDEST)
    .collect {
        print("\nCollect $it starts (${currTime() - start}ms) ")
        delay(3000)
        println("Collect $it ends (${currTime() - start}ms) ")
    }

var start: Long = 0

fun emitter(): Flow<Int> =
    (1..5)
        .asFlow()
        .onStart { start = currTime() }
        .onEach {
            delay(1000)
            print("Emit $it (${currTime() - start}ms) ")
        }

fun currTime() = System.currentTimeMillis()
fun threadName() = Thread.currentThread().name
