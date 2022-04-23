package com.github.frtu.sample.workflow.temporal.moneytransferapp

import io.temporal.client.WorkflowClient
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.worker.WorkerFactory

object MoneyTransferWorker {
    @JvmStatic
    fun main(args: Array<String>) {

        // WorkflowServiceStubs is a gRPC stubs wrapper that talks to the local Docker instance of the Temporal server.
        val service = WorkflowServiceStubs.newInstance()
        val client = WorkflowClient.newInstance(service)
        // Worker factory is used to create Workers that poll specific Task Queues.
        val factory = WorkerFactory.newInstance(client)
        val worker = factory.newWorker(Shared.MONEY_TRANSFER_TASK_QUEUE)
        // This Worker hosts both Workflow and Activity implementations.
        // Workflows are stateful so a type is needed to create instances.
        worker.registerWorkflowImplementationTypes(MoneyTransferWorkflowImpl::class.java)
        // Activities are stateless and thread safe so a shared instance is used.
        worker.registerActivitiesImplementations(AccountActivityImpl())
        // Start listening to the Task Queue.
        factory.start()
    }
}