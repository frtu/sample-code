package com.github.frtu.sample.workflow.temporal.domain

import com.github.frtu.sample.workflow.temporal.activity.TASK_QUEUE_SEND_EMAIL
import io.temporal.client.WorkflowClient
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.worker.WorkerFactory
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.TopicBuilder

/**
 * Based on :
 * @see <a href="https://docs.spring.io/spring-kafka/reference/html/#introduction">Spring Kafka</a>
 * @author Frédéric TU
 */
@SpringBootApplication
class ProducerApplication {
    @Autowired
    lateinit var sendEmailActivityImpl: SendEmailActivityImpl

    @Bean
    fun topicSource(): NewTopic? {
        return TopicBuilder.name(sendEmailActivityImpl.outputSource)
            .partitions(10)
            .replicas(1)
            .build()
    }

    @Bean
    fun runner(): ApplicationRunner? = ApplicationRunner { args: ApplicationArguments? ->
        // WorkflowServiceStubs is a gRPC stubs wrapper that talks to the local Docker instance of the Temporal server.
        val service = WorkflowServiceStubs.newInstance()
        val client = WorkflowClient.newInstance(service)

        // Worker factory is used to create Workers that poll specific Task Queues.
        val factory = WorkerFactory.newInstance(client)
        val worker = factory.newWorker(TASK_QUEUE_SEND_EMAIL)
        // Activities are stateless and thread safe so a shared instance is used.
        worker.registerActivitiesImplementations(sendEmailActivityImpl)
        // Start listening to the Task Queue.
        factory.start()

        sendEmailActivityImpl.send("domain-message")
    }
}

fun main(args: Array<String>) {
    System.getProperties().put("server.port", 8083);
    runApplication<ProducerApplication>(*args)
}