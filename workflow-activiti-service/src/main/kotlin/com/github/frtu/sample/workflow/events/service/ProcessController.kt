package com.github.frtu.sample.workflow.events.service

import org.activiti.engine.RepositoryService
import org.activiti.engine.RuntimeService
import org.activiti.engine.TaskService
import org.activiti.engine.task.Task
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/processes")
class ProcessController(
    val runtimeService: RuntimeService,
    val repositoryService: RepositoryService,
    val taskService: TaskService,
) {
    @GetMapping
    fun findAll() = runtimeService.createProcessInstanceQuery()
        .orderByProcessInstanceId()
        .desc()
        .list().map { e ->
            e.id
        }

    @PostMapping
    fun startProcess(@RequestBody assignee: String): String {
        val variables: MutableMap<String, Any> = HashMap()
        variables["assignee"] = assignee
        val processInstance = runtimeService.startProcessInstanceByKey("myProcess", variables)
        return ("Process [${processInstance.id}] started! Nb process instances = ${
            runtimeService.createProcessInstanceQuery().count()
        }")
    }

    @GetMapping("{processInstanceId}/tasks")
    fun findAll(@PathVariable processInstanceId: String): MutableList<TaskVO> {
        val usertasks: List<Task> = taskService.createTaskQuery()
            .processInstanceId(processInstanceId)
            .list()
        return usertasks.stream()
            .map { task: Task ->
                TaskVO(task.id, task.name, task.processInstanceId)
            }
            .collect(Collectors.toList())
    }

    @GetMapping("assignee/{assignee}")
    fun findAllByAssignee(@PathVariable assignee: String): MutableList<Task> {
        return taskService.createTaskQuery()
            .taskAssignee(assignee)
            .list()
    }

    @PostMapping("{processInstanceId}/complete")
    fun complete(
        @PathVariable processInstanceId: String,
        @RequestBody assignee: String
    ) {
        val task = taskService.createTaskQuery()
            .processInstanceId(processInstanceId)
            .singleResult()
        taskService.complete(task.id)
        logger.info("Task completed")
    }

    private val logger = LoggerFactory.getLogger(this::class.java)
}