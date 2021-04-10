package com.github.frtu.mail.model

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmailRepository : ReactiveCrudRepository<Email, UUID> {
}
