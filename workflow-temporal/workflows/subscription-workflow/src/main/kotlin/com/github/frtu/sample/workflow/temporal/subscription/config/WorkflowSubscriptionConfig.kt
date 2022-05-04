package com.github.frtu.sample.workflow.temporal.subscription.config

import com.github.frtu.sample.workflow.temporal.subscription.domain.workflow.SubscriptionWorkflowImpl
import com.github.frtu.sample.workflow.temporal.subscription.domain.workflow.TASK_QUEUE_SUBSCRIPTION
import io.temporal.client.WorkflowClient
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.worker.Worker
import io.temporal.worker.WorkerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(TemporalConfig::class)
@ComponentScan("com.github.frtu.sample.workflow.temporal.subscription.domain.workflow")
class WorkflowSubscriptionConfig {
    @Bean
    fun worker(factory: WorkerFactory): Worker {
        val worker = factory.newWorker(TASK_QUEUE_SUBSCRIPTION)
        // This Worker hosts both Workflow and Activity implementations.
        // Workflows are stateful so a type is needed to create instances.
        worker.registerWorkflowImplementationTypes(SubscriptionWorkflowImpl::class.java)
        // Start listening to the Task Queue.
        factory.start()
        return worker
    }
}