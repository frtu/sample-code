package com.github.frtu.sample.bot.slack.core

import com.slack.api.bolt.handler.builtin.SlashCommandHandler
import org.springframework.stereotype.Repository

@Repository
class SlackCommandRegistry(
    private val registry: MutableMap<String, SlashCommandHandler> = mutableMapOf()
) {
    fun getAll() = registry.entries

    operator fun get(name: String) = registry[name]
}