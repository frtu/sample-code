package com.github.frtu.spring.boot

import com.github.frtu.spring.core.SampleConfigTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.runner.ApplicationContextRunner

@TestPropertySource("classpath:boot/application-test.properties")
@ContextConfiguration(
    classes = [
        SampleBootConfig::class
        // NEEDED TO LOAD .properties FILES
        , SampleConfigTest.TestConfig::class
    ]
)
@SpringBootTest
class SampleBootConfigTest {
    @Autowired(required = false)
    var bean1: SampleBootConfig.Bean1? = null

    @Autowired(required = false)
    var bean2: SampleBootConfig.Bean2? = null

    @Autowired(required = false)
    var bean3: SampleBootConfig.Bean3? = null

    @Autowired(required = false)
    var bean4: SampleBootConfig.Bean4? = null

    @Test
    fun getAutowiredBeanFromAnnotation() {
        assertThat(bean1?.key1).isEqualTo("test_override_value")
        // if 'true' == 'true' => null since 'false'
        assertThat(bean2).isNull()
        // run if string is empty => null
        assertThat(bean3).isNull()
        // By default, bean is created
        assertThat(bean4).isNotNull()
    }

    private val contextRunner = ApplicationContextRunner()

    @Test
    fun getBeanWithApplicationContextRunner() {
        contextRunner
            .withPropertyValues(
                // Test override to prevent bean to be created
                "prefix.key1=code_override_value",
                // if 'true' == 'true'
                "application.config.enabled=true",
                "application.config.to-be-override=OVERRIDE",
                "application.config.mode=TEST_OVERRIDE_VALUE"
            )
            .withUserConfiguration(SampleBootConfig::class.java)
            .run { context ->
                val bean11 = context.getBean(SampleBootConfig.Bean1::class.java)
                assertThat(context).hasSingleBean(SampleBootConfig.Bean1::class.java)
                assertThat(bean11.key1).isEqualTo("code_override_value")
                assertThat(context).hasSingleBean(SampleBootConfig.Bean2::class.java)
                assertThat(context).hasSingleBean(SampleBootConfig.Bean3::class.java)
                assertThat(context).doesNotHaveBean(SampleBootConfig.Bean4::class.java)
            }
    }
}