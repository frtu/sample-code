package com.github.frtu.sample.workflow.temporal.reminder.domain.workflow

import com.github.frtu.logs.core.RpcLogger.requestBody
import com.github.frtu.logs.core.StructuredLogger
import com.github.frtu.logs.core.StructuredLogger.*
import com.github.frtu.sample.workflow.temporal.email.activity.Email
import com.github.frtu.sample.workflow.temporal.email.activity.EmailSinkActivity
import com.github.frtu.sample.workflow.temporal.email.activity.EmailSinkActivity.Companion.TASK_QUEUE_EMAIL
import com.github.frtu.sample.workflow.temporal.reminder.domain.ReminderEvent
import com.github.frtu.sample.workflow.temporal.reminder.domain.ReminderStatus
import io.temporal.activity.ActivityOptions
import io.temporal.common.RetryOptions
import io.temporal.workflow.Workflow
import java.time.Duration

class ReminderWorkflowImpl : ReminderWorkflow {
    private lateinit var reminderEvent: ReminderEvent
    private var reminderStatus: ReminderStatus = ReminderStatus.RUNNING

    override fun queryStatus(): ReminderStatus = reminderStatus

    override fun startReminder(reminderEvent: ReminderEvent) {
        this.reminderEvent = reminderEvent
        val reminderId = reminderEvent.id
        structuredLogger.info(flowId(reminderId), phase("STARTING_EMAIL"), requestBody(reminderEvent))

        val stopCondition = { reminderStatus == ReminderStatus.DONE }
        while (stopCondition.invoke()) {
            sendEmailActivity.emit(
                Email(
                    subject = "Confirmation of Subscription ID : $reminderId",
                    content = reminderEvent.data,
                    id = reminderId,
                    createdTimeMillis = reminderEvent.timeMillis,
                )
            )
            structuredLogger.info(flowId(reminderId), key("user_id", reminderEvent.userId), phase("SEND_EMAIL"))
            Workflow.await(Duration.ofSeconds(1), stopCondition)
        }
    }

    override fun finalize() {
        reminderStatus = ReminderStatus.DONE
        structuredLogger.info(flowId(reminderEvent.id), phase("STOP_REMINDER"))
    }

    private val sendEmailActivity = Workflow.newActivityStub(
        EmailSinkActivity::class.java,
        ActivityOptions {
            // ActivityOptions DSL
            setTaskQueue(TASK_QUEUE_EMAIL)
            setStartToCloseTimeout(Duration.ofSeconds(5)) // Timeout options specify when to automatically timeout Activities if the process is taking too long.
            // Temporal retries failures by default, this is simply an example.
            setRetryOptions(// RetryOptions specify how to automatically handle retries when Activities fail.
                RetryOptions {
                    setInitialInterval(Duration.ofMillis(100))
                    setMaximumInterval(Duration.ofSeconds(10))
                    setBackoffCoefficient(2.0)
                    setMaximumAttempts(10)
                })
        },
        // ActivityStubs enable calls to methods as if the Activity object is local, but actually perform an RPC.
        mapOf(
            REMINDER to ActivityOptions { setHeartbeatTimeout(Duration.ofSeconds(5)) }
        )
    )

    private val logger = Workflow.getLogger(this::class.java)
    private val structuredLogger = StructuredLogger.create(logger)

    companion object {
        private const val REMINDER = "Reminder"
    }
}