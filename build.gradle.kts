plugins {
    kotlin("jvm") version "2.1.0"
}

group = "io.github.yin"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
}

subprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

tasks.getByName("jar").enabled = false
tasks.getByName("assemble").enabled = false
tasks.getByName("build").enabled = false