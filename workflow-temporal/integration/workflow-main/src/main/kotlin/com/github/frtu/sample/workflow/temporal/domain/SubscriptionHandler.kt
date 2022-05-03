package com.github.frtu.sample.workflow.temporal.domain

import java.util.*

interface SubscriptionHandler {
    fun handle(subscriptionEvent: SubscriptionEvent): UUID
}