package com.github.frtu.sample.integration

import org.springframework.expression.Expression
import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser

fun main(args: Array<String>) {
    val parser: ExpressionParser = SpelExpressionParser()

    val exp: Expression = parser.parseExpression("'Hello World!'.length()")
    val message = exp.value as Integer
    println(message)
}