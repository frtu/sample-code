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
    id("com.github.sherter.google-java-format") version Versions.plugin_google_format
}

group = "com.github.frtu.sample.workflow.temporal"

allprojects {
    apply(plugin = "java")
    apply(plugin = "jacoco")
    apply(plugin = "com.github.sherter.google-java-format")
    apply(plugin = "project-report")

    task("allDependencies", DependencyReportTask::class) {
        evaluationDependsOnChildren()
        this.setRenderer(AsciiDependencyReportRenderer())
    }
    tasks.withType<KotlinCompile>().configureEach {
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
        kotlinOptions {
            jvmTarget = Versions.java
            languageVersion = Versions.language
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
    java {
        sourceCompatibility = JavaVersion.toVersion(Versions.java)
        targetCompatibility = JavaVersion.toVersion(Versions.java)
        withSourcesJar()
    }
    jacoco {
        toolVersion = Versions.plugin_jacoco
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

subprojects {
    dependencies {
        // Platform - Log
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
}