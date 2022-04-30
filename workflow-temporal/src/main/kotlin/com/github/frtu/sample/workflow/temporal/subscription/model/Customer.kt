package com.github.frtu.sample.workflow.temporal.subscription.model

data class Customer (
    var firstName: String? = null,
    var lastName: String? = null,
    var id: String? = null,
    var email: String? = null,
    var subscription: Subscription? = null,
)