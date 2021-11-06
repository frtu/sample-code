package com.github.frtu.samples.patterns

fun main() {
    println("== Run a supplier to a consumer ==")
    supplierFun { consumerFun { it } }

    println("== Consume from a supplier CONVENIENT FOR MULTIPLE RETURNS ==")
    consumerFun { supplierFun {} }
}

fun supplierFun(consumer: (Double) -> Unit): Double {
    var n = Math.random()
    consumer(n)
    return n
}

fun consumerFun(supplier: () -> Double) {
    for (i in 1..3) {
        println(supplier())
    }
}