package com.github.frtu.sample.workflow.temporal.subscription.starter

import com.github.frtu.sample.workflow.temporal.subscription.SubscriptionWorkflow
import com.github.frtu.sample.workflow.temporal.subscription.SubscriptionWorkflowImpl
import com.github.frtu.sample.workflow.temporal.subscription.activities.SubscriptionActivitiesImpl
import com.github.frtu.sample.workflow.temporal.subscription.model.Customer
import com.github.frtu.sample.workflow.temporal.subscription.model.Subscription
import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowOptions
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.worker.WorkerFactory
import java.time.Duration
import java.util.*
import java.util.function.Consumer

/** Subscription Workflow starter  */
// Task Queue name
const val TASK_QUEUE = "SubscriptionsTaskQueue"

// Base Id for all subscription Workflow Ids
const val WORKFLOW_ID_BASE = "SubscriptionsWorkflow"

/**
 * Define our Subscription
 * Let's say we have a trial period of 10 seconds and a billing period of 10 seconds
 * In real life this would be much longer
 * We also set the max billing periods to 24, and the billing cycle charge to 120
 */
var subscription: Subscription = Subscription(Duration.ofSeconds(10), Duration.ofSeconds(10), 24, 120)

fun main() {
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

    /**
     * Define the Workflow factory. It is used to create Workflow Workers for a specific Task Queue.
     */
    val factory = WorkerFactory.newInstance(client)

    /**
     * Define the Workflow Worker. Workflow Workers listen to a defined Task Queue and process
     * Workflows and Activities.
     */
    val worker = factory.newWorker(TASK_QUEUE)

    /**
     * Register our Workflow implementation with the Worker. Since Workflows are stateful in nature,
     * we need to register our Workflow type.
     */
    worker.registerWorkflowImplementationTypes(SubscriptionWorkflowImpl::class.java)

    /**
     * Register our Activity implementation with the Worker. Since Activities are
     * stateless and thread-safe, we need to register a shared instance.
     */
    worker.registerActivitiesImplementations(SubscriptionActivitiesImpl())

    // Start all the Workers registered for a specific Task Queue.
    factory.start()

    // List of our example customers
    val customers: MutableList<Customer> = ArrayList<Customer>()

    // Create example customers
    for (i in 0..4) {
        val customer = Customer(
            "First Name$i",
            "Last Name$i", "Id-$i", "Email$i", subscription
        )
        customers.add(customer)
    }

    /**
     * Create and start a new subscription Workflow
     * for each of the example customers
     */
    customers.forEach(
        Consumer<Customer> { customer: Customer ->
            // Create our Workflow client stub. It is used to start our Workflow Execution.
            // For sake of the example we set the total Workflow run timeout to 5 minutes
            val workflow: SubscriptionWorkflow = client.newWorkflowStub(
                SubscriptionWorkflow::class.java,
                WorkflowOptions.newBuilder()
                    .setTaskQueue(TASK_QUEUE)
                    .setWorkflowId(WORKFLOW_ID_BASE + customer.id)
                    .setWorkflowRunTimeout(Duration.ofMinutes(5))
                    .build()
            )

            // Start Workflow Execution (async)
            WorkflowClient.start(workflow::startSubscription, customer)
        })
}