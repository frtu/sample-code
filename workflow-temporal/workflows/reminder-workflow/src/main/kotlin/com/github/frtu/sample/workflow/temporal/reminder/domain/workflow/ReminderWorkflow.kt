package com.github.frtu.sample.workflow.temporal.reminder.domain.workflow

import com.github.frtu.sample.workflow.temporal.reminder.domain.ReminderEvent
import com.github.frtu.sample.workflow.temporal.reminder.domain.ReminderStatus
import io.temporal.workflow.QueryMethod
import io.temporal.workflow.SignalMethod
import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

@WorkflowInterface
interface ReminderWorkflow {
    companion object {
        const val TASK_QUEUE = "TASK_QUEUE_REMINDER"
    }

    @WorkflowMethod
    fun startReminder(reminderEvent: ReminderEvent)

    @QueryMethod
    fun queryStatus(): ReminderStatus

    @SignalMethod
    fun finalize()
}
