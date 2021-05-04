package com.github.frtu.persistence.r2dbc.entitytemplate

import com.github.frtu.persistence.r2dbc.Email
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import reactor.core.publisher.Flux

internal class EmailRepositoryTest {
    private val LOGGER: Logger = LoggerFactory.getLogger(EmailRepositoryTest::class.java)

    @Test
    fun `Test basic findAll`() {
        val email1 = Email("rndfred@gmail.com", "Mail subject", "Lorem ipsum dolor sit amet.", "INIT")
        val email2 = Email("rndfred@hotmail.com", "Mail subject", "Lorem ipsum dolor sit amet.", "SENT")

        val template = mockk<R2dbcEntityTemplate>()
        every {
            template.select(Email::class.java).all()
        } returns Flux.just(email1, email2)

        val repository = EmailRepository(template)
        LOGGER.debug("R2dbcEntityTemplate: ${template}")
        runBlocking {
            // Execute flow to list
            val result = repository.findAll().toList(mutableListOf())
            LOGGER.debug("Test result: ${result}")
            assertThat(result).isNotNull()
            assertThat(result[0]).isEqualTo(email1)
            assertThat(result[1]).isEqualTo(email2)
        }
    }
}