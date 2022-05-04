package com.github.frtu.sample.workflow.temporal.email.config

import com.github.frtu.sample.workflow.temporal.email.activity.EmailSinkActivity
import com.github.frtu.sample.workflow.temporal.email.activity.TASK_QUEUE_EMAIL
import io.temporal.client.WorkflowClient
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.worker.Worker
import io.temporal.worker.WorkerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.github.frtu.sample.workflow.temporal.email.activity")
class TemporalConfig {
    val taskQueue = TASK_QUEUE_EMAIL

    @Bean
    fun workflowClient(): WorkflowClient {
        // WorkflowServiceStubs is a gRPC stubs wrapper that talks to the local Docker instance of the Temporal server.
        val service = WorkflowServiceStubs.newInstance()
        // WorkflowClient can be used to start, signal, query, cancel, and terminate Workflows.
        return WorkflowClient.newInstance(service)
    }

    @Bean
    fun worker(workflowClient: WorkflowClient, emailSinkActivity: EmailSinkActivity): Worker {
        // Worker factory is used to create Workers that poll specific Task Queues.
        val factory = WorkerFactory.newInstance(workflowClient)
        val worker = factory.newWorker(taskQueue)
        // This Worker hosts Activity implementations.
        // Activities are stateless and thread safe so a shared instance is used.
        worker.registerActivitiesImplementations(emailSinkActivity)
        // Start listening to the Task Queue.
        factory.start()
        return worker
    }
}