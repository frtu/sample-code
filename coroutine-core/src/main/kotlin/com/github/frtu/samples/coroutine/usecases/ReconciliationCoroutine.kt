package com.github.frtu.samples.coroutine.usecases

import io.kotest.common.runBlocking
import kotlinx.coroutines.delay

class ReconciliationCoroutine {
    fun test() = runBlocking {
        val data = retrieveData()
        val isSafe = scanContent(data)
        if (isSafe) {
            sendToProcess(data)
        }
        print("ss")
    }

    suspend fun retrieveData(): ByteArray {
        delay(200)
        return "data".toByteArray(Charsets.UTF_8)
    }

    suspend fun scanContent(data: ByteArray): Boolean {
        delay(300)
        return data.contains('d'.code.toByte())
    }

    suspend fun sendToProcess(data: ByteArray) {
        delay(50)
        println(data.toString())
    }
}

