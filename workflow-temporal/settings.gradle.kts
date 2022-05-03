rootProject.name = "workflow-temporal"

include(
    "samples",
    "integration/activity-api",
    "integration/activity-sink",
    "integration/workflow-main"
)

rootProject.children.forEach {
    it.name = it.name.replace("/", "_")
}
