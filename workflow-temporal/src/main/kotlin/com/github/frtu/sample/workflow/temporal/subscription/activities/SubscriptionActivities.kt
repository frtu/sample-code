package com.github.frtu.sample.workflow.temporal.subscription.activities

import com.github.frtu.sample.workflow.temporal.subscription.model.Customer
import io.temporal.activity.ActivityInterface

/** Subscription Activity interface  */
@ActivityInterface
interface SubscriptionActivities {
    fun sendWelcomeEmail(customer: Customer)
    fun sendCancellationEmailDuringTrialPeriod(customer: Customer)
    fun chargeCustomerForBillingPeriod(customer: Customer, billingPeriodNum: Int)
    fun sendCancellationEmailDuringActiveSubscription(customer: Customer)
    fun sendSubscriptionOverEmail(customer: Customer)
}