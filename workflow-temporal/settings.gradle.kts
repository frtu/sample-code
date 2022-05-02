rootProject.name = "workflow-temporal"

include(
    "samples",
    "standalone/workflow",
    "standalone/worker"
)

rootProject.children.forEach {
    it.name = it.name.replace("/", "_")
}
