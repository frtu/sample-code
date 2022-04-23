package com.github.frtu.sample.workflow.async.service

import com.github.frtu.sample.workflow.async.Application
import com.github.frtu.sample.workflow.async.config.KafkaConfiguration
import org.activiti.engine.RepositoryService
import org.activiti.engine.RuntimeService
import org.activiti.engine.TaskService
import org.slf4j.LoggerFactory
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

@Service
class MessageConsumer(
    private val repositoryService: RepositoryService,
    private val runtimeService: RuntimeService,
    private val taskService: TaskService,
) {
    @ServiceActivator(inputChannel = KafkaConfiguration.KAFKA_INPUT)
    fun print(message: Message<*>) {
        logger.info("Received: ${message.payload}")

        logger.info("Number of process definitions : ${repositoryService.createProcessDefinitionQuery().count()}")
        logger.info("Number of tasks : ${taskService.createTaskQuery().count()}")
        runtimeService.startProcessInstanceByKey(Application.PROCESS_NAME, mapOf("assignee" to "Fred"))
        logger.info("Number of tasks after process start: ${taskService.createTaskQuery().count()}")
    }

    private val logger = LoggerFactory.getLogger(this::class.java)
}