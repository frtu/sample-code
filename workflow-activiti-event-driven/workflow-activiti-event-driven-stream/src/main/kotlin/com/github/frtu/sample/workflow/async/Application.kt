package com.github.frtu.sample.workflow.async

import org.activiti.engine.RepositoryService
import org.activiti.engine.RuntimeService
import org.activiti.engine.TaskService
import org.aspectj.util.LangUtil
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.context.properties.bind.Bindable.mapOf
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.context.annotation.Bean

@EnableConfigurationProperties(AppProperties::class)
@SpringBootApplication
@EnableBinding(Sink::class)
//@EnableR2dbcRepositories
class Application {
    @StreamListener(Sink.INPUT)
    fun handle(message: String) {
        logger.info("Received: $message")
    }

    @Bean
    fun initializer(
        repositoryService: RepositoryService,
        runtimeService: RuntimeService,
        taskService: TaskService,
    ): CommandLineRunner = CommandLineRunner {
        logger.info("Number of process definitions : ${repositoryService.createProcessDefinitionQuery().count()}")
        logger.info("Number of tasks : ${taskService.createTaskQuery().count()}")
        runtimeService.startProcessInstanceByKey(PROCESS_NAME, mapOf("assignee" to "Fred"))
        logger.info("Number of tasks after process start: ${taskService.createTaskQuery().count()}")
    }

    companion object {
        const val PROCESS_NAME = "myProcess"
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}