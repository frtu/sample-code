package com.github.frtu.sample.workflow.temporal.activity

import io.temporal.activity.ActivityInterface
import io.temporal.activity.ActivityMethod

@ActivityInterface
interface SendEmailActivity {
    @ActivityMethod
    fun emit(email: Email)
}

const val TASK_QUEUE_SEND_EMAIL = "TASK_QUEUE_SEND_EMAIL"
