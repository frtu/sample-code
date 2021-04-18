package com.github.frtu.persistence.r2dbc.query

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import java.util.*

class PostgresJsonbQueryBuilder(
    private val skipKeys: Set<String> = setOf(),
    private val idColumnName: String = "id",
    private val jsonbColumnName: String = "data"
) {
    private val LOGGER: Logger = LoggerFactory.getLogger(PostgresJsonbQueryBuilder::class.java)

    fun id(id: Any): Query = Query.query(Criteria.where(idColumnName).`is`(id))
        .apply { LOGGER.debug("""{"query_type":"id", "${idColumnName}":"${id}"""") }

    fun query(criteriaMap: Map<String, String>, pageable: Pageable? = null): Query {
        val criteria = criteria(criteriaMap)
        return query(criteria, pageable)
    }

    fun query(criteria: Criteria?, pageable: Pageable? = null): Query {
        var query = criteria?.let { Query.query(criteria) } ?: Query.empty()
        if (pageable != null && pageable.isPaged) {
            query = query.offset(pageable.offset)
            query = query.limit(pageable.pageSize)
            LOGGER.debug("""{"limit":${pageable.pageSize}, "offset":${pageable.offset}}""")
        }
        return query
    }

    fun criteria(criteriaMap: Map<String, String>): Criteria? {
        val criteriaIterator = criteriaMap
            // skip empty key or value
            .filter { it.key != null && it.value != null }
            .filter { !skipKeys.contains(it.key) }
            .map { Criteria.where("${jsonbColumnName}->>'${it.key}'").`is`(it.value) }
            .asSequence().iterator()

        // Build fluent CriteriaDefinition
        var criteria: Criteria? = null
        if (criteriaIterator.hasNext()) {
            criteria = criteriaIterator.next()
            while (criteriaIterator.hasNext()) {
                criteria = criteria?.and(criteriaIterator.next())
            }
            LOGGER.debug("""{"query_type":"criteria", criteria":"${criteria}"""")
        }
        return criteria
    }
}