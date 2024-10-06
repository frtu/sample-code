package com.github.frtu.sample.workflow.temporal.subscription.config

import com.github.frtu.sample.workflow.temporal.reminder.domain.workflow.ReminderWorkflow.Companion.TASK_QUEUE_REMINDER
import com.github.frtu.sample.workflow.temporal.reminder.domain.workflow.ReminderWorkflowImpl
import com.github.frtu.sample.workflow.temporal.subscription.domain.workflow.SubscriptionWorkflow.Companion.TASK_QUEUE_SUBSCRIPTION
import com.github.frtu.sample.workflow.temporal.subscription.domain.workflow.SubscriptionWorkflowImplDoubleChildrenWkf
import com.github.frtu.workflow.temporal.config.ObservabilityConfig
import com.github.frtu.workflow.temporal.config.TemporalConfig
import io.temporal.worker.WorkerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(TemporalConfig::class, ObservabilityConfig::class)
@ComponentScan("com.github.frtu.sample.workflow.temporal.subscription.domain.workflow")
class WorkflowSubscriptionConfig {
    /**
     * There should ONLY have one worker per application
     */
    @Bean
    fun worker(factory: WorkerFactory): String {
        factory.newWorker(TASK_QUEUE_SUBSCRIPTION).registerWorkflowImplementationTypes(
            SubscriptionWorkflowImplDoubleChildrenWkf::class.java,
        )
        factory.newWorker(TASK_QUEUE_REMINDER).registerWorkflowImplementationTypes(
            ReminderWorkflowImpl::class.java,
        )
        // Start listening to the Task Queue.
        factory.start()
        return "STARTED"
    }
}