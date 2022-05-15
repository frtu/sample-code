package com.github.frtu.sample.workflow.temporal.reminder.domain.workflow

import com.github.frtu.sample.workflow.temporal.reminder.domain.ReminderEvent
import io.temporal.workflow.SignalMethod
import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

@WorkflowInterface
interface ReminderWorkflow {
    @WorkflowMethod
    fun startReminder(reminderEvent: ReminderEvent)

    @SignalMethod
    fun finalize()
}

const val TASK_QUEUE_REMINDER = "TASK_QUEUE_REMINDER"
