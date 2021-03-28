package com.github.frtu.sample.springcloud.loan

import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel
import org.springframework.stereotype.Component


@Component
interface LoanProcessor {
    @Input(APPLICATIONS_IN)
    fun sourceOfLoanApplications(): SubscribableChannel

    @Output(APPROVED_OUT)
    fun approved(): MessageChannel

    @Output(DECLINED_OUT)
    fun declined(): MessageChannel

    companion object {
        const val APPLICATIONS_IN = "output"
        const val APPROVED_OUT = "approved"
        const val DECLINED_OUT = "declined"
    }
}
