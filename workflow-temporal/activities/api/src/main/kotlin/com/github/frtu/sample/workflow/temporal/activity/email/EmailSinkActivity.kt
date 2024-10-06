package com.github.frtu.sample.workflow.temporal.activity.email

import io.temporal.activity.ActivityInterface
import io.temporal.activity.ActivityMethod

@ActivityInterface
interface EmailSinkActivity {
    companion object {
        const val TASK_QUEUE_EMAIL = "TASK_QUEUE_EMAIL"
    }

    @ActivityMethod
    fun emit(email: Email)
}
