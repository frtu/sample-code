package com.github.frtu.workflow.temporal.config

import com.github.frtu.workflow.temporal.observability.JaegerUtils
import io.temporal.opentracing.OpenTracingClientInterceptor
import io.temporal.opentracing.OpenTracingOptions
import io.temporal.opentracing.OpenTracingWorkerInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObservabilityConfig {
    @Value("\${spring.application.name}")
    lateinit var applicationName: String

    @Value("\${jaeger.endpoint:http://localhost:14250}")
    lateinit var jaegerEndpoint: String

    @Bean
    fun tracingOptions(): OpenTracingOptions = JaegerUtils.getJaegerOptions(applicationName, jaegerEndpoint)

    @Bean
    fun tracingClientInterceptor(tracingOptions: OpenTracingOptions) = OpenTracingClientInterceptor(tracingOptions)

    @Bean
    fun tracingWorkerInterceptor(tracingOptions: OpenTracingOptions) = OpenTracingWorkerInterceptor(tracingOptions)
}