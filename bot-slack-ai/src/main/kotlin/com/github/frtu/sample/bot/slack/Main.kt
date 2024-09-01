package com.github.frtu.sample.bot.slack

import com.slack.api.app_backend.events.payload.EventsApiPayload
import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.api.bolt.context.builtin.EventContext
import com.slack.api.bolt.context.builtin.SlashCommandContext
import com.slack.api.bolt.request.builtin.SlashCommandRequest
import com.slack.api.bolt.socket_mode.SocketModeApp
import com.slack.api.model.event.AppMentionEvent
import com.slack.api.socket_mode.SocketModeClient

/**
 * https://slack.dev/java-slack-sdk/guides/socket-mode
 * https://api.slack.com/apis/socket-mode
 */
fun main() {
    // Create a new Slack app
    val botToken = System.getenv("SLACK_BOT_TOKEN")
    val botSigningSecret = System.getenv("SLACK_BOT_SIGNING_SECRET")
    val appConfig = AppConfig.builder()
        .singleTeamBotToken(botToken)
        .signingSecret(botSigningSecret)
        .build()

    // Initialize the App and register listeners
    val app = App(appConfig)

    // https://api.slack.com/apis/events-api
    // https://slack.dev/java-slack-sdk/guides/bolt-basics/#dispatching-events
    app.event(AppMentionEvent::class.java) { req: EventsApiPayload<AppMentionEvent>, ctx: EventContext ->
        ctx.say("Hi there!")
        ctx.ack()
    }

    app.command("/hello") { req: SlashCommandRequest, ctx: SlashCommandContext ->
        ctx.logger.debug("Command /hello called")

        val response = ctx.client().chatPostMessage { r ->
            r.channel(ctx.channelId).text(":wave: How are you?")
        }
        println(response.message)
        ctx.ack(":wave: Hello!")
    }
    app.command("/echo") { req: SlashCommandRequest, ctx: SlashCommandContext ->
        val commandArgText = req.payload.text
        val channelId = req.payload.channelId
        val channelName = req.payload.channelName
        val text = "You said $commandArgText at <#$channelId|$channelName>"
        ctx.logger.debug("Command /echo called : $text")
        ctx.ack(text)
    }

    // -----------------------------------------------------
    // Start the app in Socket Mode
    // https://slack.dev/java-slack-sdk/guides/socket-mode
    // -----------------------------------------------------
    // the app-level token with `connections:write` scope
    val appToken = System.getenv("SLACK_APP_TOKEN")
    // Initialize the adapter for Socket Mode
    // with an app-level token and your Bolt app with listeners.
    val socketModeApp = SocketModeApp(
        appToken,
        SocketModeClient.Backend.JavaWebSocket,
        app,
    )
    // #start() method establishes a new WebSocket connection and then blocks the current thread.
    // If you do not want to block this thread, use #startAsync() instead.
    socketModeApp.start()

    // HTTP receiving message https://api.slack.com/tools/bolt-java
//    val server = SlackAppServer(app)
//    server.start() // http://localhost:3000/slack/events
}