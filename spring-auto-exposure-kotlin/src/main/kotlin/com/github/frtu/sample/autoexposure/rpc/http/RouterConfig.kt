package com.github.frtu.sample.autoexposure.rpc.http

import com.github.frtu.logs.core.RpcLogger
import com.github.frtu.sample.autoexposure.domain.DomainInterface
import com.github.frtu.sample.autoexposure.persistence.basic.IEmailRepository
import com.github.frtu.sample.autoexposure.rpc.DomainService
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.*

@Configuration
class RouterConfig(
    applicationContext: ApplicationContext,
) {
    private lateinit var domainService: DomainInterface

    private val uriPath: String = "/v1/emails"
    internal val logger = LoggerFactory.getLogger(this::class.java)

    init {
        val beansWithAnnotation: Map<String, Any> = applicationContext.getBeansWithAnnotation(DomainService::class.java)
        with(beansWithAnnotation["service1"]) {
            domainService = this as DomainInterface
        }
        logger.info("domainService:$domainService")
    }

    @Bean
    fun route(): RouterFunction<*> = coRouter {
        // Get ID
        GET("$uriPath/{id}") { serverRequest ->
            val id = serverRequest.pathVariable("id")
            val entity = domainService.exec(id)
            when {
                entity != null -> ok().json().bodyValueAndAwait(entity)
                else -> notFound().buildAndAwait()
            }
        }
    }
}
