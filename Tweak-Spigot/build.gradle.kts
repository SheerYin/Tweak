import java.text.SimpleDateFormat
import java.util.*

plugins {
    kotlin("jvm")
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.2.0"
}

val rootName = rootProject.name
val lowercaseName = rootName.lowercase()
group = "${rootProject.group}.${lowercaseName}"
version = SimpleDateFormat("yyyy.MM.dd").format(Date()) + "-SNAPSHOT";

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

    // maven("https://libraries.minecraft.net/")
    // maven("https://repo.codemc.io/repository/nms/")
}

val minecraftVersion = "1.20.1"
dependencies {
    compileOnly("org.spigotmc:spigot-api:${minecraftVersion}-R0.1-SNAPSHOT")
    // compileOnly("org.spigotmc:spigot:${minecraftVersion}-R0.1-SNAPSHOT")

    // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
}

bukkitPluginYaml {
    apiVersion = "1.16"
    name = rootName
    version = project.version.toString()
    main = "${project.group}.${rootName}"
    authors.add("尹")
    prefix = "微调"
    libraries = listOf("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")
}

tasks.jar {
    archiveFileName.set("${project.name}.jar")
}

kotlin {
    jvmToolchain(17)
}
