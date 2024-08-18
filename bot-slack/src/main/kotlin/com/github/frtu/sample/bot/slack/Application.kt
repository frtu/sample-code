package com.github.frtu.sample.bot.slack

import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {
    @Bean
    fun initializer(): CommandLineRunner = CommandLineRunner {
        logger.debug("======================================")

        val token = "xoxb-xxx"

        // https://slack.dev/java-slack-sdk/guides/web-api-basics
        val slack = Slack.getInstance()

        // Initialize an API Methods client with the given token
        val methods: MethodsClient = slack.methods(token)

        // Build a request object
        val request = ChatPostMessageRequest.builder()
            .channel("#random") // Use a channel ID `C1234567` is preferable
            .text(":wave: Hi from a bot written in Java!")
            .build()

        // Get a response as a Java object
        val response = methods.chatPostMessage(request)
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}