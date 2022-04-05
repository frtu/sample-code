package com.github.frtu.sample.workflow.events.service

data class TaskVO(
    val id: String,
    val name: String,
    val processInstanceId: String,
)