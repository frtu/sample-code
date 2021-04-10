package com.github.frtu.coroutine.persistence

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmailRepository : CoroutineCrudRepository<Email, UUID> {
}
