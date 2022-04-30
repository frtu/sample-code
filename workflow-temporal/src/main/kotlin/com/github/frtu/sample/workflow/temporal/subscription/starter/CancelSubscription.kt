package com.github.frtu.sample.workflow.temporal.subscription.starter

import com.github.frtu.sample.workflow.temporal.subscription.SubscriptionWorkflow
import io.temporal.client.WorkflowClient
import io.temporal.serviceclient.WorkflowServiceStubs

// Can be used to cancel a subscription for a specific customer
object CancelSubscription {
    @JvmStatic
    fun main(args: Array<String>) {

        /**
         * Define the Workflow service. It is a gRPC stubs wrapper which talks to the docker instance of
         * our locally running Temporal service.
         * Defined here as reused by other starters
         */
        val service = WorkflowServiceStubs.newInstance()

        /**
         * Define the Workflow client. It is a Temporal service client used to start, Signal, and Query
         * Workflows
         */
        val client = WorkflowClient.newInstance(service)

        // Passed in customer id
        val customerId = if (args.isNotEmpty()) args[0] else "Id-0"

        // Create a stub that points to an existing subscription Workflow with the given ID
        val workflow: SubscriptionWorkflow = client.newWorkflowStub(
            SubscriptionWorkflow::class.java, SubscriptionWorkflowStarter.WORKFLOW_ID_BASE.toString() + customerId
        )
        println("Cancelling subscription for customer: $customerId")
        workflow.cancelSubscription()
    }
}