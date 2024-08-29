rootProject.name = "Application-Chatty"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
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
