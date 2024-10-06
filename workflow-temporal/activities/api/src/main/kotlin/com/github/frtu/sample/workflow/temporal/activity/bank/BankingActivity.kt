package com.github.frtu.sample.workflow.temporal.activity.bank

import io.temporal.activity.ActivityInterface
import io.temporal.activity.ActivityMethod

@ActivityInterface
interface BankingActivity {
    companion object {
        const val TASK_QUEUE = "TASK_QUEUE_TRANSFER_MONEY"
    }

    @ActivityMethod
    fun deposit(paymentDetails: PaymentDetails): String
}
