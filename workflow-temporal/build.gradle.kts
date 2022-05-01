import org.gradle.api.tasks.diagnostics.internal.dependencies.AsciiDependencyReportRenderer
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    java
    jacoco
    pmd
    `java-library`
    `maven-publish`
    application
    id("com.github.sherter.google-java-format") version Versions.google_format
}

group = "com.github.frtu.sample.workflow.temporal"

allprojects {
    apply(plugin="project-report")

    task("allDependencies", DependencyReportTask::class) {
        evaluationDependsOnChildren()
        this.setRenderer(AsciiDependencyReportRenderer())
    }
    tasks.withType<KotlinCompile>().configureEach {
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
        kotlinOptions{
            jvmTarget = Versions.java
            languageVersion = Versions.language
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    java {
        withSourcesJar()
    }
    jacoco {
        toolVersion = Versions.jacoco
    }
    tasks {
        test {
            useJUnitPlatform()
        }
        jacocoTestCoverageVerification {
            violationRules {
                // Configure the ratio based on your standard
                rule { limit { minimum = BigDecimal.valueOf(0.0) } }
            }
        }
        check {
            dependsOn(jacocoTestCoverageVerification)
        }
    }

    repositories {
        mavenLocal()
        mavenCentral()
    }
}

dependencies {
    // Temporal
    implementation("io.temporal:temporal-sdk:${Versions.temporal}")
    implementation("io.temporal:temporal-kotlin:${Versions.temporal}")
    testImplementation("io.temporal:temporal-testing:${Versions.temporal}")

    // Platform - Log
    implementation(Libs.commons_configuration)
    implementation(Libs.logger_core)
    implementation(Libs.log_impl)
    testImplementation(Libs.lib_utils)
    testImplementation(Libs.spring_core)

    // Test
    testImplementation(Libs.junit)
    testImplementation(Libs.mock)
    testImplementation(Libs.assertions)
    testImplementation(kotlin("test"))

    // Platform - BOMs
    implementation(platform(Libs.bom_kotlin_base))
    implementation(platform(Libs.bom_kotlin_libs))
    implementation(platform(Libs.bom_logger))
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib-jdk8"))
}

application {
    // Define the main class for the application.
    mainClass.set("com.github.frtu.sample.workflow.temporal.MainKt")
}