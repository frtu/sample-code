package com.github.frtu.sample.workflow.temporal.asyncchild

import io.temporal.workflow.Workflow
import java.time.Duration

class ChildWorkflowImpl : ChildWorkflow {
    override fun executeChild(): String {
        Workflow.sleep(Duration.ofSeconds(3))
        return "Child workflow done"
    }
}