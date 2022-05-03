package com.github.frtu.sample.workflow.temporal.domain.workflow

import com.github.frtu.logs.core.StructuredLogger
import com.github.frtu.logs.core.StructuredLogger.flowId
import com.github.frtu.logs.core.StructuredLogger.phase
import com.github.frtu.sample.workflow.temporal.activity.Email
import com.github.frtu.sample.workflow.temporal.activity.SendEmailActivity
import com.github.frtu.sample.workflow.temporal.domain.SubscriptionEvent
import io.temporal.activity.ActivityOptions
import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowOptions
import io.temporal.common.RetryOptions
import io.temporal.workflow.Workflow
import org.springframework.context.annotation.Bean
import java.time.Duration

class SubscriptionWorkflowImpl : SubscriptionWorkflow {
    // RetryOptions specify how to automatically handle retries when Activities fail.
    private val retryOptions = RetryOptions {
        setInitialInterval(Duration.ofMillis(100))
        setMaximumInterval(Duration.ofSeconds(10))
        setBackoffCoefficient(2.0)
        setMaximumAttempts(10)
    }

    private val defaultActivityOptions = ActivityOptions {
        // ActivityOptions DSL
        setTaskQueue("TestQueue")
        setStartToCloseTimeout(Duration.ofSeconds(5)) // Timeout options specify when to automatically timeout Activities if the process is taking too long.
        // Temporal retries failures by default, this is simply an example.
        setRetryOptions(retryOptions)
    }

    // ActivityStubs enable calls to methods as if the Activity object is local, but actually perform an RPC.
    private val perActivityMethodOptions: Map<String, ActivityOptions> = mapOf<String, ActivityOptions>(
        SUBSCRIPTION to ActivityOptions { setHeartbeatTimeout(Duration.ofSeconds(5)) }
    )

    private val sendEmailActivity =
        Workflow.newActivityStub(SendEmailActivity::class.java, defaultActivityOptions, perActivityMethodOptions)

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
    }

    private val structuredLogger = StructuredLogger.create(this::class.java)

    companion object {
        private const val SUBSCRIPTION = "Subscription"
    }
}