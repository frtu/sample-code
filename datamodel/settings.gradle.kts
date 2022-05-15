rootProject.name = "datamodel"

include(
    "datamodel-protobuf"
)

rootProject.children.forEach {
    it.name = it.name.replace("/", "-")
}