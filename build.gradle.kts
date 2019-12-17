group = "org.guivista"
version = "0.1-SNAPSHOT"

plugins {
    kotlin("multiplatform") version "1.3.61"
    id("maven-publish")
}

repositories {
    jcenter()
    mavenCentral()
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
        }
    }
    sourceSets {
        @Suppress("UNUSED_VARIABLE") val linuxX64Main by getting {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
        }
    }
}
