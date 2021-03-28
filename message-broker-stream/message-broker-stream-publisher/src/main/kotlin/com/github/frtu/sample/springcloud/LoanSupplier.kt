package com.github.frtu.sample.springcloud

import com.github.frtu.sample.model.Loan
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Supplier

@Configuration
open class LoanSupplier {
    private val log: Logger = LoggerFactory.getLogger(LoanSupplier::class.java)
    private val names: List<String> =
        listOf("Donald", "Theresa", "Vladimir", "Angela", "Emmanuel", "Shinzō", "Jacinda", "Kim")
    private val amounts: List<Long> =
        listOf(10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 100000000L)

    @Bean
    open fun supplyLoan(): Supplier<Loan> {
        return Supplier<Loan> {
            val loan = Loan(
                UUID.randomUUID().toString(),
                names[Random().nextInt(names.size)],
                amounts[Random().nextInt(amounts.size)]
            )
            log.info("{} {} for \${} for {}", loan.status, loan.uuid, loan.amount, loan.name)
            loan
        }
    }
}