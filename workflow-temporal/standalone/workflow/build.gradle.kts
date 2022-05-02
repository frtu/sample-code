plugins {
    java
    kotlin("jvm")
    application
}

dependencies {
    // Temporal
    implementation("io.temporal:temporal-sdk:${Versions.temporal}")
    implementation("io.temporal:temporal-kotlin:${Versions.temporal}")
    testImplementation("io.temporal:temporal-testing:${Versions.temporal}")

    // Commons
    implementation(Libs.commons_configuration)
}

application {
    // Define the main class for the application.
    mainClass.set("com.github.frtu.sample.workflow.temporal.MainKt")
}