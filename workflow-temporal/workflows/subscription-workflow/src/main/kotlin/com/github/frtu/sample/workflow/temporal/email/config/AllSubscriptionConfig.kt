package com.github.frtu.sample.workflow.temporal.subscription.config

import com.github.frtu.sample.workflow.temporal.subscription.source.sync.RouterConfig
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(TemporalConfig::class, RouterConfig::class)
@ComponentScan("com.github.frtu.sample.workflow.temporal.subscription.domain.service")
class AllSubscriptionConfig