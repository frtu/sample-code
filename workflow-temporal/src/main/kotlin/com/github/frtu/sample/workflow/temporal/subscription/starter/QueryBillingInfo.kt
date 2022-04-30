package com.github.frtu.sample.workflow.temporal.subscription.starter

import com.github.frtu.sample.workflow.temporal.subscription.SubscriptionWorkflow
import io.temporal.client.WorkflowClient
import io.temporal.serviceclient.WorkflowServiceStubs

// Allows you to query billing information for an existing customer
object QueryBillingInfo {
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
        val customerId = if (args.isNotEmpty()) args[0] else "Id-1"

        // Create a stub that points to an existing subscription Workflow with the given ID
        val workflow: SubscriptionWorkflow = client.newWorkflowStub(
            SubscriptionWorkflow::class.java, SubscriptionWorkflowStarter.WORKFLOW_ID_BASE + customerId
        )

        // Print the customer billing info (from Workflow Query methods)
        printCustomerBillingInfo(workflow)
    }

    private fun printCustomerBillingInfo(workflow: SubscriptionWorkflow) {
        println("*****************")
        System.out.println("Customer Id: " + workflow.queryCustomerId())
        System.out.println("Billing Period #: " + workflow.queryBillingPeriodNumber())
        System.out.println("Charge Amount: " + workflow.queryBillingPeriodChargeAmount())
    }
}