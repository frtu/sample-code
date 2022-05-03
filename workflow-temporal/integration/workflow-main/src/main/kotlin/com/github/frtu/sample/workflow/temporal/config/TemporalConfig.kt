package com.github.frtu.sample.workflow.temporal.config

import com.github.frtu.sample.workflow.temporal.workflow.MessageWorkflow
import com.github.frtu.sample.workflow.temporal.workflow.MessageWorkflowImpl
import com.github.frtu.sample.workflow.temporal.workflow.TASK_QUEUE_MESSAGE
import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowOptions
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.worker.Worker
import io.temporal.worker.WorkerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.github.frtu.sample.workflow.temporal.workflow")
class TemporalConfig {
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String? = null

    @Bean
    fun workflowClient(): WorkflowClient {
        // WorkflowServiceStubs is a gRPC stubs wrapper that talks to the local Docker instance of the Temporal server.
        val service = WorkflowServiceStubs.newInstance()
        // WorkflowClient can be used to start, signal, query, cancel, and terminate Workflows.
        return WorkflowClient.newInstance(service)
    }

    @Bean
    fun messageWorkflow(workflowClient: WorkflowClient): MessageWorkflow {
        val options = WorkflowOptions.newBuilder()
            .setTaskQueue(TASK_QUEUE_MESSAGE) // A WorkflowId prevents this it from having duplicate instances, remove it to duplicate.
            .setWorkflowId("message-workflow4")
            .build()

        // WorkflowStubs enable calls to methods as if the Workflow object is local, but actually perform an RPC.
        return workflowClient.newWorkflowStub(MessageWorkflow::class.java, options)
    }

    @Bean
    fun worker(workflowClient: WorkflowClient): Worker {
        // Worker factory is used to create Workers that poll specific Task Queues.
        val factory = WorkerFactory.newInstance(workflowClient)
        val worker = factory.newWorker(TASK_QUEUE_MESSAGE)
        // This Worker hosts both Workflow and Activity implementations.
        // Workflows are stateful so a type is needed to create instances.
        worker.registerWorkflowImplementationTypes(MessageWorkflowImpl::class.java)
        // Start listening to the Task Queue.
        factory.start()
        return worker
    }
}