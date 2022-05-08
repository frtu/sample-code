package com.github.frtu.workflow.temporal.config

import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowClientOptions
import io.temporal.common.interceptors.WorkerInterceptor
import io.temporal.common.interceptors.WorkflowClientInterceptor
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
    fun workflowClient(service: WorkflowServiceStubs, workflowClientInterceptors: List<WorkflowClientInterceptor>): WorkflowClient =
        WorkflowClient.newInstance(service, WorkflowClientOptions {
            setInterceptors(*workflowClientInterceptors.toTypedArray())
        })

    @Bean
    fun workerFactory(client: WorkflowClient, workerInterceptors: List<WorkerInterceptor>): WorkerFactory =
        WorkerFactory.newInstance(client, WorkerFactoryOptions {
            setWorkerInterceptors(*workerInterceptors.toTypedArray())
        })
}