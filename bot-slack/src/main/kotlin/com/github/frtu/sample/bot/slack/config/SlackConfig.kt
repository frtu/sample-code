package com.github.frtu.sample.bot.slack.config

import com.github.frtu.sample.bot.slack.core.SlackAppProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(SlackAppProperties::class)
class SlackConfig