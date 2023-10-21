package com.github.frtu.samples.coroutine.exec.a_basics

class CaptureThreadExecution(
    private val initialSize: Int = 5000,
    val threadMap: MutableMap<String, IntArray> = mutableMapOf(),
) {
    private var referenceTime: Long = getTime()

    fun markReferenceTime() {
        referenceTime = getTime()
    }

    fun increment(threadName: String) {
        val index = getIndex()
        val timeArray: IntArray = threadMap[threadName] ?: run {
            IntArray(initialSize).also { threadMap[threadName] = it }
        }
        timeArray[index] += 1
    }

    private fun getIndex() = (getTime() - referenceTime).toInt()


    fun print(range: Int) {
        for ((key, value) in threadMap) {
            print("$key:")
            for (item in 0..range) {
                print(value[item])
            }
            println("")
        }
    }

    private fun getTime() = System.currentTimeMillis()
}