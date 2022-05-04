package com.github.frtu.workflow.temporal.config

import io.temporal.client.WorkflowClient
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.worker.WorkerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TemporalConfig {
    @Bean
    fun workflowServiceStubs(): WorkflowServiceStubs = WorkflowServiceStubs.newInstance()

    @Bean
    fun workflowClient(service: WorkflowServiceStubs): WorkflowClient = WorkflowClient.newInstance(service)

    @Bean
    fun workerFactory(client: WorkflowClient): WorkerFactory = WorkerFactory.newInstance(client)
}