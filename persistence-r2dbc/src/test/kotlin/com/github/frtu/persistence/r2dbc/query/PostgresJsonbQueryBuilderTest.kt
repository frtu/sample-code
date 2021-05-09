package com.github.frtu.persistence.r2dbc.query

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.relational.core.query.Criteria

internal class PostgresJsonbQueryBuilderTest {
    private val LOGGER = LoggerFactory.getLogger(PostgresJsonbQueryBuilderTest::class.java)

    @Test
    fun `Test pageable query`() {
        val postgresJsonbQueryBuilder = PostgresJsonbQueryBuilder()
        val query = postgresJsonbQueryBuilder.query(Criteria.empty(), PageRequest.of(2, 2))
        LOGGER.debug("offset=[${query.offset}] limit=[${query.limit}]")
        // Page 2, offset is equals to 4
        assertThat(query.offset).isEqualTo(4)
        assertThat(query.limit).isEqualTo(2)
    }

    @Test
    fun `Testing criteria mapping empty parameter`() {
        val postgresJsonbQueryBuilder = PostgresJsonbQueryBuilder()
        val criteria = postgresJsonbQueryBuilder.criteria(mapOf())
        assertThat(criteria).isEqualTo(Criteria.empty())
    }

    @Test
    fun `Testing criteria mapping correctly parameters`() {
        val postgresJsonbQueryBuilder = PostgresJsonbQueryBuilder()
        val criteria = postgresJsonbQueryBuilder.criteria(
            mapOf(
                "key1" to "value1",
                "key2" to "value2"
            )
        )
        val criteriaString = criteria.toString()
        LOGGER.debug("criteria=[$criteriaString]")
        assertThat(criteriaString).contains("data->>'key1' = 'value1'")
        assertThat(criteriaString).contains("data->>'key2' = 'value2'")
    }
}