package com.github.frtu.sample.workflow.temporal.workflow

import com.github.frtu.sample.workflow.temporal.activity.Email
import com.github.frtu.sample.workflow.temporal.activity.SendEmailActivity
import io.temporal.activity.ActivityOptions
import io.temporal.common.RetryOptions
import io.temporal.workflow.Workflow
import java.time.Duration
import java.util.*
import org.slf4j.LoggerFactory

class MessageWorkflowImpl(
) : MessageWorkflow {
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
        WITHDRAW to ActivityOptions { setHeartbeatTimeout(Duration.ofSeconds(5)) }
    )

    private val sendEmailActivity =
        Workflow.newActivityStub(SendEmailActivity::class.java, defaultActivityOptions, perActivityMethodOptions)

    override fun emit(message: String) {
        logger.info("Sending to message:$message")
        sendEmailActivity.emit(Email(data = message, identity = UUID.randomUUID()))
    }

    private val logger = LoggerFactory.getLogger(this::class.java)

    companion object {
        private const val WITHDRAW = "Withdraw"
    }
}