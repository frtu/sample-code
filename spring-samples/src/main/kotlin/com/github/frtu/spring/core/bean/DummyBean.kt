package com.github.frtu.spring.core.bean

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class DummyBean {
    @Value("\${prefix.key1:default_value}")
    lateinit var key1: String

    @Value("\${prefix.key2:default_value}")
    lateinit var key2: String

    @Value("\${prefix.key3:default_value}")
    lateinit var key3: String
}