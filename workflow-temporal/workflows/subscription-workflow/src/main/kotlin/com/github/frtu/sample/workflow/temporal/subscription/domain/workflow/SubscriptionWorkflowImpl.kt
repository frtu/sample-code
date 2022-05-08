package com.github.frtu.sample.workflow.temporal.subscription.domain.workflow

import com.github.frtu.logs.core.StructuredLogger
import com.github.frtu.logs.core.StructuredLogger.flowId
import com.github.frtu.logs.core.StructuredLogger.phase
import com.github.frtu.sample.workflow.temporal.email.activity.Email
import com.github.frtu.sample.workflow.temporal.email.activity.EmailSinkActivity
import com.github.frtu.sample.workflow.temporal.email.activity.TASK_QUEUE_EMAIL
import com.github.frtu.sample.workflow.temporal.subscription.domain.SubscriptionEvent
import io.temporal.activity.ActivityOptions
import io.temporal.common.RetryOptions
import io.temporal.workflow.Workflow
import java.time.Duration

class SubscriptionWorkflowImpl : SubscriptionWorkflow {
    // RetryOptions specify how to automatically handle retries when Activities fail.
    private val retryOptions = RetryOptions {
        setInitialInterval(Duration.ofMillis(100))
        setMaximumInterval(Duration.ofSeconds(10))
        setBackoffCoefficient(2.0)
        setMaximumAttempts(10)
    }

    private val sendEmailActivity = Workflow.newActivityStub(
        EmailSinkActivity::class.java,
        ActivityOptions {
            // ActivityOptions DSL
            setTaskQueue(TASK_QUEUE_EMAIL)
            setStartToCloseTimeout(Duration.ofSeconds(5)) // Timeout options specify when to automatically timeout Activities if the process is taking too long.
            // Temporal retries failures by default, this is simply an example.
            setRetryOptions(retryOptions)
        },
        // ActivityStubs enable calls to methods as if the Activity object is local, but actually perform an RPC.
        mapOf(
            SUBSCRIPTION to ActivityOptions { setHeartbeatTimeout(Duration.ofSeconds(5)) }
        )
    )

    override fun start(subscriptionEvent: SubscriptionEvent) {
        val subscriptionId = subscriptionEvent.id

        structuredLogger.info(flowId(subscriptionId), phase("CONFIRMATION_EMAIL"))
        sendEmailActivity.emit(
            Email(
                subject = "Confirmation of Subscription ID : $subscriptionId",
                content = subscriptionEvent.data,
                id = subscriptionId,
                createdTimeMillis = subscriptionEvent.timeMillis,
            )
        )
        structuredLogger.info(flowId(subscriptionId), phase("CONFIRMATION_EMAIL_DONE"))
    }

    private val structuredLogger = StructuredLogger.create(this::class.java)
    private val logger = Workflow.getLogger(this::class.java)

    companion object {
        private const val SUBSCRIPTION = "Subscription"
    }
}