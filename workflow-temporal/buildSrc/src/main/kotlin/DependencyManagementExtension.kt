object Versions {
    const val java = "17"

    // Allow to enable >1.4 syntax
    const val language = "1.7"
    const val kotlin = "1.9.20"
    const val kotlinx = "1.8.1"

    const val spring_boot = "3.3.1"
    const val spring_cloud = "2023.0.3"
    const val springdoc = "1.8.0"
    const val opentelemetry = "1.42.1"
    const val jaeger = "1.8.0"
    const val frtu_base = "2.0.0"
    const val frtu_libs = "2.0.4"
    const val frtu_logs = "1.1.5"

    const val plugin_jacoco = "0.8.8" // 2022-04-05
    const val plugin_google_format = "0.9" // 2020-06-09

    const val temporal = "1.25.2"
    const val commons_configuration = "1.10"
}

object Libs {
    const val kafka_client = "org.apache.kafka:kafka-clients:2.6.1"
    const val commons_configuration = "commons-configuration:commons-configuration:${Versions.commons_configuration}"

    //---------- Commons -----------
    const val jackson_databind = "com.fasterxml.jackson.core:jackson-databind"
    const val jackson_module_kotlin = "com.fasterxml.jackson.module:jackson-module-kotlin"
    const val jackson_yaml = "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml"
    const val lib_utils = "com.github.frtu.libs:lib-utils"
    const val spring_core = "org.springframework:spring-core"

    //---------- Commons -----------
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx}"
    const val coroutines_reactive = "org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${Versions.kotlinx}"
    const val coroutines_reactor = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${Versions.kotlinx}"

    //---------- LOGS -----------
    // Implementation for slf4j
    const val log_impl = "ch.qos.logback:logback-classic"
    const val logger_core = "com.github.frtu.logs:logger-core"

    //---------- TRACING -----------
    const val opentelemetry_sdk = "io.opentelemetry:opentelemetry-sdk"
    const val opentelemetry_trace_propagators = "io.opentelemetry:opentelemetry-extension-trace-propagators"
    const val opentelemetry_semconv = "io.opentelemetry:opentelemetry-semconv"
    const val opentelemetry_shim = "io.opentelemetry:opentelemetry-opentracing-shim"

    const val opentelemetry_exporter = "io.opentelemetry:opentelemetry-exporter-jaeger"
    const val trace_impl = "io.jaegertracing:jaeger-client:${Versions.jaeger}"

    //---------- LIBS BOM -----------
    const val bom_kotlin_base = "com.github.frtu.archetype:kotlin-base-pom:${Versions.frtu_base}"
    const val bom_kotlin_libs = "com.github.frtu.libs:lib-kotlin-bom:${Versions.frtu_libs}"
    const val bom_logger = "com.github.frtu.logs:logger-bom:${Versions.frtu_logs}"
    const val bom_opentelemetry = "io.opentelemetry:opentelemetry-bom:${Versions.opentelemetry}"
    const val bom_opentelemetry_alpha = "io.opentelemetry:opentelemetry-bom-alpha:${Versions.opentelemetry}-alpha"

    //---------- TESTS -----------
    const val junit = "org.junit.jupiter:junit-jupiter"

    // Mock lib mockk or mockito
    const val mock = "io.mockk:mockk"
    const val assertions = "org.assertj:assertj-core"
}
