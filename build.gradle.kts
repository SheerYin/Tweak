plugins {
    kotlin("jvm") version "2.0.0"
}

group = "io.github.yin"
version = "1.0.0"

subprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

tasks.getByName("jar").enabled = false
tasks.getByName("assemble").enabled = false
tasks.getByName("build").enabled = false