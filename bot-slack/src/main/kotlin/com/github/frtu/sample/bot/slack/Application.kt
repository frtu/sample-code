package com.github.frtu.sample.bot.slack

import com.github.frtu.sample.bot.slack.config.SlackConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(SlackConfig::class)
class Application

fun main(args: Array<String>) {
    try {
        runApplication<Application>(*args)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}