package com.github.frtu.sample.integration

import org.springframework.expression.Expression
import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext

fun main(args: Array<String>) {
    val car = Car()
    car.make = "Good manufacturer"
    car.model = "Model 3"
    car.yearOfProduction = 2014

    val carPark = CarPark()
    carPark.cars.add(car)

    val context = StandardEvaluationContext(carPark)

    val expressionParser: ExpressionParser = SpelExpressionParser()

    val expression: Expression = expressionParser.parseExpression("model")
    println(expression.getValue(car) as String)

    expressionParser.parseExpression("cars[0].model")
        .setValue(context, "Other model")

    println(expression.getValue(car) as String)

    val expression2 = expressionParser.parseExpression("yearOfProduction > 2005")
    println(expression2.getValue(car, Boolean::class.java))

    val exp: Expression = expressionParser.parseExpression("'Hello World!'.length()")
    val message = exp.value as Integer
    println(message)


    // create an array of integers
    val primes: MutableList<Int> = mutableListOf()
    primes.addAll(listOf(2, 3, 5, 7, 11, 13, 17))

    // create parser and set variable 'primes' as the array of integers
    context.setVariable("primes", primes)

    // all prime numbers > 10 from the list (using selection ?{...})
    // evaluates to [11, 13, 17]
    val primesGreaterThanTen = expressionParser.parseExpression("#primes.?[#this>10]").getValue(context) as List<Int>
    println(primesGreaterThanTen)
}