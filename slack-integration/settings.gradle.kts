rootProject.name = "slack-integration"

//include(
//    ""
//)

rootProject.children.forEach {
    it.name = it.name.replace("/", "_")
}