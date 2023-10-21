package com.github.frtu.samples.future

import io.kotest.matchers.shouldBe
import java.util.concurrent.CompletableFuture

fun main() {
//    val future = waitAndReturn(1, 1000, "Harry")
//    future!!.join() shouldBe "Harry"
//
//    val futureError = waitAndThrow(1, 1000)
//    shouldThrow<RuntimeException> {
//        futureError!!.join()
//    }

    val f1 = waitAndReturn(1, 1000, "Harry")
    val f2 = waitAndReturn(2, 2000, "Ron")

    val combinedFutures = CompletableFuture.allOf(f1, f2)
    combinedFutures.join()

    "Harry" shouldBe f1!!.join()
    "Ron" shouldBe f2!!.join()
}

private fun waitAndReturn(index: Int, millis: Long, value: String): CompletableFuture<String>? {
    return CompletableFuture.supplyAsync {
        println("$index-start")
        try {
            Thread.sleep(millis)
            println("$index-end")
            return@supplyAsync value
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }
}

private fun waitAndThrow(index: Int, millis: Long): CompletableFuture<String>? {
    return CompletableFuture.supplyAsync {
        println("$index-start")
        try {
            Thread.sleep(millis)
            println("$index-end")
            null
        } finally {
            throw RuntimeException()
        }
    }
}

object CompletableFutureJoin
