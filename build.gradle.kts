import org.jetbrains.dokka.gradle.DokkaTask

group = "org.guivista"
version = "0.1-SNAPSHOT"

plugins {
    kotlin("multiplatform") version "1.3.72"
    id("maven-publish")
    id("org.jetbrains.dokka") version "0.10.0"
}

repositories {
    jcenter()
    mavenCentral()
    mavenLocal()
}

kotlin {
    val guiVistaIoVer = "0.1-SNAPSHOT"
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
                implementation("org.guivista:guivista-io-linuxx64:$guiVistaIoVer")
            }
        }
    }
    linuxArm32Hfp("linuxArm32")

    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val linuxX64Main by getting {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
        }

        @Suppress("UNUSED_VARIABLE")
        val linuxArm32Main by getting {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
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
    }
}
