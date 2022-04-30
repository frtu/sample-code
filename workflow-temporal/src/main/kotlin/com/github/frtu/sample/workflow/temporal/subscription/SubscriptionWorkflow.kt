package com.github.frtu.sample.workflow.temporal.subscription

import com.github.frtu.sample.workflow.temporal.subscription.model.Customer
import io.temporal.workflow.QueryMethod
import io.temporal.workflow.SignalMethod
import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod


/** Subscription Workflow interface  */
@WorkflowInterface
interface SubscriptionWorkflow {
    @WorkflowMethod
    fun startSubscription(customer: Customer)

    @SignalMethod
    fun cancelSubscription()

    @SignalMethod
    fun updateBillingPeriodChargeAmount(billingPeriodChargeAmount: Int)

    @QueryMethod
    fun queryCustomerId(): String?

    @QueryMethod
    fun queryBillingPeriodNumber(): Int

    @QueryMethod
    fun queryBillingPeriodChargeAmount(): Int
}