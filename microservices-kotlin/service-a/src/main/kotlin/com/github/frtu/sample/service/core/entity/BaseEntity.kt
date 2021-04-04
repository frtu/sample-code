package com.github.frtu.sample.service.core.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.Type
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(
    value = ["createdAt", "updatedAt"],
    allowGetters = true
)
abstract class BaseEntity(
    @Id @Type(type = "pg-uuid")
    var id: UUID = UUID.randomUUID(),

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: Date? = null,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    var updatedAt: Date? = null
)