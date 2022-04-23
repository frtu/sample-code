package com.github.frtu.sample.workflow.temporal.moneytransferapp

import io.temporal.activity.ActivityOptions
import io.temporal.common.RetryOptions
import io.temporal.workflow.Workflow
import java.time.Duration
import java.util.*

class MoneyTransferWorkflowImpl : MoneyTransferWorkflow {
    // RetryOptions specify how to automatically handle retries when Activities fail.
    private val retryoptions = RetryOptions.newBuilder()
        .setInitialInterval(Duration.ofSeconds(1))
        .setMaximumInterval(Duration.ofSeconds(100))
        .setBackoffCoefficient(2.0)
        .setMaximumAttempts(500)
        .build()

    private val defaultActivityOptions =
        ActivityOptions.newBuilder() // Timeout options specify when to automatically timeout Activities if the process is taking too long.
            .setStartToCloseTimeout(Duration.ofSeconds(5)) // Optionally provide customized RetryOptions.
            // Temporal retries failures by default, this is simply an example.
            .setRetryOptions(retryoptions)
            .build()

    // ActivityStubs enable calls to methods as if the Activity object is local, but actually perform an RPC.
    private val perActivityMethodOptions: Map<String, ActivityOptions> = HashMap<String, ActivityOptions>().apply {
        put(WITHDRAW, ActivityOptions.newBuilder().setHeartbeatTimeout(Duration.ofSeconds(5)).build())
    }

    private val account =
        Workflow.newActivityStub(AccountActivity::class.java, defaultActivityOptions, perActivityMethodOptions)

    // The transfer method is the entry point to the Workflow.
    // Activity method executions can be orchestrated here or from within other Activity methods.
    override fun transfer(fromAccountId: String, toAccountId: String, referenceId: String, amount: Double) {
        account.withdraw(fromAccountId!!, referenceId!!, amount)
        account.deposit(toAccountId!!, referenceId, amount)
    }

    companion object {
        private const val WITHDRAW = "Withdraw"
    }
}