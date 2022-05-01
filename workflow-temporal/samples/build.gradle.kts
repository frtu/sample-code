plugins {
    java
    kotlin("jvm")
}

dependencies {
    // Temporal
    implementation("io.temporal:temporal-sdk:${Versions.temporal}")
    implementation("io.temporal:temporal-kotlin:${Versions.temporal}")
    testImplementation("io.temporal:temporal-testing:${Versions.temporal}")

    // Commons
    implementation(Libs.commons_configuration)
}
