rootProject.name = "bot-slack"

rootProject.children.forEach {
    it.name = it.name.replace("/", "_")
}