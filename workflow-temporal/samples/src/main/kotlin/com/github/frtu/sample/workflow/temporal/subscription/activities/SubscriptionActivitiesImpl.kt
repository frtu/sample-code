package com.github.frtu.sample.workflow.temporal.subscription.activities

import com.github.frtu.sample.workflow.temporal.subscription.model.Customer

class SubscriptionActivitiesImpl() : SubscriptionActivities {
    override fun sendWelcomeEmail(customer: Customer) {
        println("** SubscriptionActivities ** sending welcome email to : $customer")
    }

    override fun sendCancellationEmailDuringTrialPeriod(customer: Customer) {
        println("** SubscriptionActivities ** sending cancellation email during trial period to : $customer")
    }

    override fun chargeCustomerForBillingPeriod(customer: Customer, billingPeriodNum: Int) {
        println(
            "** SubscriptionActivities ** performing billing for customer: $customer" +
                    " and billing period: $billingPeriodNum and amount: ${customer.subscription?.billingPeriodCharge}"
        )
    }

    override fun sendCancellationEmailDuringActiveSubscription(customer: Customer) {
        println(
            "** SubscriptionActivities ** sending cancellation email during active subscription period to : $customer"
        )
    }

    override fun sendSubscriptionOverEmail(customer: Customer) {
        println("** SubscriptionActivities ** sending subscription is over email to : $customer")
    }
}