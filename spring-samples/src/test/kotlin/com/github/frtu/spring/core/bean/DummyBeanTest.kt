package com.github.frtu.spring.core.bean

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

// Another way to do @ExtendWith(SpringExtension::class)
@SpringJUnitConfig(TestConfig::class)
// For Kotlin, 1.2.x using arrayOf
@TestPropertySource(properties = arrayOf("prefix.key1=annotation_override_value"))
class DummyBeanTest {
    @Autowired
    lateinit var dummyBean: DummyBean

    @Test
    fun getKey() {
        // Overridden by TestPropertySource
        assertEquals("annotation_override_value", dummyBean.key1)
        // Overridden by application.properties
        assertEquals("file_value", dummyBean.key2)
        // Default value
        assertEquals("default_value", dummyBean.key3)
    }
}