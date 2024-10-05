package com.github.frtu.sample.workflow.temporal.subscription

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("prefix")
data class AppProperties(var key1: String) {
    data class App(val key1: String? = null)
}