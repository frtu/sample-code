package com.github.frtu.sample.workflow.temporal.email.activity

import io.temporal.activity.ActivityInterface
import io.temporal.activity.ActivityMethod

@ActivityInterface
interface EmailSinkActivity {
    @ActivityMethod
    fun emit(email: Email)
}

const val TASK_QUEUE_EMAIL = "TASK_QUEUE_EMAIL"
