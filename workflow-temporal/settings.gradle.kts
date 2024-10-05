rootProject.name = "workflow-temporal"

include(
    "samples",
    "spring-boot/starter-temporal",
    "activities/api",
    "activities/email-activity",
    "workflows/reminder-workflow",
    "workflows/subscription-workflow",
    "workflows/subscription-service"
)

rootProject.children.forEach {
    it.name = it.name.replace("/", "-")
}

// plugin repositories
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}
// build repositories
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}