package com.github.frtu.mail.model

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EmailRepository : JpaRepository<Email, UUID> {
}
