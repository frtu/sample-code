package com.github.frtu.sample.workflow.temporal.subscription.config

import com.github.frtu.sample.workflow.temporal.email.activity.TASK_QUEUE_EMAIL
import com.github.frtu.sample.workflow.temporal.subscription.domain.workflow.SubscriptionWorkflow
import com.github.frtu.sample.workflow.temporal.subscription.domain.workflow.SubscriptionWorkflowImpl
import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowOptions
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.worker.Worker
import io.temporal.worker.WorkerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.github.frtu.sample.workflow.temporal.subscription.domain.workflow")
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
    fun messageWorkflow(workflowClient: WorkflowClient): SubscriptionWorkflow {
        val options = WorkflowOptions.newBuilder()
            .setTaskQueue(taskQueue) // A WorkflowId prevents this it from having duplicate instances, remove it to duplicate.
            .build()

        // WorkflowStubs enable calls to methods as if the Workflow object is local, but actually perform an RPC.
        return workflowClient.newWorkflowStub(SubscriptionWorkflow::class.java, options)
    }

    @Bean
    fun worker(workflowClient: WorkflowClient): Worker {
        // Worker factory is used to create Workers that poll specific Task Queues.
        val factory = WorkerFactory.newInstance(workflowClient)
        val worker = factory.newWorker(taskQueue)
        // This Worker hosts both Workflow and Activity implementations.
        // Workflows are stateful so a type is needed to create instances.
        worker.registerWorkflowImplementationTypes(SubscriptionWorkflowImpl::class.java)
        // Start listening to the Task Queue.
        factory.start()
        return worker
    }
}