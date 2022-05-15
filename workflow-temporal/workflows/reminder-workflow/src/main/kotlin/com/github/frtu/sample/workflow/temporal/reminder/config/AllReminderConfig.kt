package com.github.frtu.sample.workflow.temporal.reminder.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    WorkflowReminderConfig::class,
)
class AllReminderConfig