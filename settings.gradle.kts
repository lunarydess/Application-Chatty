rootProject.name = "Application-Chatty"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

include(
    ":api",
    ":client",
    ":server"
)
