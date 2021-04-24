package com.github.frtu.spring

enum class ConfigModeEnum {
    DEFAULT_VALUE, FILE_VALUE, TEST_OVERRIDE_VALUE, ANNOTATION_OVERRIDE_VALUE;

    companion object {
        fun getConfigModeByName(name: String) = valueOf(name.toUpperCase())
    }
}