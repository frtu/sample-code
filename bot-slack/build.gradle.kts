plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.21" // use the latest Kotlin version
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.slack.api:slack-api-client:1.40.3")

    // Add these dependencies if you want to use the Kotlin DSL for building rich messages
    implementation("com.slack.api:slack-api-model-kotlin-extension:1.40.3")
    implementation("com.slack.api:slack-api-client-kotlin-extension:1.40.3")
}

application {
    mainClassName.set("com.github.frtu.sample.bot.slack.ApplicationKt")
}
