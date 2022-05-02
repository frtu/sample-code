rootProject.name = "workflow-temporal"

include(
    "samples",
    "standalone/workflow"
)

rootProject.children.forEach {
    it.name = it.name.replace("/", "_")
}
