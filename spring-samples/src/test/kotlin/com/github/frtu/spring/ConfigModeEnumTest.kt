package com.github.frtu.spring

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ConfigModeEnumTest {
    @Test
    fun configModeNoOverride() {
        val configModeEnum = ConfigModeEnum.getConfigModeByName("default_value")
        Assertions.assertThat(configModeEnum).isEqualTo(ConfigModeEnum.DEFAULT_VALUE)
    }

    @Test
    fun configModeFile() {
        val configModeEnum = ConfigModeEnum.getConfigModeByName("file_value")
        Assertions.assertThat(configModeEnum).isEqualTo(ConfigModeEnum.FILE_VALUE)
    }

    @Test
    fun configModeTestFile() {
        val configModeEnum = ConfigModeEnum.getConfigModeByName("test_override_value")
        Assertions.assertThat(configModeEnum).isEqualTo(ConfigModeEnum.TEST_OVERRIDE_VALUE)
    }

    @Test
    fun getConfigMode() {
        val configModeEnum = ConfigModeEnum.getConfigModeByName("annotation_override_value")
        Assertions.assertThat(configModeEnum).isEqualTo(ConfigModeEnum.ANNOTATION_OVERRIDE_VALUE)
    }
}