package com.github.frtu.mail.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "emails")
//@Table(schema = "notification", name = "emails")
//@EntityListeners(AuditingEntityListener::class)
@JsonPropertyOrder("id", "receiver", "subject", "content", "createdAt", "updatedAt")
@JsonIgnoreProperties(
    value = ["createdAt", "updatedAt"],
    allowGetters = true
)
data class Email(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    var id: UUID? = null,

    @Column(name = "receiver", nullable = false, updatable = false)
    var receiver: String? = null,

    @Column(name = "subject", nullable = false, updatable = false)
    var subject: String? = null,

    @Column(name = "content", nullable = false)
    var content: String? = null,

    @Column(name = "status", nullable = false)
    var status: String? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", nullable = false)
    @CreatedDate
    val creationTime: LocalDateTime = LocalDateTime.now(),

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time", nullable = false)
    @LastModifiedDate
    var updateTime: LocalDateTime = creationTime
)
