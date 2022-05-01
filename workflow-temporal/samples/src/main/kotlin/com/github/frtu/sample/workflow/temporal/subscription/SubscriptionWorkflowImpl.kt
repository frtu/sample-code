package com.github.frtu.sample.workflow.temporal.subscription

import com.github.frtu.sample.workflow.temporal.subscription.activities.SubscriptionActivities
import com.github.frtu.sample.workflow.temporal.subscription.model.Customer
import io.temporal.activity.ActivityOptions
import io.temporal.workflow.Workflow
import java.time.Duration

/** Subscription Workflow implementation. Note this is just a POJO.  */
class SubscriptionWorkflowImpl : SubscriptionWorkflow {
    private var billingPeriodNum = 0
    private var subscriptionCancelled = false
    lateinit var customer: Customer

    /**
     * Define our Activity options:
     * setStartToCloseTimeout: maximum Activity Execution time after it was sent to a Worker
     */
    private val activityOptions = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(5)).build()

    // Define subscription Activities stub
    private val activities: SubscriptionActivities =
        Workflow.newActivityStub(SubscriptionActivities::class.java, activityOptions)

    override fun startSubscription(customer: Customer) {
        // Set the Workflow customer
        this.customer = customer

        // Send welcome email to customer
        activities.sendWelcomeEmail(customer)

        // Start the free trial period. User can still cancel subscription during this time
        Workflow.await(customer.subscription?.trialPeriod) { subscriptionCancelled }

        // If customer cancelled their subscription during trial period, send notification email
        if (subscriptionCancelled) {
            activities.sendCancellationEmailDuringTrialPeriod(customer)
            // We have completed subscription for this customer.
            // Finishing Workflow Execution
            return
        }

        // Trial period is over, start billing until
        // we reach the max billing periods for the subscription
        // or sub has been cancelled
        while (billingPeriodNum < customer.subscription?.maxBillingPeriods!!) {
            // Charge customer for the billing period
            activities.chargeCustomerForBillingPeriod(customer, billingPeriodNum)

            // Wait 1 billing period to charge customer or if they cancel subscription
            // whichever comes first
            Workflow.await(customer.subscription?.billingPeriod) { subscriptionCancelled }

            // If customer cancelled their subscription send notification email
            if (subscriptionCancelled) {
                activities.sendCancellationEmailDuringActiveSubscription(customer)

                // We have completed subscription for this customer.
                // Finishing Workflow Execution
                break
            }
            billingPeriodNum++
        }

        // if we get here the subscription period is over
        // notify the customer to buy a new subscription
        if (!subscriptionCancelled) {
            activities.sendSubscriptionOverEmail(customer)
        }
    }

    override fun cancelSubscription() {
        subscriptionCancelled = true
    }

    override fun updateBillingPeriodChargeAmount(billingPeriodChargeAmount: Int) {
        customer.subscription?.billingPeriodCharge = billingPeriodChargeAmount
    }

    override fun queryCustomerId(): String {
        return customer.id!!
    }

    override fun queryBillingPeriodNumber(): Int {
        return billingPeriodNum
    }

    override fun queryBillingPeriodChargeAmount(): Int {
        return customer.subscription?.billingPeriodCharge!!
    }
}