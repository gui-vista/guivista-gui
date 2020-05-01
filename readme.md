# GUI Vista GUI (guivista-gui)

A Kotlin Native library for developing a GTK application in a Kotlin Native project. This library uses GTK 3, and 
utilises GTK concepts (Slot, Signal, Widget etc). **Warning** - This library depends on Kotlin Native which is 
currently in beta, and doesn't provide any backwards compatibility guarantees! Currently, GUI Vista GUI isn't available 
in Maven Central or JCenter, but is available in a remote GitLab Maven repository.


## Setup Gradle Build File

In order to use the library with Gradle do the following:

1. Open/create a Kotlin Native project which targets **linuxX64** or **linuxArm32Hfp**
2. Open the project's **build.gradle.kts** file
3. Insert the following into the **repositories** block:
```kotlin
maven {
    val guiVistaCore = "16245519"
    url = uri("https://gitlab.com/api/v4/projects/$guiVistaCore/packages/maven")
}
maven {
    val guiVistaIo = "16243425"
    url = uri("https://gitlab.com/api/v4/projects/$guiVistaIo/packages/maven")
}
maven {
    val guiVistaGui = "15889948"
    url = uri("https://gitlab.com/api/v4/projects/$guiVistaGui/packages/maven")
}
```
4. Create a library definition file called **glib2.def** which contains the following:
```
linkerOpts = -lglib-2.0 -lgobject-2.0
linkerOpts.linux_x64 = -L/usr/lib/x86_64-linux-gnu
linkerOpts.linux_arm32_hfp = -L/mnt/pi_image/usr/lib/arm-linux-gnueabihf
```
5. Create a library definition file called **gio2.def** which contains the following:
```
linkerOpts = -lgio-2.0
linkerOpts.linux_x64 = -L/usr/lib/x86_64-linux-gnu
linkerOpts.linux_arm32_hfp = -L/mnt/pi_image/usr/lib/arm-linux-gnueabihf
```
6. Create a library definition file called **gtk3.def** which contains the following:
```
linkerOpts = -lgtk-3 -lgdk-3 -latk-1.0 -lpangocairo-1.0 -lgdk_pixbuf-2.0 -lcairo-gobject -lpango-1.0 -lcairo
linkerOpts.linux_x64 = -L/usr/lib/x86_64-linux-gnu
linkerOpts.linux_arm32_hfp = -L/mnt/pi_image/usr/lib/arm-linux-gnueabihf
```
7. Add the following C library dependencies:
```kotlin
cinterops.create("glib2")
cinterops.create("gio2")
cinterops.create("gtk3")
```
8. Add the GUI Vista GUI library dependency: `implementation("org.guivista:guivista-gui:$guiVistaVer")`

The build file should look similar to the following:
```kotlin
// ...
repositories {
    maven {
        val guiVistaCore = "16245519"
        url = uri("https://gitlab.com/api/v4/projects/$guiVistaCore/packages/maven")
    }
    maven {
        val guiVistaIo = "16243425"
        url = uri("https://gitlab.com/api/v4/projects/$guiVistaIo/packages/maven")
    }
    maven {
        val guiVistaGui = "15889948"
        url = uri("https://gitlab.com/api/v4/projects/$guiVistaGui/packages/maven")
    }
}

kotlin {
    // ...
    linuxX64 {
        // ...
        compilations.getByName("main") {
            dependencies {
                val guiVistaVer = "0.1.1"
                cinterops.create("glib2")
                cinterops.create("gio2")
                cinterops.create("gtk3")
                implementation("org.guivista:guivista-gui:$guiVistaVer")
            }
        }
    }
}
```

## Basic Usage

A basic GTK program using this library requires using an Application object that has the Application ID, connecting the 
**activate** signal, and creating the AppWindow UI in the slot (event handler) for the **activate** signal.

1. Create the **main.kt** file with a **main** function
2. Define the top level **appWin** variable:
```kotlin
private lateinit var appWin: AppWindow
```
3. Define the top level **activateApplication** function (handles creating the AppWindow UI):
```kotlin
private fun activateApplication(app: CPointer<GApplication>, userData: gpointer) {}
```
4. Print the Application ID, and create the AppWindow UI in the **activateApplication** function:
```kotlin
private fun activateApplication(app: CPointer<GApplication>, userData: gpointer) {
    println("Application ID: ${Application(appPtr = app).appId}")
    appWin.createUi {
        title = "GUI App"
        visible = true
    }
}
```
5. Create a instance of Application, pass through the **org.example.basicgui** Application ID, and call the *use* 
function with a lambda:
```kotlin
Application(id = "org.example.basicgui").use {}
```
6. In the lambda initialise **appWin**, connect the **activate** signal, and run the application:
```kotlin
Application("org.example.basicgui").use {
    appWin = AppWin(this)
    connectActivateSignal(staticCFunction(::activateApplication), fetchEmptyDataPointer())
    println("Application Status: ${run()}")
}
```
7. Insert the following imports:
- import gtk3.GApplication
- import gtk3.gpointer
- import kotlinx.cinterop.CPointer
- import kotlinx.cinterop.StableRef
- import kotlinx.cinterop.staticCFunction
- import org.guiVista.gui.Application
- import org.guiVista.gui.window.AppWindow
- import org.guiVista.gui.fetchEmptyDataPointer


After completing the steps above the **main.kt** file should look like the following:
```kotlin
import gtk3.GApplication
import gtk3.gpointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.staticCFunction
import org.guiVista.gui.Application
import org.guiVista.gui.window.AppWindow
import org.guiVista.gui.fetchEmptyDataPointer

private lateinit var appWin: AppWindow

fun main() {
    Application(id = "org.example.basicgui").use {
        appWin = AppWindow(this)
        connectActivateSignal(staticCFunction(::activateApplication), fetchEmptyDataPointer())
        println("Application Status: ${run()}")
    }
}

@Suppress("UNUSED_PARAMETER")
private fun activateApplication(app: CPointer<GApplication>, userData: gpointer) {
    appWin.createUi {
        title = "Basic GUI"
        visible = true
    }
}
```

## Custom Window

With GUI Vista GUI it is possible to develop a custom window. In order to do so a class needs to be defined that either 
extends `org.guiVista.gui.window.Window`, or implements `org.guiVista.gui.window.AppWindow`. Both Window, and AppWindow 
get their properties/functions from `org.guiVista.gui.window.WindowBase`.

If there is a main layout used then the `createMainLayout` function should be overridden to create the layout. The UI 
for the window should be created in the `createUi` function (override it). Default focus for the window is handled in 
the `resetFocus` function (override it), where a widget in the window **"grabs"** focus. Below is a example of a custom 
window:

```kotlin
// ...

internal class MainWindow(app: Application) : AppWindow(app) {
    private val nameEntry by lazy { createNameEntry() }
    private val greetingLbl by lazy { createGreetingLbl() }

    private fun createGreetingLbl() = labelWidget(text = "") {}

    override fun createMainLayout(): Container? = boxLayout(orientation = GtkOrientation.GTK_ORIENTATION_VERTICAL) {
        spacing = 20
        changeAllMargins(5)
        this += createInputLayout()
        this += greetingLbl
    }

    private fun createInputLayout() = boxLayout {
        spacing = 5
        prependChild(nameEntry)
        prependChild(createGreetingBtn())
    }

    private fun createNameEntry() = entryWidget {
        text = ""
        placeholderText = "Enter name"
    }

    private fun createGreetingBtn() = buttonWidget(label = "Display Greeting") {
        // Signals (events) are connected here...
    }

    fun updateGreeting() {
        greetingLbl.text = "Hello ${nameEntry.text}! :)"
        nameEntry.text = ""
    }
}

// Custom slots (event handlers) are defined here...
```

## Handling Signals

With GTK signals (events) are handled by connecting slots (event handlers) to them. When a signal no longer needs to be 
handled by a slot then the slot is disconnected (via the `disconnectSignal` function). Each slot needs 
to be defined as a top level function, and cannot capture Kotlin class instances. Below is a example of a defined slot:

```kotlin
private fun greetingBtnClicked(@Suppress("UNUSED_PARAMETER") btn: CPointer<GtkButton>?, userData: gpointer) {
    val mainWin = userData.asStableRef<MainWindow>().get()
    mainWin.updateGreeting()
}
```

Note that a stable reference is used, which comes from the **userData** parameter. A stable reference allows a Kotlin 
class instance to be used without capturing it in the slot. Below is a example of connecting a slot to a signal:

```kotlin
connectClickedSignal(staticCFunction(::greetingBtnClicked), stableRef.asCPointer())
```

A slot has to be converted to a `CFunction` first (via the `staticCFunction` function), **before** it can be used as a 
function reference. If a Kotlin class instance is to be accessible in the slot then it needs to be passed as a 
`CPointer` (eg `stableRef.asCPointer()`). A stable reference is defined in a Kotlin class definition, eg:

```kotlin
class MainWindow(app: Application) : AppWindow(app) {
    val stableRef = StableRef.create(this)
    // ...
}
```

Alternatively if the Kotlin class instance has state/functionality that is accessible via a `CPointer` then the Kotlin 
class creation technique can be used in a slot, eg:

```kotlin
private fun activateApplication(app: CPointer<GApplication>, @Suppress("UNUSED_PARAMETER") userData: gpointer) {
    println("Activating application...")
    println("Application ID: ${Application(appPtr = app).appId}")
    // ...
}
```
