package com.github.frtu.sample.service.events.entity

import java.util.*
import javax.persistence.*

@Entity
class Event(
    val name: String = "",
    val value: Float = 0F,
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0L,
    val eventTime: Long = System.currentTimeMillis() / 1000L
)