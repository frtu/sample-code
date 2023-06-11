package com.github.frtu.sample.autoexposure.rpc

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class DomainService(
    @get:AliasFor(annotation = Component::class)
    val value: String = ""
)