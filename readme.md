# GUI Vista Core (guivista-core)

A Kotlin Native library for developing a GTK application in a Kotlin Native project. This library uses GTK 3, and uses GTK concepts (Slot, Signal, Widget etc). **Warning** - This library depends on Kotlin Native which is currently in beta, and doesn't provide any backwards compatibility guarantees!


## Publish Library

Currently GUI Vista Core isn't available in Maven Central, JCenter, or any other remote Maven repository. Do the following to publish the library:

1. Clone this repository
2. Change working directory to where the repository has been cloned to
3. Publish the library locally via Gradle, eg `./gradlew publishLinuxX64PublicationToMavenLocal`


## Setup Gradle Build File

In order to use the library with Gradle do the following:

1. Open/create a Kotlin Native project which targets **linuxX64**
2. Open the project's **build.gradle.kts** file
3. Insert `mavenLocal()` into the **repositories** block
4. Add the library dependency:
`implementation("org.guivista:guivista-core:0.1-SNAPSHOT")`

The build file should look similar to the following:
```kotlin
// ...
repositories {
    mavenLocal()
}

kotlin {
    // ...
    linuxX64() {
        // ...
        compilations.getByName("main") {
            dependencies {
                val guiVistaVer = "0.1-SNAPSHOT"
                implementation("org.guivista:guivista-core-linuxx64:$guiVistaVer")
            }
        }
    }
}
```


## Basic Usage

A basic GTK program using this library requires using an Application object that has the Application ID, connecting the **activate** signal, and creating the AppWindow UI in the slot (event handler) for the **activate** signal.

1. Create the **main.kt** file with a **main** function
2. Define the top level **DummyData** class:
```kotlin
private class DummyData {
    val stableRef = StableRef.create(this)
}
```
3. Define the top level **appWin** variable:
```kotlin
private lateinit var appWin: AppWindow
```
4. Define the top level **activateApplication** function (handles creating the AppWindow UI):
```kotlin
private fun activateApplication(app: CPointer<GApplication>, userData: gpointer) {
    
}
```
5. Create the AppWindow UI in the **activateApplication** function:
```kotlin
private fun activateApplication(app: CPointer<GApplication>, userData: gpointer) {
    appWin.createUi {
        title = "GUI App"
        visible = true
    }
}
```
6. In the **main** function define a read only property called **data**:
```kotlin
val data = DummyData()
```
7. Once **data** is defined create a instance of Application, and pass through the **org.example.basicgui** Application ID:
```kotlin
Application("org.example.basicgui")
```
8. With the same line append the **use** function with a lambda:
```kotlin
Application("org.example.basicgui").use {}
```
9. In the lambda initialise **appWin**, connect the **activate** signal, run the application, and dispose of the stable reference to **data** after the application exits:
```kotlin
Application("org.example.basicgui").use {
    appWin = AppWin(this)
    connectActivateSignal(staticCFunction(::activateApplication), data.stableRef.asCPointer())
    println("Application Status: ${run()}")
    data.stableRef.dispose()
}
```
10. Insert the following imports:
- import gtk3.GApplication
- import gtk3.gpointer
- import kotlinx.cinterop.CPointer
- import kotlinx.cinterop.StableRef
- import kotlinx.cinterop.staticCFunction
- import org.guivista.core.Application
- import org.guivista.core.window.AppWindow


After completing the steps above the **main.kt** file should look like the following:
```kotlin
import gtk3.GApplication
import gtk3.gpointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.staticCFunction
import org.guivista.core.Application
import org.guivista.core.window.AppWindow

private lateinit var appWin: AppWindow

fun main() {
    val data = DummyData()
    Application("org.example.basicgui").use {
        appWin = AppWindow(this)
        connectActivateSignal(staticCFunction(::activateApplication), data.stableRef.asCPointer())
        println("Application Status: ${run()}")
        data.stableRef.dispose()
    }
}

@Suppress("UNUSED_PARAMETER")
private fun activateApplication(app: CPointer<GApplication>, userData: gpointer) {
    appWin.createUi {
        title = "Basic GUI"
        visible = true
    }
}

private class DummyData {
    val stableRef = StableRef.create(this)
}
```
