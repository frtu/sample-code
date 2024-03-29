import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    java
    kotlin("jvm")
    application

    kotlin("plugin.spring") version Versions.kotlin
    id("org.springframework.boot") version Versions.spring_boot
}
apply(plugin = "io.spring.dependency-management")

the<DependencyManagementExtension>().apply {
    imports { mavenBom(SpringBootPlugin.BOM_COORDINATES) }
}

dependencies {
    // Project
    implementation(project(":spring-boot-starter-temporal"))
    implementation(project(":activities-api"))

    // Temporal
    implementation("io.temporal:temporal-sdk:${Versions.temporal}")
    implementation("io.temporal:temporal-kotlin:${Versions.temporal}")
    testImplementation("io.temporal:temporal-testing:${Versions.temporal}")

    // Commons
    implementation(Libs.commons_configuration)

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.kafka:spring-kafka")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation(Libs.kafka_client)
}

application {
    // Define the main class for the application.
    mainClass.set("com.github.frtu.sample.workflow.temporal.producer.ProducerApplicationKt")
}