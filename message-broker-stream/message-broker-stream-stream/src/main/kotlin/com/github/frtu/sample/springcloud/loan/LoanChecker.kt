package com.github.frtu.sample.springcloud.loan

import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class LoanChecker(var processor: LoanProcessor) {
    val log = LoggerFactory.getLogger(LoanChecker::class.java)

    private val MAX_AMOUNT = 10000L

    @StreamListener(LoanProcessor.APPLICATIONS_IN)
    fun checkAndSortLoans(loan: Loan) {
        log.info("{} {} for \${} for {}", loan.status, loan.uuid, loan.amount, loan.name)
        if (loan.amount > MAX_AMOUNT) {
            loan.status = Statuses.DECLINED.name
            processor.declined().send(message<Any>(loan))
        } else {
            loan.status = Statuses.APPROVED.name
            processor.approved().send(message<Any>(loan))
        }
        log.info("{} {} for \${} for {}", loan.status, loan.uuid, loan.amount, loan.name)
    }

    private fun <T> message(value: T): Message<T> {
        return MessageBuilder.withPayload(value).build()
    }
}