package com.github.frtu.persistence.r2dbc.entitytemplate

import com.github.frtu.persistence.exception.DataNotExist
import com.github.frtu.persistence.r2dbc.Email
import com.github.frtu.persistence.r2dbc.query.IPostgresJsonbQueryBuilder
import com.github.frtu.persistence.r2dbc.query.PostgresJsonbQueryBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository

@Repository
class EmailRepository(private val template: R2dbcEntityTemplate) {
    private val queryBuilder: IPostgresJsonbQueryBuilder = PostgresJsonbQueryBuilder(setOf("page", "size"))

    suspend fun findAll(): Flow<Email> = template.select(Email::class.java)
        .all().asFlow()

    suspend fun findAll(searchParams: Map<String, String>, pageable: Pageable? = null): Flow<Email> {
        LOGGER.debug("""{"query_type":"criteria", "criteria":"${searchParams}", "limit":${pageable?.pageSize}, "offset":${pageable?.offset}}""")
        return template
            .select(Email::class.java)
            .matching(queryBuilder.query(searchParams, pageable))
            .all().asFlow()
    }

    suspend fun findById(id: Long): Email {
        LOGGER.debug("""{"query_type":"id", "id":"${id}"}""")
        return template
            .selectOne(
                queryBuilder.id(id), Email::class.java
            ).awaitFirstOrNull() ?: throw DataNotExist(id.toString())
    }

    private val LOGGER: Logger = LoggerFactory.getLogger(EmailRepository::class.java)
}