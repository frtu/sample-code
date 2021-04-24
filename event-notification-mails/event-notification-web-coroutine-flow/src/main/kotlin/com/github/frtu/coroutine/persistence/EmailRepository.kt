package com.github.frtu.coroutine.persistence

import com.github.frtu.coroutine.exception.DataNotExist
import com.github.frtu.persistence.r2dbc.query.PostgresJsonbQueryBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Update
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class EmailRepository(val template: R2dbcEntityTemplate) {
    private val LOGGER: Logger = LoggerFactory.getLogger(EmailRepository::class.java)
    private val queryBuilder = PostgresJsonbQueryBuilder(setOf("page", "size"))

    suspend fun findAll(searchParams: Map<String, String>, pageable: Pageable?): Flow<Email> {
        LOGGER.debug("""{"query_type":"criteria", "criteria":"${searchParams}", "limit":${pageable?.pageSize}, "offset":${pageable?.offset}}""")
        return template
            .select(Email::class.java)
            .matching(queryBuilder.query(searchParams, pageable))
            .all().asFlow()
    }

    suspend fun findById(id: UUID): Email? {
        LOGGER.debug("""{"query_type":"id", "id":"${id}"}""")
        return template
            .selectOne(
                queryBuilder.id(id), Email::class.java
            ).awaitFirstOrNull() ?: throw DataNotExist(id.toString())
    }

    suspend fun update(id: UUID, emailDetail: EmailDetail): UUID? = template
        .update(
            queryBuilder.id(id),
            Update.update("data->>'status'", emailDetail.status),
            Email::class.java
        )
        .map { id }
        .awaitFirstOrNull() ?: throw DataNotExist(id.toString())

    suspend fun save(emailDetail: EmailDetail): UUID? = this.save(Email(emailDetail))

    suspend fun save(email: Email): UUID? = template
        .insert(Email::class.java)
        .using(email)
        .map { email.identity }
        .awaitFirstOrNull()

    suspend fun deleteById(id: UUID): Int? = template
        .delete(
            queryBuilder.id(id), Email::class.java
        ).awaitFirstOrNull() ?: throw DataNotExist(id.toString())
}