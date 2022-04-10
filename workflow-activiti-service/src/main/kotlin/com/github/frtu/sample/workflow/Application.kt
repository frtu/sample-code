package com.github.frtu.sample.workflow

import com.github.frtu.sample.workflow.events.service.ProcessController
import org.activiti.engine.RepositoryService
import org.activiti.engine.RuntimeService
import org.activiti.engine.TaskService
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

/**
 * https://www.activiti.org/userguide/#springSpringBoot
 */
@SpringBootApplication
class Application {
    @Bean
    fun initializer(
        repositoryService: RepositoryService,
        runtimeService: RuntimeService,
        taskService: TaskService,
    ): CommandLineRunner = CommandLineRunner {
        logger.debug("Number of process definitions : ${repositoryService.createProcessDefinitionQuery().count()}")
        logger.debug("Number of tasks : ${taskService.createTaskQuery().count()}")
        runtimeService.startProcessInstanceByKey(ProcessController.PROCESS_NAME, mapOf("assignee" to "Fred"))
        logger.debug("Number of tasks after process start: ${taskService.createTaskQuery().count()}")
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}