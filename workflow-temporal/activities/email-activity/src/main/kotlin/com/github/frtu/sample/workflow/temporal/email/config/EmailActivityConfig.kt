package com.github.frtu.sample.workflow.temporal.email.config

import com.github.frtu.sample.workflow.temporal.activity.email.EmailSinkActivity
import com.github.frtu.sample.workflow.temporal.activity.email.EmailSinkActivity.Companion.TASK_QUEUE_EMAIL
import com.github.frtu.workflow.temporal.config.TemporalConfig
import io.temporal.client.WorkflowClient
import io.temporal.worker.Worker
import io.temporal.worker.WorkerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(TemporalConfig::class)
@ComponentScan("com.github.frtu.sample.workflow.temporal.email.activity")
class EmailActivityConfig {
    @Bean
    fun worker(workflowClient: WorkflowClient, emailSinkActivity: EmailSinkActivity): Worker {
        // Worker factory is used to create Workers that poll specific Task Queues.
        val factory = WorkerFactory.newInstance(workflowClient)
        val worker = factory.newWorker(TASK_QUEUE_EMAIL)
        // This Worker hosts Activity implementations.
        // Activities are stateless and thread safe so a shared instance is used.
        worker.registerActivitiesImplementations(emailSinkActivity)
        // Start listening to the Task Queue.
        factory.start()
        return worker
    }
}