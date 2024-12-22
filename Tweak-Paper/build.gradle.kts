import java.text.SimpleDateFormat
import java.util.*

plugins {
    kotlin("jvm")
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.2.0"
}

val rootName = rootProject.name
val lowercaseName = rootName.lowercase()
group = "${rootProject.group}.${lowercaseName}"
version = SimpleDateFormat("yyyy.MM.dd").format(Date()) + "-SNAPSHOT";

val minecraftVersion = "1.21.1"
dependencies {
    paperweight.paperDevBundle("$minecraftVersion-R0.1-SNAPSHOT")

    // 如果要修改需要同步 Loader 的 maven

    // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
}

paperPluginYaml {
    apiVersion = "1.20.6"
    name = rootName
    version = project.version.toString()
    main = "${project.group}.${rootName}"
    authors.add("尹")
    prefix = "微调"
    loader = "${project.group}.Loader"
    // bootstrapper = "${project.group}.Bootstrap"
}

tasks.jar {
    archiveFileName.set("${project.name}.jar")
}

kotlin {
    jvmToolchain(21)
}
