import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    java
    application
    kotlin("jvm")
    kotlin("plugin.spring") version Versions.kotlin
    id("org.springframework.boot") version Versions.spring_boot
}
apply(plugin = "org.jetbrains.kotlin.jvm")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")
apply(plugin = "io.spring.dependency-management")

dependencies {
    // Project
    implementation(project(":temporal-lib"))
    implementation(project(":activities_api"))

    // Temporal
    implementation("io.temporal:temporal-sdk:${Versions.temporal}")
    implementation("io.temporal:temporal-kotlin:${Versions.temporal}")
    testImplementation("io.temporal:temporal-testing:${Versions.temporal}")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:${Versions.springdoc}")

//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    runtimeOnly("com.h2database:h2")
//    runtimeOnly("org.postgresql:postgresql")
//    implementation("org.flywaydb:flyway-core")

    // Commons
    implementation(Libs.commons_configuration)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // Platform - Coroutine
    implementation(Libs.coroutines_reactor)

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

the<DependencyManagementExtension>().apply {
    imports {
        mavenBom(SpringBootPlugin.BOM_COORDINATES)
    }
}
