package com.github.frtu.coroutine.persistence

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.frtu.coroutine.persistence.Email.Companion.TABLE_NAME
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table(TABLE_NAME)
@JsonPropertyOrder("id", "receiver", "subject", "content", "createdAt", "updatedAt")
@JsonIgnoreProperties(
    value = ["createdAt", "updatedAt"],
    allowGetters = true
)
data class Email(
    @Column("data")
    var data: String = "{}",

    @Id
    @Column("id")
    var identity: UUID? = null,

    @CreatedDate
    @Column("creation_time")
    val creationTime: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @Column("update_time")
    var updateTime: LocalDateTime = creationTime
)
//    : Persistable<UUID>
{
    constructor(
        value: Any,
        identity: UUID? = null,
        creationTime: LocalDateTime = LocalDateTime.now(),
        updateTime: LocalDateTime = creationTime
    ) : this(objectMapper.writeValueAsString(value), identity, creationTime, updateTime)

    companion object {
        const val TABLE_NAME = "email"
        val objectMapper = ObjectMapper()
    }

//    var isNewlyCreated = false;
//
//    init {
//        if (identity == null) {
//            isNewlyCreated = true;
//            identity = UUID.randomUUID()
//        }
//    }
//
//    override fun isNew(): Boolean = this.isNewlyCreated
//
//    override fun getId(): UUID? = this.identity
}
