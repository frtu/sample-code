package com.github.frtu.sample.workflow.temporal.workflow

import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

@WorkflowInterface
interface MessageWorkflow {
    @WorkflowMethod
    fun emit(message: String)
}

const val TASK_QUEUE_MESSAGE = "TASK_QUEUE_MESSAGE"
