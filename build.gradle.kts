import org.jetbrains.dokka.gradle.DokkaTask
import java.util.Properties

val gitLabSettings = fetchGitLabSettings()
val projectSettings = fetchProjectSettings()

group = "org.guivista"
version = if (projectSettings.isDevVer) "${projectSettings.libVer}-dev" else projectSettings.libVer

plugins {
    kotlin("multiplatform") version "1.3.72"
    id("maven-publish")
    id("org.jetbrains.dokka") version "0.10.0"
}

repositories {
    jcenter()
    mavenCentral()
    maven {
        val guiVistaCore = "16245519"
        url = uri("https://gitlab.com/api/v4/projects/$guiVistaCore/packages/maven")
    }
    maven {
        val guiVistaIo = "16243425"
        url = uri("https://gitlab.com/api/v4/projects/$guiVistaIo/packages/maven")
    }
}

kotlin {
    linuxX64("linuxX64") {
        compilations.getByName("main") {
            cinterops.create("gtk3") {
                val userIncludeDir = "/usr/include"
                includeDirs(
                    "$userIncludeDir/atk-1.0",
                    "$userIncludeDir/gdk-pixbuf-2.0",
                    "$userIncludeDir/cairo",
                    "$userIncludeDir/pango-1.0",
                    "$userIncludeDir/gtk-3.0",
                    "$userIncludeDir/glib-2.0",
                    "/usr/lib/x86_64-linux-gnu/glib-2.0/include"
                )
            }
            dependencies {
                implementation("org.guivista:guivista-core-linuxx64:${projectSettings.libVer}")
                implementation("org.guivista:guivista-io-linuxx64:${projectSettings.libVer}")
            }
        }
    }
    linuxArm32Hfp("linuxArm32") {
        compilations.getByName("main") {
            cinterops.create("gtk3") {
                val userIncludeDir = "/mnt/pi_image/usr/include"
                includeDirs(
                    "$userIncludeDir/atk-1.0",
                    "$userIncludeDir/gdk-pixbuf-2.0",
                    "$userIncludeDir/cairo",
                    "$userIncludeDir/pango-1.0",
                    "$userIncludeDir/gtk-3.0",
                    "$userIncludeDir/glib-2.0",
                    "/mnt/pi_image/usr/lib/arm-linux-gnueabihf/glib-2.0/include"
                )
            }
            dependencies {
                implementation("org.guivista:guivista-core-linuxarm32:${projectSettings.libVer}")
                implementation("org.guivista:guivista-io-linuxarm32:${projectSettings.libVer}")
            }
        }
    }

    sourceSets {
        val unsignedTypes = "kotlin.ExperimentalUnsignedTypes"

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            languageSettings.useExperimentalAnnotation(unsignedTypes)
            dependencies {
                val kotlinVer = "1.3.72"
                implementation(kotlin("stdlib-common", kotlinVer))
                implementation("org.guivista:guivista-core:${projectSettings.libVer}")
                implementation("org.guivista:guivista-io:${projectSettings.libVer}")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val linuxX64Main by getting {
            languageSettings.useExperimentalAnnotation(unsignedTypes)
        }

        @Suppress("UNUSED_VARIABLE")
        val linuxArm32Main by getting {
            languageSettings.useExperimentalAnnotation(unsignedTypes)
        }
    }
}

publishing {
    repositories {
        maven {
            val projectId = gitLabSettings.projectId
            url = uri("https://gitlab.com/api/v4/projects/$projectId/packages/maven")
            credentials(HttpHeaderCredentials::class.java) {
                name = "Private-Token"
                value = gitLabSettings.token
            }
            authentication {
                create("header", HttpHeaderAuthentication::class.java)
            }
        }
    }
}

tasks.create("createLinuxLibraries") {
    dependsOn("linuxX64MainKlibrary", "linuxArm32MainKlibrary")
}

tasks.getByName("dokka", DokkaTask::class) {
    outputDirectory = "$buildDir/dokka"
    outputFormat = "html"
    multiplatform {
        create("linuxX64")
        create("linuxArm32")
    }
}

tasks.getByName("publish") {
    doFirst { println("Project Version: ${project.version}") }
}

data class GitLabSettings(val token: String, val projectId: Int)

fun fetchGitLabSettings(): GitLabSettings {
    var token = ""
    var projectId = -1
    val properties = Properties()
    file("gitlab.properties").inputStream().use { inputStream ->
        properties.load(inputStream)
        token = properties.getProperty("token") ?: ""
        @Suppress("RemoveSingleExpressionStringTemplate")
        projectId = "${properties.getProperty("projectId")}".toInt()
    }
    return GitLabSettings(token = token, projectId = projectId)
}

data class ProjectSettings(val libVer: String, val isDevVer: Boolean)

fun fetchProjectSettings(): ProjectSettings {
    var libVer = "SNAPSHOT"
    var isDevVer = true
    val properties = Properties()
    file("project.properties").inputStream().use { inputStream ->
        properties.load(inputStream)
        libVer = properties.getProperty("libVer") ?: "SNAPSHOT"
        @Suppress("RemoveSingleExpressionStringTemplate")
        isDevVer = "${properties.getProperty("isDevVer")}".toBoolean()
    }
    return ProjectSettings(libVer = libVer, isDevVer = isDevVer)
}
