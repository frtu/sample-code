// https://github.com/grpc/grpc-kotlin/blob/master/compiler/README.md
import com.google.protobuf.gradle.*
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    `java-library`
    kotlin("jvm")
    kotlin("plugin.spring") version Versions.kotlin
    id("org.springframework.boot") version Versions.spring_boot
    id("com.google.protobuf") version Versions.plugin_protobuf
//    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}
//apply(plugin = "org.jlleitschuh.gradle.ktlint")
apply(plugin = "idea")
apply(plugin = "org.jetbrains.kotlin.jvm")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")
apply(plugin = "io.spring.dependency-management")

group = "com.github.frtu.sample.grpc"

dependencies {
    implementation("net.devh:grpc-server-spring-boot-starter:${Versions.grpc_spring_boot_starter}")
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:${Versions.springdoc}")

    implementation("com.google.protobuf:protobuf-java:${Versions.protobuf}")
    implementation("com.google.protobuf:protobuf-java-util:${Versions.protobuf}")
    implementation("com.google.protobuf:protobuf-kotlin:${Versions.protobuf}")
    implementation("io.grpc:grpc-protobuf:${Versions.grpc}")
    implementation("io.grpc:grpc-kotlin-stub:${Versions.grpc_kotlin}")
    implementation("io.grpc:grpc-stub:${Versions.grpc}")
    implementation("io.grpc:grpc-netty:${Versions.grpc}")

    // Serialization
    implementation(Libs.jackson_databind)
    implementation(Libs.jackson_module_kotlin)

    // Platform - Coroutine
    implementation(Libs.coroutines_reactor)

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
    implementation(platform(Libs.bom_jackson))
//    implementation(platform(Libs.bom_kotlin_base))
    implementation(platform(Libs.bom_kotlin_libs))
    implementation(platform(Libs.bom_logger))
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    if (JavaVersion.current().isJava9Compatible) {
        // Workaround for @javax.annotation.Generated
        // see: https://github.com/grpc/grpc-java/issues/3633
        implementation("javax.annotation:javax.annotation-api:1.3.2")
    }
}

sourceSets {
    create("proto") {
        proto {
            srcDir("src/main/proto")
        }
    }
}

protobuf {
    protoc { artifact = "com.google.protobuf:protoc:${Versions.protobuf}" }
    plugins {
        id("grpc") { artifact = "io.grpc:protoc-gen-grpc-java:${Versions.grpc}" }
        id("grpckt") { artifact = "io.grpc:protoc-gen-grpc-kotlin:${Versions.plugin_grpc_kotlin}" }
    }
    // generatedFilesBaseDir = "$projectDir/generated"
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}
