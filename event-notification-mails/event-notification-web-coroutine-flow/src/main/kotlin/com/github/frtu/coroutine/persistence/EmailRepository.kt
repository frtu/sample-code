package com.github.frtu.coroutine.persistence

import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmailRepository : CoroutineCrudRepository<Email, UUID> {
    @Query("""
        SELECT * FROM email
        WHERE creation_time > (SELECT creation_time FROM email WHERE ID = :id)
        ORDER BY "creation_time" ASC
        LIMIT 2
    """)
    fun afterThisId(@Param("id") id: UUID): Flow<Email>
}
