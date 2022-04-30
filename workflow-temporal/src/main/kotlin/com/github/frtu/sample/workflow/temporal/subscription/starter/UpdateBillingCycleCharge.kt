package com.github.frtu.sample.workflow.temporal.subscription.starter

import com.github.frtu.sample.workflow.temporal.subscription.SubscriptionWorkflow
import io.temporal.client.WorkflowClient
import io.temporal.serviceclient.WorkflowServiceStubs

object UpdateBillingCycleCharge {
    @JvmStatic
    fun main(args: Array<String>) {
        /**
         * Define the workflow service. It is a gRPC stubs wrapper which talks to the docker instance of
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
        val customerId = if (args.isNotEmpty()) args[0] else "Id-1"
        // Passed updated charge amount
        val newCharge = if (args.size > 1) args[1] else "30"

        // Create a stub that points to an existing subscription Workflow with the given ID
        val workflow: SubscriptionWorkflow = client.newWorkflowStub(
            SubscriptionWorkflow::class.java, SubscriptionWorkflowStarter.WORKFLOW_ID_BASE + customerId
        )

        // Update the billing cycle charge
        println("Updating billing period charge for customer: $customerId using newCharge=$newCharge")
        workflow.updateBillingPeriodChargeAmount(newCharge.toInt())
    }
}