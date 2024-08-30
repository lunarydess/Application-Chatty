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
        libs.bundles.svg.all,
        libs.bundles.swing.all,
        libs.bundles.network.all,
        libs.bundles.crypto.all,
    ).forEach { implementation(it); shadow(it) }

    var idx = 0;

    val classifiersLinux = arrayOf(
        "linux-aarch_64",
        "linux-riscv64",
        "linux-x86_64",
    )
    val nettyNativesLinux = arrayOf(
        libs.netty.transport.native.epoll.linux.aarch.get64().get(),
        libs.netty.transport.native.epoll.linux.riscv64.get(),
        libs.netty.transport.native.epoll.linux.x86.get64().get()
    )
    nettyNativesLinux.forEach {
        it.artifacts.add(it.artifact { classifier = classifiersLinux[idx] })
        idx++
        implementation(it)
        shadow(it)
    }

    idx = 0;

    val classifiersMac = arrayOf(
        "osx-aarch_64",
        "osx-x86_64",
    )
    val nettyNativesMac = arrayOf(
        libs.netty.transport.native.kqueue.osx.aarch.get64().get(),
        libs.netty.transport.native.kqueue.osx.x86.get64().get()
    )
    nettyNativesMac.forEach {
        it.artifacts.add(it.artifact { classifier = classifiersMac[idx] })
        idx++
        implementation(it)
        shadow(it)
    }

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
