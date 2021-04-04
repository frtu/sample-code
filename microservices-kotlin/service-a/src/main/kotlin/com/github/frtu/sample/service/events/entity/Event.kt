package com.github.frtu.sample.service.events.entity

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.github.frtu.sample.service.core.entity.BaseEntity
import javax.persistence.*

@Entity
//@Table(name = "event")
@JsonPropertyOrder("id", "name", "value", "eventTime", "createdAt", "updatedAt")
class Event(
    val name: String = "",
    val value: Float = 0F,
    val comments: String = "",
//    @Id @GeneratedValue(strategy = GenerationType.AUTO)
//    var id: Long = 0L,
    val eventTime: Long = System.currentTimeMillis() / 1000L
) : BaseEntity()