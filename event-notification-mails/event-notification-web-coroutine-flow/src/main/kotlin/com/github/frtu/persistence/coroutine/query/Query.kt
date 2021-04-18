package com.github.frtu.persistence.coroutine.query

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.relational.core.query.CriteriaDefinition
import org.springframework.data.relational.core.sql.SqlIdentifier
import org.springframework.util.Assert
import java.lang.IllegalArgumentException
import java.util.*
import java.util.stream.Collectors

/**
 * EXPERIMENTAL : Copy from {@link org.springframework.data.relational.core.query.Query} to override SQL generator
 * based on {@link CriteriaDefinition}
 */
class Query(
    val criteria: CriteriaDefinition?,
    /**
     * Return the columns that this query should project.
     *
     * @return
     */
    private val columns: List<SqlIdentifier> = emptyList(),

    private val sort: Sort = Sort.unsorted(),
    /**
     * Maximum number of rows to be return.
     */
    private val limit: Int = -1,
    /**
     * Number of rows to skip.
     */
    private val offset: Long = -1
) {
    companion object {
        fun query(criteria: CriteriaDefinition): Query {
            return Query(criteria)
        }

        fun empty(): Query {
            return Query(null)
        }
    }

    fun build(): String {
        // Print internal criteria object & data
        println(criteria.toString())

        // TODO : Dynamically use criteria to generate the SQL
        val condition = " WHERE id = :id"
        return condition
    }

    fun bind(executionSpec: DatabaseClient.GenericExecuteSpec): DatabaseClient.GenericExecuteSpec {
        // TODO : Dynamically use criteria to bind parameters
        val result = executionSpec.bind("id", UUID.fromString("ba3a7152-9277-4146-9415-8eddd43d6d63"))
        return result
    }

    /**
     * Add columns to the query.
     *
     * @param columns
     * @return a new [Query] object containing the former settings with `columns` applied.
     */
    fun columns(vararg columns: String): Query = withColumns(Arrays.stream(columns)
        .map { name: String? ->
            SqlIdentifier.unquoted(
                name!!
            )
        }
        .collect(Collectors.toList()))

    /**
     * Add columns to the query.
     *
     * @param columns
     * @return a new [Query] object containing the former settings with `columns` applied.
     */
    private fun withColumns(columns: Collection<SqlIdentifier>): Query {
        val newColumns: MutableList<SqlIdentifier> = ArrayList(this.columns)
        newColumns.addAll(columns)
        return Query(criteria, newColumns, sort, limit, offset)
    }

    /**
     * Set number of rows to skip before returning results.
     *
     * @param offset
     * @return a new [Query] object containing the former settings with `offset` applied.
     */
    fun offset(offset: Long): Query = Query(criteria, columns, sort, limit, offset)

    /**
     * Limit the number of returned documents to `limit`.
     *
     * @param limit
     * @return a new [Query] object containing the former settings with `limit` applied.
     */
    fun limit(limit: Int): Query = Query(criteria, columns, sort, limit, offset)

    /**
     * Set the given pagination information on the [Query] instance. Will transparently set `offset` and
     * `limit` as well as applying the [Sort] instance defined with the [Pageable].
     *
     * @param pageable
     * @return a new [Query] object containing the former settings with [Pageable] applied.
     */
    fun with(pageable: Pageable): Query {
        if (pageable.isUnpaged) {
            return this
        }
        return Query(
            criteria, columns, sort.and(pageable.sort), pageable.pageSize,
            pageable.offset
        )
    }

    /**
     * Add a [Sort] to the [Query] instance.
     *
     * @param sort
     * @return a new [Query] object containing the former settings with [Sort] applied.
     */
    fun sort(sort: Sort): Query {
        Assert.notNull(sort, "Sort must not be null!")
        if (sort.isUnsorted) {
            return this
        }
        assertNoCaseSort(sort)
        return Query(criteria, columns, this.sort.and(sort), limit, offset)
    }

    /**
     * Return true if the [Query] has a sort parameter.
     *
     * @return true if sorted.
     * @see Sort.isSorted
     */
    val isSorted: Boolean
        get() = sort.isSorted

    private fun assertNoCaseSort(sort: Sort) {
        val var1: Iterator<*> = sort.iterator()
        var order: Sort.Order
        do {
            if (!var1.hasNext()) {
                return
            }
            order = var1.next() as Sort.Order
        } while (!order.isIgnoreCase)
        throw IllegalArgumentException(
            String.format(
                "Given sort contained an Order for %s with ignore case;Ignore case sorting is not supported",
                order.property
            )
        )
    }
}
