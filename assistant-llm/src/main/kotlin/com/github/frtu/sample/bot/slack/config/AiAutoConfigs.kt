package com.github.frtu.sample.bot.slack.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * Allow to bootstrap AI OS configuration
 */
@Configuration
@ComponentScan("com.github.frtu.kotlin.ai.os")
@Import(
    ChatApiConfigs::class,
)
class AiAutoConfigs