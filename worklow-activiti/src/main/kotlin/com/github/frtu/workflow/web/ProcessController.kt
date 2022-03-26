package com.github.frtu.workflow.web

import com.github.frtu.workflow.domain.ProcessService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProcessController {
    @Autowired
    lateinit var processService: ProcessService

    /*
	 * Method will start the Activiti process engine and set employee to perform
	 * the task
	 */
    @RequestMapping(value = ["/process"])
    fun startProcessInstance(@RequestParam assignee: String?): String {
        return processService.startTheProcess(assignee)
    }

    // Retrieve the tasks assigned to an employee
    @RequestMapping(value = ["/tasks"])
    fun getTasks(@RequestParam assignee: String?): String {
        val tasks = processService.getTasks(assignee)
        return tasks.toString()
    }

    // Complete the task by their ID
    @RequestMapping(value = ["/completetask"])
    fun completeTask(@RequestParam taskId: String): String {
        processService.completeTask(taskId)
        return "Task with id $taskId has been completed!"
    }
}