package com.github.frtu.samples.coroutine.millions

object MillionThread

fun main() {
    val threads = (1..1_000_000).map { index ->
        Thread.ofVirtual()
            .name("virtual-", index.toLong())
            .unstarted {
                sleep(5000)
                print('.')
            }
    }
    for (thread in threads) {
        thread.start()
    }
    for (thread in threads) {
        thread.join()
    }
}

private fun sleep(millis: Long) {
    try {
        Thread.sleep(millis)
    } catch (e: InterruptedException) {
        throw RuntimeException(e)
    }
}