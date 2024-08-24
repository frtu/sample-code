package com.github.frtu.sample.bot.slack

import com.slack.api.Slack
import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.api.bolt.socket_mode.SocketModeApp
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.kotlin_extension.request.chat.blocks

fun main() {
    // Create a new Slack app
    val botToken = System.getenv("SLACK_BOT_TOKEN")
    val botSigningSecret = System.getenv("SLACK_BOT_SIGNING_SECRET")
    val app = App(
        AppConfig.builder()
            .singleTeamBotToken(botToken)
            .signingSecret(botSigningSecret)
            .build()
    )

    // https://slack.dev/java-slack-sdk/guides/web-api-basics
    val slack = Slack.getInstance()

    // Initialize an API Methods client with the given token
    val methods: MethodsClient = slack.methods(botToken)

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

    // Start the app in Socket Mode
    val appToken = System.getenv("SLACK_APP_TOKEN")
    val socketModeApp = SocketModeApp(appToken, app)
    socketModeApp.start()

//    val server = SlackAppServer(app)
//    server.start() // http://localhost:3000/slack/events
}