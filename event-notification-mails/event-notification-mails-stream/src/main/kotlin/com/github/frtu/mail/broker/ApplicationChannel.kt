package com.github.frtu.mail.broker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.github.frtu.mail.broker.consumer")
class ApplicationChannel

fun main(args: Array<String>) {
    runApplication<ApplicationChannel>(*args)
}
