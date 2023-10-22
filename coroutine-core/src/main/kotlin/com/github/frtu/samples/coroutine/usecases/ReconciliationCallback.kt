package com.github.frtu.samples.coroutine.usecases

import io.kotest.common.runBlocking

object ReconciliationCallback

fun main() = runBlocking {
    retrieveDataAsync { data ->
        scanContentAsync(data) { isSafe ->
            if (isSafe) {
                sendToProcessAsync(data)
            }
        }
    }
}

fun retrieveDataAsync(scan: (ByteArray) -> Unit) {
    Thread.sleep(200)
    scan("data".toByteArray(Charsets.UTF_8))
}
fun scanContentAsync(data: ByteArray, process: (Boolean) -> Unit) {
    Thread.sleep(300)
    process(data.contains('d'.code.toByte()))
}
fun sendToProcessAsync(data: ByteArray) {
    Thread.sleep(50)
    println(data.toString())
}
