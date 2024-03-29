package com.github.frtu.workflow.temporal.config

import com.github.frtu.logs.core.StructuredLogger
import com.github.frtu.logs.core.StructuredLogger.message
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
            structuredLogger.info(message("Setting workflowClientInterceptors[${workflowClientInterceptors.size}]"))
            setInterceptors(*workflowClientInterceptors.toTypedArray())
        })

    @Bean
    fun workerFactory(client: WorkflowClient, workerInterceptors: List<WorkerInterceptor>): WorkerFactory =
        WorkerFactory.newInstance(client, WorkerFactoryOptions {
            structuredLogger.info(message("Setting workerInterceptors[${workerInterceptors.size}]"))
            setWorkerInterceptors(*workerInterceptors.toTypedArray())
        })

    private val structuredLogger = StructuredLogger.create(this::class.java)
}