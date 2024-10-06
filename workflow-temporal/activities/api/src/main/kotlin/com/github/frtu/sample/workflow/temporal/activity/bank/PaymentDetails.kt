package com.github.frtu.sample.workflow.temporal.activity.bank

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaymentDetails(
    val sourceAccount: String,
    val targetAccount: String,
    val amount: Int,
    val referenceId: String,
)
//[
//{
//    "amount": 250,
//    "reference_id": "12345",
//    "source_account": "85-150",
//    "target_account": "43-812"
//}
//]