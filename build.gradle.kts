import java.nio.charset.StandardCharsets

plugins {
    id("idea")
    id("java")
    id("java-library")
}

group = "zip.luzey"
version = "0.0.0-develop"

repositories {
    mavenCentral()
}

dependencies {}

tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_21.toString()
    targetCompatibility = JavaVersion.VERSION_21.toString()
    options.encoding = StandardCharsets.UTF_8.toString()
}

tasks.withType<AbstractArchiveTask> {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}
