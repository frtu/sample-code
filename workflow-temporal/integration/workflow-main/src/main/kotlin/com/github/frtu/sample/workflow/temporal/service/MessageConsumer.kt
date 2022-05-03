package com.github.frtu.sample.workflow.temporal.service

import com.github.frtu.sample.workflow.temporal.config.KafkaConfiguration
import com.github.frtu.sample.workflow.temporal.workflow.MessageWorkflow
import io.temporal.client.WorkflowClient
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

@Service
class MessageConsumer(private val messageWorkflow: MessageWorkflow) {
    @ServiceActivator(inputChannel = KafkaConfiguration.KAFKA_INPUT)
    fun print(message: Message<*>) {
        logger.info("\nmessage($message)\n")
        message("Received: ${message.payload}")
    }

    @StreamListener(Sink.INPUT)
    fun handle(message: String) {
        logger.info("\nhandle($message)\n")
        message("Received: $message")
    }

    fun message(message: String) {
        // Asynchronous execution. This process will exit after making this call.
        val we = WorkflowClient.start(messageWorkflow::emit, message)
        logger.info("\nMessage $message\n")
        logger.info("\nWorkflowID: ${we.workflowId} RunID: ${we.runId}")
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}