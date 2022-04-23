import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    jacoco
    application
}

group = "com.github.frtu.sample.workflow.temporal"

allprojects {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = "11"
    }
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

dependencies {
    // Platform - Log
    implementation("com.github.frtu.logs:logger-core")
    implementation("ch.qos.logback:logback-classic")
    testImplementation("com.github.frtu.libs:lib-utils")
    testImplementation("org.springframework:spring-core:5.3.5")

    // Test
    testImplementation("io.mockk:mockk")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    // Platform - BOMs
    implementation(platform("com.github.frtu.archetype:kotlin-base-pom:1.2.3"))
    implementation(platform("com.github.frtu.libs:lib-kotlin-bom:1.1.5"))
    implementation(platform("com.github.frtu.logs:logger-bom:1.1.4"))
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

application {
    // Define the main class for the application.
    mainClass.set("com.github.frtu.sample.workflow.temporal.MainKt")
}