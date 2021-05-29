package com.github.frtu.coroutine.web

import com.github.frtu.coroutine.persistence.EmailDetail
import com.github.frtu.coroutine.persistence.EmailRepository
import com.github.frtu.coroutine.persistence.IEmailRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.*
import java.net.URI
import java.util.*

@Configuration
class RouterConfig {
    @Bean
    fun route(repository: IEmailRepository, emailRepository: EmailRepository): RouterFunction<*> = coRouter {
        // https://docs.spring.io/spring-framework/docs/current/kdoc-api/spring-framework/org.springframework.web.reactive.function.server/-co-router-function-dsl/index.html
        accept(APPLICATION_JSON).nest {
            GET("/v2/emails") { serverRequest ->
                ok().json().bodyAndAwait(emailRepository.findAll())
            }
            GET("/v2/emails/{id}") { serverRequest ->
                val id = serverRequest.pathVariable("id")
                val entity = emailRepository.findById(UUID.fromString(id))
                when {
                    entity != null -> ok().bodyValueAndAwait(entity)
                    else -> notFound().buildAndAwait()
                }
            }
        }
        contentType(APPLICATION_JSON).nest {
            POST("/v2/emails") { serverRequest ->
                val uuid = emailRepository.save(serverRequest.awaitBody<EmailDetail>())
                created(URI.create("/posts/$uuid")).buildAndAwait()
            }
            PUT("/v2/emails/{id}") { serverRequest ->
                val id = serverRequest.pathVariable("id")
                val email = serverRequest.awaitBody<EmailDetail>()
                val entity = emailRepository.update(UUID.fromString(id), email)
                when {
                    entity != null -> ok().bodyValueAndAwait(entity)
                    else -> notFound().buildAndAwait()
                }
            }
        }
    }
}
