package com.github.frtu.sample.workflow.temporal.subscription.model

import java.time.Duration

// The subscription model
data class Subscription(
    // defines the duration of subscription trial period
    val trialPeriod: Duration? = null,

    // defines the duration of subscription billing period
    val billingPeriod: Duration? = null,

    // defines the max billing periods for this subscription
    var maxBillingPeriods: Int = 0,

    // defines the per-billing period charge
    var billingPeriodCharge: Int = 0,
)