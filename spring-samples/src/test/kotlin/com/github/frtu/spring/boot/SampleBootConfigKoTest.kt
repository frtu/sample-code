package com.github.frtu.spring.boot

import com.github.frtu.spring.core.SampleConfigTest
import io.kotlintest.matchers.types.shouldBeNull
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.runner.ApplicationContextRunner

@TestPropertySource("classpath:boot/application-yml.yaml")
@ContextConfiguration(
    classes = [
        SampleBootConfig::class,
        SampleConfigTest.TestConfig::class,
    ]
)
@SpringBootTest
class SampleBootConfigKoTest {
    @Autowired
    var bean1: SampleBootConfig.Bean1? = null

    @Autowired
    var bean2: SampleBootConfig.Bean2? = null

    @Autowired
    var bean3: SampleBootConfig.Bean3? = null

    @Autowired
    var bean4: SampleBootConfig.Bean4? = null

    @Test
    fun getAutowiredBeanFromAnnotation() {
        bean1?.key1 shouldBe "test_override_value"
        // if 'true' == 'true' => null since 'false'
        bean2.shouldBeNull()
        // run if string is empty => null
        bean3.shouldBeNull()
        // By default, bean is created
        bean4.shouldNotBeNull()
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
                with(context.getBean(SampleBootConfig.Bean1::class.java)) {
                    shouldNotBeNull()
                    key1 shouldBe "code_override_value"
                }
                context.getBean(SampleBootConfig.Bean2::class.java).shouldNotBeNull()
                context.getBean(SampleBootConfig.Bean3::class.java).shouldNotBeNull()
                shouldThrow<NoSuchBeanDefinitionException> { context.getBean(SampleBootConfig.Bean4::class.java) }
            }
    }
}