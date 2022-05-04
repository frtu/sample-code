rootProject.name = "workflow-temporal"

include(
    "samples",
    "integration/activity-sink",
    "integration/workflow-main"
    "activities/api",
)

rootProject.children.forEach {
    it.name = it.name.replace("/", "_")
}
