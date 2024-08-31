package com.github.frtu.sample.bot.slack.udf

import com.slack.api.app_backend.events.payload.EventsApiPayload
import com.slack.api.bolt.context.builtin.EventContext
import com.slack.api.bolt.handler.BoltEventHandler
import com.slack.api.model.event.AppMentionEvent
import com.slack.api.model.event.Event
import com.slack.api.model.event.MessageEvent
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventHandlerFactory {
    @Bean
    fun messageEventHandler(): Pair<Class<MessageEvent>, BoltEventHandler<MessageEvent>> {
        val boltEventHandler = BoltEventHandler { payload: EventsApiPayload<MessageEvent>, ctx: EventContext ->
            logger.info("Message received: channelId=[${ctx.channelId}] botUserId=[${ctx.botUserId}] botScopes=[${ctx.botScopes}] botToken=[${ctx.botToken}]")
            ctx.ack()
        }
        return Pair(MessageEvent::class.java, boltEventHandler)
    }

    @Bean
    fun appMentionEventHandler(): Pair<Class<AppMentionEvent>, BoltEventHandler<AppMentionEvent>> {
        val boltEventHandler = BoltEventHandler { req: EventsApiPayload<AppMentionEvent>, ctx: EventContext ->
            ctx.say("Hi there!")
            ctx.ack()
        }
        return Pair(AppMentionEvent::class.java, boltEventHandler)
    }

    private val logger = LoggerFactory.getLogger(this::class.java)
}