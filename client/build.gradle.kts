import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.nio.charset.StandardCharsets

plugins {
    id("idea")

    id("java")
    id("java-library")

    alias(libs.plugins.shadow)
}

group = "zip.luzey.chatty"
version = "0.0.0-develop"

repositories {
    mavenCentral()
    maven(url = "https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    annotationProcessor(libs.annotations)
    implementation(libs.annotations)

    val api = project(":api")
    implementation(api)
    shadow(api)

    arrayOf(
        libs.bundles.log.all,
        libs.bundles.swing.all,
        libs.bundles.network.all,
        libs.bundles.crypto.all,
    ).forEach { implementation(it); shadow(it) }

    val libs = fileTree(
        mapOf(
            "dir" to "../libs",
            "include" to listOf("*.jar"),
            "includes" to listOf("**")
        )
    )
    implementation(libs)
    shadow(libs)
}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_21.toString()
    targetCompatibility = JavaVersion.VERSION_21.toString()
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
