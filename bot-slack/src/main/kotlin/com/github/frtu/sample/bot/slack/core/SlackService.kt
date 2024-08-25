package com.github.frtu.sample.bot.slack.core

import com.slack.api.app_backend.events.payload.EventsApiPayload
import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.api.bolt.context.builtin.EventContext
import com.slack.api.bolt.context.builtin.SlashCommandContext
import com.slack.api.bolt.request.builtin.SlashCommandRequest
import com.slack.api.bolt.socket_mode.SocketModeApp
import com.slack.api.model.event.AppMentionEvent
import com.slack.api.socket_mode.SocketModeClient
import jakarta.annotation.PostConstruct

class SlackService(
    private val appConfig: AppConfig,
    private val appToken: String,
) {
    constructor(slackAppProperties: SlackAppProperties) : this(
        appConfig = AppConfig.builder()
            .singleTeamBotToken(slackAppProperties.botOauthToken)
            .signingSecret(slackAppProperties.signingSecret)
            .build(),
        appToken = slackAppProperties.token,
    )

    private lateinit var app: App
    private lateinit var socketModeApp : SocketModeApp

    @PostConstruct
    fun initialize() {
        // Initialize the App and register listeners
        app = App(appConfig)

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

        // Initialize the adapter for Socket Mode
        // with an app-level token and your Bolt app with listeners.
        socketModeApp = SocketModeApp(
            appToken,
            SocketModeClient.Backend.JavaWebSocket,
            app,
        )
        // #start() method establishes a new WebSocket connection and then blocks the current thread.
        // If you do not want to block this thread, use #startAsync() instead.
        socketModeApp.start()
    }
}