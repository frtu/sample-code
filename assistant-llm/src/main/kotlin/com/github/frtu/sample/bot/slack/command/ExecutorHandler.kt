package com.github.frtu.sample.bot.slack.command

import com.slack.api.bolt.context.builtin.SlashCommandContext
import com.slack.api.bolt.request.builtin.SlashCommandRequest
import org.slf4j.Logger

interface ExecutorHandler {
    suspend fun invoke(req: SlashCommandRequest, ctx: SlashCommandContext, logger: Logger): String?
}