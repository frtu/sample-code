package com.github.frtu.sample.workflow.temporal.subscription.domain.workflow

import com.github.frtu.sample.workflow.temporal.subscription.domain.SubscriptionEvent
import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

@WorkflowInterface
interface SubscriptionWorkflow {
    companion object {
        const val TASK_QUEUE = "TASK_QUEUE_SUBSCRIPTION"
    }

    @WorkflowMethod
    fun start(subscriptionEvent: SubscriptionEvent)
}
