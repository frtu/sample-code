package com.github.frtu.sample.workflow.temporal.subscription.domain.service

import com.github.frtu.sample.workflow.temporal.subscription.domain.SubscriptionEvent
import java.util.*

interface SubscriptionHandler {
    fun handle(subscriptionEvent: SubscriptionEvent): UUID
}