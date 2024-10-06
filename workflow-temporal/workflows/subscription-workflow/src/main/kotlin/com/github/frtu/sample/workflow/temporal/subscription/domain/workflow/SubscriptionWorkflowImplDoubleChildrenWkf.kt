package com.github.frtu.sample.workflow.temporal.subscription.domain.workflow

import com.github.frtu.logs.core.RpcLogger.requestBody
import com.github.frtu.logs.core.RpcLogger.responseBody
import com.github.frtu.logs.core.StructuredLogger
import com.github.frtu.logs.core.StructuredLogger.*
import com.github.frtu.sample.workflow.temporal.reminder.domain.ReminderEvent
import com.github.frtu.sample.workflow.temporal.reminder.domain.workflow.ReminderWorkflow
import com.github.frtu.sample.workflow.temporal.reminder.domain.workflow.ReminderWorkflow.Companion.TASK_QUEUE_REMINDER
import com.github.frtu.sample.workflow.temporal.subscription.domain.SubscriptionEvent
import io.temporal.api.enums.v1.ParentClosePolicy
import io.temporal.api.enums.v1.WorkflowIdReusePolicy
import io.temporal.common.RetryOptions
import io.temporal.workflow.Async
import io.temporal.workflow.ChildWorkflowOptions
import io.temporal.workflow.Workflow
import java.time.Duration
import java.util.UUID

class SubscriptionWorkflowImplDoubleChildrenWkf : SubscriptionWorkflow {
    override fun start(subscriptionEvent: SubscriptionEvent) {
        // Fetch users & startReminder to all of them
        val childReminderWorkflowList = fetchUserIds(subscriptionEvent)
            .map {
                val reminderEvent = subscriptionEvent.toReminderEvent(it)
                structuredLogger.info(flowId(subscriptionEvent.id), key("user_id", it), phase("STARTING_REMINDER"), requestBody(reminderEvent))
                val childReminderWorkflow = buildReminderWorkflow()
                Async.function(childReminderWorkflow::startReminder, reminderEvent).get()
                childReminderWorkflow
            }.toCollection(mutableListOf())

        structuredLogger.info(flowId(subscriptionEvent.id), phase("STARTED_REMINDER"), responseBody(childReminderWorkflowList.size))
        // Do some tasks
        Workflow.sleep(3_000)

        // Release them
//        childReminderWorkflowList.forEach {
//            Async.function(it::finalize).get()
//        }
        structuredLogger.info(flowId(subscriptionEvent.id), phase("REMINDER_STARTED"))
    }

    private fun buildReminderWorkflow() = Workflow.newChildWorkflowStub(
        ReminderWorkflow::class.java,
        ChildWorkflowOptions {
            setTaskQueue(TASK_QUEUE_REMINDER)
            setParentClosePolicy(ParentClosePolicy.PARENT_CLOSE_POLICY_ABANDON)
            setWorkflowIdReusePolicy(WorkflowIdReusePolicy.WORKFLOW_ID_REUSE_POLICY_ALLOW_DUPLICATE)
            setRetryOptions(// RetryOptions specify how to automatically handle retries when Activities fail.
                RetryOptions {
                    setInitialInterval(Duration.ofMillis(100))
                    setMaximumInterval(Duration.ofSeconds(1))
                    setBackoffCoefficient(2.0)
                    setMaximumAttempts(10)
                })
        },
    )

    private fun fetchUserIds(subscriptionEvent: SubscriptionEvent) = (1..2)
        .map { UUID.randomUUID() }
        .toCollection(mutableListOf())

    private val logger = Workflow.getLogger(this::class.java)
    private val structuredLogger = StructuredLogger.create(logger)
}

fun SubscriptionEvent.toReminderEvent(userId: UUID) = ReminderEvent(
    userId = userId,
    data = this.data,
    type = this.type,
    id = this.id,
    timeMillis = this.timeMillis,
    metadata = this.metadata,
)