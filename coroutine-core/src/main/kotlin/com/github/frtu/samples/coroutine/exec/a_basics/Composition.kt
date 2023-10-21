package com.github.frtu.samples.coroutine.exec.a_basics

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.system.measureTimeMillis

object Composition

/**
 * Configure parallelism with `-Dkotlinx.coroutines.io.parallelism=1`
 */
fun main() = runBlocking(IO) {
    println("== begin ==")
    val execution = CaptureThreadExecution()
    val timeMillis = measureTimeMillis {
        val deferredList = (1..1000).map {
            async { coroutine(it.toLong(), it, execution) }
        }
        awaitAll(*deferredList.toTypedArray())
    }
    println("== ended in $timeMillis ==")
    val range = timeMillis.toInt()
    val threadMap = execution.threadMap
    for ((key, value) in threadMap) {
        print("$key:")
        for (item in 0..range) {
            print(value[item])
        }
        println("")
    }
}

suspend fun coroutine(
    delay: Long, index: Int? = null,
    execution: CaptureThreadExecution,
) {
    val threadName = Thread.currentThread().name
    execution.increment(threadName)
    index?.let { println("$index-start-$threadName") }

    delay(delay)

    execution.increment(threadName)
    index?.let { println("$index-stop-$threadName") }
}