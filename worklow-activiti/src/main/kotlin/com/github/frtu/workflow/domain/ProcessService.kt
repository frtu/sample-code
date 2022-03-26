package com.github.frtu.workflow.domain

import com.github.frtu.workflow.model.EmployeeRepository
import org.activiti.engine.RepositoryService
import org.activiti.engine.RuntimeService
import org.activiti.engine.TaskService
import org.activiti.engine.task.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.function.Consumer

@Service
class ProcessService() {
    @Autowired
    private val employeeRepository: EmployeeRepository? = null

    @Autowired
    private val runtimeService: RuntimeService? = null

    @Autowired
    private val taskService: TaskService? = null

    @Autowired
    private val repositoryService: RepositoryService? = null

    // start the process and set employee as variable
    fun startTheProcess(assignee: String?): String {
        val employee = employeeRepository!!.findByName(assignee!!)
        val variables: MutableMap<String, Any> = HashMap()
        variables["employee"] = employee
        runtimeService!!.startProcessInstanceByKey("simple-process", variables)
        return processInformation()
    }

    // fetching process and task information
    fun processInformation(): String {
        val taskList = taskService!!.createTaskQuery().orderByTaskCreateTime().asc().list()
        val processAndTaskInfo = StringBuilder()
        processAndTaskInfo.append(
            "Number of process definition available: "
                    + repositoryService!!.createProcessDefinitionQuery().count() + " | Task Details= "
        )
        taskList.forEach(Consumer { task: Task ->
            processAndTaskInfo.append(
                ("ID: " + task.getId() + ", Name: " + task.getName() + ", Assignee: "
                        + task.getAssignee() + ", Description: " + task.getDescription())
            )
        })
        return processAndTaskInfo.toString()
    }

    // fetch task assigned to employee
    fun getTasks(assignee: String?): List<Task> {
        return taskService!!.createTaskQuery().taskAssignee(assignee).list()
    }

    // complete the task
    fun completeTask(taskId: String?) {
        taskService!!.complete(taskId)
    }
}