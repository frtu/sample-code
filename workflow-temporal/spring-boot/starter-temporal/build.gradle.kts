plugins {
    java
    kotlin("jvm")
    kotlin("plugin.spring") version Versions.kotlin
}

dependencies {
    implementation("io.temporal:temporal-sdk:${Versions.temporal}")
    implementation("io.temporal:temporal-kotlin:${Versions.temporal}")
    implementation("io.temporal:temporal-opentracing:${Versions.temporal}")

    implementation("org.springframework.boot:spring-boot-starter")
}