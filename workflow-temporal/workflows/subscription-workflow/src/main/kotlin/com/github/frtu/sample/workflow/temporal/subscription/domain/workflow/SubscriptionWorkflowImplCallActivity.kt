package com.github.frtu.sample.workflow.temporal.subscription.domain.workflow

import com.github.frtu.logs.core.RpcLogger.requestBody
import com.github.frtu.logs.core.RpcLogger.responseBody
import com.github.frtu.logs.core.StructuredLogger
import com.github.frtu.logs.core.StructuredLogger.flowId
import com.github.frtu.logs.core.StructuredLogger.phase
import com.github.frtu.sample.workflow.temporal.activity.bank.BankingActivity
import com.github.frtu.sample.workflow.temporal.activity.bank.PaymentDetails
import com.github.frtu.sample.workflow.temporal.subscription.domain.SubscriptionEvent
import io.temporal.activity.ActivityOptions
import io.temporal.common.RetryOptions
import io.temporal.workflow.Workflow
import java.time.Duration
import java.util.UUID

class SubscriptionWorkflowImplCallActivity : SubscriptionWorkflow {
    private lateinit var subscriptionEvent: SubscriptionEvent

    override fun start(subscriptionEvent: SubscriptionEvent) {
        this.subscriptionEvent = subscriptionEvent
        val id = subscriptionEvent.id
        structuredLogger.info(flowId(id), phase("STARTING_DEPOSIT"), requestBody(subscriptionEvent))

        val sourceAccountId = UUID.randomUUID()
        val targetAccountId = UUID.randomUUID()
        val confirmation = bankingActivity.deposit(
            PaymentDetails(
                sourceAccount = sourceAccountId.toString(),
                targetAccount = targetAccountId.toString(),
                amount = 10,
                referenceId = subscriptionEvent.data,
            )
        )
        structuredLogger.info(flowId(id), phase("DEPOSIT_SUCCEEDED"), responseBody(confirmation))
    }

    private val bankingActivity = Workflow.newActivityStub(
        BankingActivity::class.java,
        ActivityOptions {
            // ActivityOptions DSL
            setTaskQueue(BankingActivity.TASK_QUEUE)
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
    )

    private val logger = Workflow.getLogger(this::class.java)
    private val structuredLogger = StructuredLogger.create(logger)
}