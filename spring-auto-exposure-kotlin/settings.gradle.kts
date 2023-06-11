rootProject.name = "spring-auto-exposure-kotlin"

//include(
//    ""
//)

rootProject.children.forEach {
    it.name = it.name.replace("/", "_")
}