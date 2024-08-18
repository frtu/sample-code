package com.github.frtu.sample.bot.slack

import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.kotlin_extension.request.chat.blocks
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
        val response = methods.chatPostMessage { req ->
            req
            .channel("#random")
            .blocks {
                section {
                    // "text" fields can be constructed via `plainText()` and `markdownText()`
                    markdownText("*Please select a restaurant:*")
                }
                divider()
                actions {
                    // To align with the JSON structure, you could put the `elements { }` block around the buttons but for brevity it can be omitted
                    // The same is true for things such as the section block's "accessory" container
                    button {
                        // For instances where only `plain_text` is acceptable, the field's name can be filled with `plain_text` inputs
                        text("Farmhouse", emoji = true)
                        value("v1")
                    }
                    button {
                        text("Kin Khao", emoji = true)
                        value("v2")
                    }
                }
            }
        }

        // Get a response as a Java object
        if (response.isOk) {
            val postedMessage = response.message
            println(postedMessage)
        } else {
            val errorCode = response.error // e.g., "invalid_auth", "channel_not_found"
        }
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}