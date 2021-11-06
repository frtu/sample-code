package com.github.frtu.samples.patterns

import kotlin.math.pow

fun main() {
    var index = 0
    val unitSupplier = NumSupplier { (1..9).elementAt(index++) }

    val operator = Operator { i -> 2.0.pow(i.toDouble()) }

    for (i in 1..9) {
        println(operator.execute(unitSupplier.get()))
    }
}

fun interface NumSupplier {
    fun get(): Int
}

fun interface Operator {
    fun execute(i: Int): Double
}