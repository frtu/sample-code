plugins {
    java
    kotlin("jvm")
}

dependencies {
    implementation("io.temporal:temporal-sdk:${Versions.temporal}")
}