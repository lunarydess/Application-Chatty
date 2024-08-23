import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.nio.charset.StandardCharsets

plugins {
    id("idea")

    id("java")
    id("java-library")

    id("com.gradleup.shadow") version "8.3.0"
}

group = "zip.luzey.chatty"
version = "0.0.0-develop"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor(
        group = "org.jetbrains",
        name = "annotations",
        version = properties["dep-ann_jbr"].toString()
    )
    implementation(
        group = "org.jetbrains",
        name = "annotations",
        version = properties["dep-ann_jbr"].toString()
    )

    val api = project(":api")
    implementation(api)
    shadow(api)

    implementation(
        group = "com.miglayout",
        name = "miglayout-swing",
        version = properties["dep-ui_miglayout"].toString()
    )
    shadow(
        group = "com.miglayout",
        name = "miglayout-swing",
        version = properties["dep-ui_miglayout"].toString()
    )

    implementation(
        group = "com.formdev",
        name = "flatlaf",
        version = properties["dep-ui_flatlaf"].toString()
    )
    shadow(
        group = "com.formdev",
        name = "flatlaf",
        version = properties["dep-ui_flatlaf"].toString()
    )

    implementation(
        group = "com.formdev",
        name = "flatlaf-intellij-themes",
        version = properties["dep-ui_flatlaf"].toString()
    )
    shadow(
        group = "com.formdev",
        name = "flatlaf-intellij-themes",
        version = properties["dep-ui_flatlaf"].toString()
    )
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_17.toString()
    targetCompatibility = JavaVersion.VERSION_17.toString()
    options.encoding = StandardCharsets.UTF_8.toString()
}

tasks.withType<AbstractArchiveTask> {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}

tasks.withType<ShadowJar> {
    configurations = listOf(project.configurations.shadow.get())
    isZip64 = true
}

configurations.shadow { isTransitive = false }
