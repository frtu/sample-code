rootProject.name = "workflow-temporal"

include(
    "samples"
)

rootProject.children.forEach {
    it.name = it.name.replace("/", "_")
}
