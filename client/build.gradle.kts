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
    maven(url = "https://oss.sonatype.org/content/groups/public/")
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
        group = "org.slf4j",
        name = "slf4j-api",
        version = properties["dep-log_slf4j"].toString()
    )
    shadow(
        group = "org.slf4j",
        name = "slf4j-api",
        version = properties["dep-log_slf4j"].toString()
    )

    implementation(
        group = "org.tinylog",
        name = "tinylog-api",
        version = properties["dep-log_tinylog"].toString()
    )
    shadow(
        group = "org.tinylog",
        name = "tinylog-api",
        version = properties["dep-log_tinylog"].toString()
    )

    implementation(
        group = "org.tinylog",
        name = "tinylog-impl",
        version = properties["dep-log_tinylog"].toString()
    )
    shadow(
        group = "org.tinylog",
        name = "tinylog-impl",
        version = properties["dep-log_tinylog"].toString()
    )

    implementation(
        group = "org.tinylog",
        name = "slf4j-tinylog",
        version = properties["dep-log_tinylog"].toString()
    )
    shadow(
        group = "org.tinylog",
        name = "slf4j-tinylog",
        version = properties["dep-log_tinylog"].toString()
    )

    implementation(
        group = "com.miglayout",
        name = "miglayout-swing",
        version = properties["dep_ui-miglayout"].toString()
    )
    shadow(
        group = "com.miglayout",
        name = "miglayout-swing",
        version = properties["dep_ui-miglayout"].toString()
    )

    implementation(
        group = "com.formdev",
        name = "flatlaf",
        version = properties["dep_ui-flatlaf"].toString()
    )
    shadow(
        group = "com.formdev",
        name = "flatlaf",
        version = properties["dep_ui-flatlaf"].toString()
    )

    implementation(
        group = "com.formdev",
        name = "flatlaf-intellij-themes",
        version = properties["dep_ui-flatlaf"].toString()
    )
    shadow(
        group = "com.formdev",
        name = "flatlaf-intellij-themes",
        version = properties["dep_ui-flatlaf"].toString()
    )

    implementation(
        group = "io.netty",
        name = "netty-common",
        version = properties["dep_net-netty"].toString()
    )
    shadow(
        group = "io.netty",
        name = "netty-common",
        version = properties["dep_net-netty"].toString()
    )

    implementation(
        group = "io.netty",
        name = "netty-buffer",
        version = properties["dep_net-netty"].toString()
    )
    shadow(
        group = "io.netty",
        name = "netty-buffer",
        version = properties["dep_net-netty"].toString()
    )

    implementation(
        group = "io.netty",
        name = "netty-codec",
        version = properties["dep_net-netty"].toString()
    )
    shadow(
        group = "io.netty",
        name = "netty-codec",
        version = properties["dep_net-netty"].toString()
    )

    implementation(
        group = "io.netty",
        name = "netty-codec-compression",
        version = properties["dep_net-netty"].toString()
    )
    shadow(
        group = "io.netty",
        name = "netty-codec-compression",
        version = properties["dep_net-netty"].toString()
    )

    implementation(
        group = "io.netty",
        name = "netty-resolver",
        version = properties["dep_net-netty"].toString()
    )
    shadow(
        group = "io.netty",
        name = "netty-resolver",
        version = properties["dep_net-netty"].toString()
    )

    implementation(
        group = "io.netty",
        name = "netty-codec",
        version = properties["dep_net-netty"].toString()
    )
    shadow(
        group = "io.netty",
        name = "netty-codec",
        version = properties["dep_net-netty"].toString()
    )

    implementation(
        group = "io.netty",
        name = "netty-transport",
        version = properties["dep_net-netty"].toString()
    )
    shadow(
        group = "io.netty",
        name = "netty-transport",
        version = properties["dep_net-netty"].toString()
    )

    implementation(
        group = "io.netty",
        name = "netty-transport-classes-epoll",
        version = properties["dep_net-netty"].toString()
    )
    shadow(
        group = "io.netty",
        name = "netty-transport-classes-epoll",
        version = properties["dep_net-netty"].toString()
    )

    implementation(
        group = "io.netty",
        name = "netty-transport-classes-kqueue",
        version = properties["dep_net-netty"].toString()
    )
    shadow(
        group = "io.netty",
        name = "netty-transport-classes-kqueue",
        version = properties["dep_net-netty"].toString()
    )

    implementation(
        group = "io.netty",
        name = "netty-transport-native-epoll",
        version = properties["dep_net-netty"].toString(),
        classifier = "linux-aarch_64"
    )
    shadow(
        group = "io.netty",
        name = "netty-transport-native-epoll",
        version = properties["dep_net-netty"].toString(),
        classifier = "linux-aarch_64"
    )

    /*implementation(
        group = "io.netty",
        name = "netty-transport-native-epoll",
        version = properties["dep_net-netty"].toString(),
        classifier = "linux-riscv64"
    )
    shadow(
        group = "io.netty",
        name = "netty-transport-native-epoll",
        version = properties["dep_net-netty"].toString(),
        classifier = "linux-riscv64"
    )*/

    implementation(
        group = "io.netty",
        name = "netty-transport-native-epoll",
        version = properties["dep_net-netty"].toString(),
        classifier = "linux-x86_64"
    )
    shadow(
        group = "io.netty",
        name = "netty-transport-native-epoll",
        version = properties["dep_net-netty"].toString(),
        classifier = "linux-x86_64"
    )

    implementation(
        group = "io.netty",
        name = "netty-transport-native-kqueue",
        version = properties["dep_net-netty"].toString(),
        classifier = "osx-aarch_64"
    )
    shadow(
        group = "io.netty",
        name = "netty-transport-native-kqueue",
        version = properties["dep_net-netty"].toString(),
        classifier = "osx-aarch_64"
    )

    implementation(
        group = "io.netty",
        name = "netty-transport-native-kqueue",
        version = properties["dep_net-netty"].toString(),
        classifier = "osx-x86_64"
    )
    shadow(
        group = "io.netty",
        name = "netty-transport-native-kqueue",
        version = properties["dep_net-netty"].toString(),
        classifier = "osx-x86_64"
    )

    implementation(
        group = "io.netty",
        name = "netty-handler",
        version = properties["dep_net-netty"].toString()
    )
    shadow(
        group = "io.netty",
        name = "netty-handler",
        version = properties["dep_net-netty"].toString()
    )

    implementation(
        group = "com.jcraft",
        name = "jzlib",
        version = properties["dep_net-jzlib"].toString()
    )
    shadow(
        group = "com.jcraft",
        name = "jzlib",
        version = properties["dep_net-jzlib"].toString()
    )

    implementation(
        group = "org.bouncycastle",
        name = "bcprov-jdk18on",
        version = properties["dep_net-bcprov"].toString()
    )
    shadow(
        group = "org.bouncycastle",
        name = "bcprov-jdk18on",
        version = properties["dep_net-bcprov"].toString()
    )
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
