package com.github.frtu.sample.springcloud.loan

enum class Statuses {
    APPROVED, DECLINED, PENDING, REJECTED
}

data class Loan(val uuid: String, val name: String, val amount: Long) {
    var status: String = Statuses.PENDING.name
}