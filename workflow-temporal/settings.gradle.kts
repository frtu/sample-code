rootProject.name = "workflow-temporal"

include(
    "samples",
    "activities/api",
    "activities/email-activity",
    "workflows/subscription-workflow",
    "workflows/subscription-service"
)

rootProject.children.forEach {
    it.name = it.name.replace("/", "_")
}
