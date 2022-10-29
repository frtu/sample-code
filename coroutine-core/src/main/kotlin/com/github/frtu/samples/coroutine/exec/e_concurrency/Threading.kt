package com.github.frtu.samples.coroutine.exec.e_concurrency

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

// https://proandroiddev.com/from-rxjava-2-to-kotlin-flow-threading-8618867e1955
val d1 = Executors.newFixedThreadPool(3).asCoroutineDispatcher()
val d2 = Executors.newFixedThreadPool(3).asCoroutineDispatcher()
val d3 = Executors.newFixedThreadPool(3).asCoroutineDispatcher()
val d4 = Executors.newFixedThreadPool(3).asCoroutineDispatcher()
val main = Executors.newFixedThreadPool(3).asCoroutineDispatcher()

fun main() = runBlocking { // this: CoroutineScope
    launch(main) {
        flowOf(1, 2, 3)
            .onEach { printThread("1") }
            .flowOn(d1) // All operators before it run in this dispatcher d1 fixed
            .onEach { printThread("2") }
            .flowOn(d2) // All operators before it run in this dispatcher d2 fixed
            .flatMapMerge {
                flowOf(4, 5, 6)
                    .onEach { printThread("inner 1") }
                    .flowOn(d3) // All operators before it run in this dispatcher d3 floating
                    .onEach { printThread("inner 2") } // run on d4
            }
            .onEach { printThread("3") }
            .flowOn(d4) // All operators before it run in this dispatcher d4 fixed
            .collect { println("end") }
    }
    println("Last")
}

fun printThread(sectionName: String) {
    println("$sectionName: ${Thread.currentThread().name}")
}