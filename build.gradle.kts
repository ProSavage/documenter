import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.ReplaceTokens


plugins {
    kotlin("jvm") version "1.4.21"
    id("com.github.johnrengelman.shadow") version "5.1.0"
    id("maven")
}

group = "net.savagelabs.dockerdocumenter"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://nexus.savagelabs.net/repository/maven-releases/")
    maven("https://repo.codemc.org/repository/maven-releases")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://jitpack.io")
    mavenLocal()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly("org.spigotmc:spigot-api:1.16.2-R0.1-SNAPSHOT")
}

tasks {
    processResources {
        filter<ReplaceTokens>(
            "tokens" to mapOf(
                "project.version" to project.version
            )
        )
    }

    val build by existing {
        dependsOn(processResources)
        dependsOn(shadowJar)
    }

    val shadowJar = named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
        minimize()

        val shadePath = "net.savagelabs.dockerdocumenter.shade"
        relocate("kotlinx", "$shadePath.kotlinx")
        relocate("kotlin", "$shadePath.kotlin")
        archiveFileName.set("${project.name}-${project.version}.jar")
    }
}