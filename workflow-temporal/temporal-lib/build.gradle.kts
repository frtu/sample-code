plugins {
    java
    kotlin("jvm")
    kotlin("plugin.spring") version Versions.kotlin
}

dependencies {
    implementation("io.temporal:temporal-sdk:${Versions.temporal}")
    implementation("org.springframework:spring-context")
}