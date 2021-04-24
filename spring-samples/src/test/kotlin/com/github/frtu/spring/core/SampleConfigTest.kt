package com.github.frtu.spring.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@TestPropertySource(
    // Properties files
    value = ["classpath:core/core.properties", "classpath:core/core-test.properties"]
    // Override properties
    , properties = ["config.test.annotation=annotation_override_value"]
)
@ContextConfiguration(
    classes = [
        BasicConfig::class,
        // NEEDED TO LOAD .properties FILES
        SampleConfigTest.TestConfig::class
    ]
)
// For Kotlin, 1.2.x using arrayOf instead of []
//@ContextConfiguration(classes = arrayOf(BasicConfig::class))
//@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@ExtendWith(SpringExtension::class)
class SampleConfigTest {
    @Autowired
    lateinit var basicConfig: BasicConfig


    @Configuration
    class TestConfig {
        @Bean
        fun propertiesResolver(): PropertySourcesPlaceholderConfigurer {
            return PropertySourcesPlaceholderConfigurer()
        }
    }

    @Test
    fun configModeNoOverride() {
        assertThat(basicConfig.configModeNoOverride).isEqualTo("default_value");
    }

    @Test
    fun configModeFile() {
        assertThat(basicConfig.configModeFile).isEqualTo("file_value");
    }

    @Test
    fun configModeTestFile() {
        assertThat(basicConfig.configModeTestFile).isEqualTo("test_override_value");
    }

    @Test
    fun getConfigMode() {
        assertThat(basicConfig.configModeTestAnnotation).isEqualTo("annotation_override_value");
    }
}