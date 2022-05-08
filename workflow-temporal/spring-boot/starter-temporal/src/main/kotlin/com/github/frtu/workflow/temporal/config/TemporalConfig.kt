package com.github.frtu.workflow.temporal.config

import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowClientOptions
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.worker.WorkerFactory
import io.temporal.worker.WorkerFactoryOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TemporalConfig {
    @Bean
    fun workflowServiceStubs(): WorkflowServiceStubs = WorkflowServiceStubs.newInstance()

    @Bean
    fun workflowClient(service: WorkflowServiceStubs): WorkflowClient {
        return WorkflowClient.newInstance(service, WorkflowClientOptions {
        })
    }

    @Bean
    fun workerFactory(client: WorkflowClient): WorkerFactory {
        return WorkerFactory.newInstance(client, WorkerFactoryOptions {
        })
    }
}