package com.github.frtu.sample.workflow.temporal.subscription.domain.service

import com.github.frtu.logs.core.StructuredLogger
import com.github.frtu.logs.core.StructuredLogger.flowId
import com.github.frtu.logs.core.StructuredLogger.key
import com.github.frtu.sample.workflow.temporal.subscription.domain.SubscriptionEvent
import com.github.frtu.sample.workflow.temporal.subscription.domain.workflow.SubscriptionWorkflow
import io.temporal.client.WorkflowClient
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubscriptionHandlerImpl(private val subscriptionWorkflow: SubscriptionWorkflow) : SubscriptionHandler {
    override fun handle(subscriptionEvent: SubscriptionEvent): UUID {
        val id = subscriptionEvent.id
        val we = WorkflowClient.start(subscriptionWorkflow::start, subscriptionEvent)
        structuredLogger.info(flowId(id), key("workflow_id", we.workflowId), key("run_id", we.runId))
        return id
    }

    private val structuredLogger = StructuredLogger.create(this::class.java)
}