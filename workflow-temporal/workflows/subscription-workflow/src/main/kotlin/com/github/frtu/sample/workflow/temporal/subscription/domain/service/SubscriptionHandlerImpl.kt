package com.github.frtu.sample.workflow.temporal.subscription.domain.service

import com.github.frtu.logs.core.StructuredLogger
import com.github.frtu.logs.core.StructuredLogger.flowId
import com.github.frtu.logs.core.StructuredLogger.key
import com.github.frtu.sample.workflow.temporal.subscription.domain.SubscriptionEvent
import com.github.frtu.sample.workflow.temporal.subscription.domain.workflow.SubscriptionWorkflow
import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowOptions
import java.time.Duration
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class SubscriptionHandlerImpl(
    private val client: WorkflowClient,
) : SubscriptionHandler {
    override fun handle(subscriptionEvent: SubscriptionEvent): UUID {
        val id = UUID.randomUUID()

        val workflow: SubscriptionWorkflow = client.newWorkflowStub(
            SubscriptionWorkflow::class.java,
            WorkflowOptions.newBuilder()
                .setTaskQueue(SubscriptionWorkflow.TASK_QUEUE)
                .setWorkflowId("SubscriptionWorkflow-$id")
                .setWorkflowRunTimeout(Duration.ofMinutes(5))
                .build()
        )
        val we = WorkflowClient.start(workflow::start, subscriptionEvent)

        structuredLogger.info(flowId(id), key("workflow_id", we.workflowId), key("run_id", we.runId))
        return id
    }

    private val structuredLogger = StructuredLogger.create(this::class.java)
}