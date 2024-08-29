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

    annotationProcessor(
        group = "com.fasterxml.jackson.core",
        name = "jackson-annotations",
        version = properties["dep-conf_jackson"].toString()
    )
    implementation(
        group = "com.fasterxml.jackson.core",
        name = "jackson-annotations",
        version = properties["dep-conf_jackson"].toString()
    )
    shadow(
        group = "com.fasterxml.jackson.core",
        name = "jackson-annotations",
        version = properties["dep-conf_jackson"].toString()
    )

    implementation(
        group = "com.fasterxml.jackson.core",
        name = "jackson-core",
        version = properties["dep-conf_jackson"].toString()
    )
    shadow(
        group = "com.fasterxml.jackson.core",
        name = "jackson-core",
        version = properties["dep-conf_jackson"].toString()
    )

    implementation(
        group = "com.fasterxml.jackson.core",
        name = "jackson-databind",
        version = properties["dep-conf_jackson"].toString()
    )
    shadow(
        group = "com.fasterxml.jackson.core",
        name = "jackson-databind",
        version = properties["dep-conf_jackson"].toString()
    )

    implementation(
        group = "com.fasterxml.jackson.dataformat",
        name = "jackson-dataformat-toml",
        version = properties["dep-conf_jackson"].toString()
    )
    shadow(
        group = "com.fasterxml.jackson.dataformat",
        name = "jackson-dataformat-toml",
        version = properties["dep-conf_jackson"].toString()
    )

    implementation(
        group = "org.jline",
        name = "jline-native",
        version = properties["dep-term_jline"].toString()
    )
    shadow(
        group = "org.jline",
        name = "jline-native",
        version = properties["dep-term_jline"].toString()
    )

    implementation(
        group = "org.jline",
        name = "jline-terminal",
        version = properties["dep-term_jline"].toString()
    )
    shadow(
        group = "org.jline",
        name = "jline-terminal",
        version = properties["dep-term_jline"].toString()
    )

    implementation(
        group = "org.jline",
        name = "jline-reader",
        version = properties["dep-term_jline"].toString()
    )
    shadow(
        group = "org.jline",
        name = "jline-reader",
        version = properties["dep-term_jline"].toString()
    )

    implementation(
        group = "org.jline",
        name = "jline-terminal-jni",
        version = properties["dep-term_jline"].toString()
    )
    shadow(
        group = "org.jline",
        name = "jline-terminal-jni",
        version = properties["dep-term_jline"].toString()
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

    val libs = fileTree(
        mapOf(
            "dir" to "libs",
            "include" to listOf("*.jar"),
            "includes" to listOf("**")
        )
    )
    implementation(dependencyNotation = libs)
    shadow(dependencyNotation = libs)
}

tasks {
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
