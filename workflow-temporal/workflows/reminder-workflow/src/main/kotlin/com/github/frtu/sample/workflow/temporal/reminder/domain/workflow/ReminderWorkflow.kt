package com.github.frtu.sample.workflow.temporal.reminder.domain.workflow

import com.github.frtu.sample.workflow.temporal.reminder.domain.ReminderEvent
import com.github.frtu.sample.workflow.temporal.reminder.domain.ReminderStatus
import io.temporal.workflow.QueryMethod
import io.temporal.workflow.SignalMethod
import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

@WorkflowInterface
interface ReminderWorkflow {
    @WorkflowMethod
    fun startReminder(reminderEvent: ReminderEvent)

    @QueryMethod
    fun queryStatus(): ReminderStatus

    @SignalMethod
    fun finalize()
}

const val TASK_QUEUE_REMINDER = "TASK_QUEUE_REMINDER"
