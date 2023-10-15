package com.github.frtu.samples.coroutine.millions

import kotlin.concurrent.thread

object MillionThread

fun main() {
    repeat(1_000_000) {
        thread {
            Thread.sleep(5000)
            print('.')
        }
    }
}