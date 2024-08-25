package com.github.frtu.sample.bot.slack.core

import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.api.bolt.socket_mode.SocketModeApp
import com.slack.api.socket_mode.SocketModeClient
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service

@Service
class SlackService(
    private val slackAppProperties: SlackAppProperties,
    private val slackCommandRegistry: SlackCommandRegistry,
) {
    private lateinit var app: App
    private lateinit var socketModeApp : SocketModeApp

    @PostConstruct
    fun initialize() {
        val appConfig = AppConfig.builder()
            .singleTeamBotToken(slackAppProperties.botOauthToken)
            .signingSecret(slackAppProperties.signingSecret)
            .build()

        // Initialize the App and register listeners
        app = App(appConfig)

        // Register all commands available as Spring Beans
        slackCommandRegistry.getAll().forEach { (name, command) ->
            app.command("/$name", command)
        }

        // Initialize the adapter for Socket Mode
        // with an app-level token and your Bolt app with listeners.
        socketModeApp = SocketModeApp(
            slackAppProperties.token,
            SocketModeClient.Backend.JavaWebSocket,
            app,
        )
        // #start() method establishes a new WebSocket connection and then blocks the current thread.
        // If you do not want to block this thread, use #startAsync() instead.
        socketModeApp.start()
    }
}