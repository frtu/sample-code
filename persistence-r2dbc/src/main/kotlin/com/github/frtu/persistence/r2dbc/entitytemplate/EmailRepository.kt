package com.github.frtu.persistence.r2dbc.entitytemplate

import com.github.frtu.persistence.r2dbc.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository

@Repository
class EmailRepository(val template: R2dbcEntityTemplate) {
    suspend fun findAll(): Flow<Email> = template.select(Email::class.java)
        .all().asFlow()
}