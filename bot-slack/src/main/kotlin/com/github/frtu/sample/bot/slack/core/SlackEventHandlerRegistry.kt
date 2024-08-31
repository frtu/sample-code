package com.github.frtu.sample.bot.slack.core

import com.slack.api.bolt.handler.BoltEventHandler
import com.slack.api.model.event.Event
import jakarta.annotation.PostConstruct
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.javaType
import kotlin.reflect.typeOf
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class SlackEventHandlerRegistry(
    boltEventHandlers: List<Pair<Class<*>, BoltEventHandler<*>>> = emptyList()
) {
    private val registry: Map<Class<*>, BoltEventHandler<*>> = boltEventHandlers.toMap()

    @PostConstruct
    fun init() {
        registry.entries.forEach { (eventType, handler) ->
            logger.info("Registered event handler for [${eventType.simpleName}] for type ${handler.javaClass}")
        }
    }

    fun getAll(): Set<Map.Entry<Class<*>, BoltEventHandler<*>>> = registry.entries

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}