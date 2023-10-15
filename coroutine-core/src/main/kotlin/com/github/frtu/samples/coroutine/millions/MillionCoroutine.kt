package com.github.frtu.samples.coroutine.millions

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object MillionCoroutine

fun main() = runBlocking {
    repeat(1_000_000) {
        launch {
            delay(5000)
            if (Thread.currentThread().name == "main") {
                print('.')
            } else {
                print('*')
            }
        }
    }
}