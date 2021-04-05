package com.github.frtu.mail.model

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface EmailRepository : JpaRepository<Email, UUID> {
    @Query(
        value = """SELECT id, receiver, subject, content, status, creation_time, update_time, ts_rank_cd(tsv, query, 1) AS rank
            FROM notification.emails, to_tsquery(?1) query
            WHERE query @@ tsv
            ORDER BY rank DESC""",
        countQuery = """SELECT count(*)
            FROM notification.emails, to_tsquery(?1) query
            WHERE query @@ tsv""",
        nativeQuery = true
    )
    fun search(text: String, pageable: Pageable): Page<Email>
}
