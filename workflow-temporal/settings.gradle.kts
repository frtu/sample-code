rootProject.name = "workflow-temporal"

include(
    "samples",
    "integration/workflow-main"
    "activities/api",
    "activities/email-activity",
)

rootProject.children.forEach {
    it.name = it.name.replace("/", "_")
}
