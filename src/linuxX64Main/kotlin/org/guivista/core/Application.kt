package org.guivista.core

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlin.system.exitProcess

/** Handles a GTK application including application lifecycle, and session management. */
class Application(appPtr: CPointer<GApplication>? = null, id: String = "org.example.gui-app") : ApplicationBase {
    val gtkAppPtr: CPointer<GtkApplication> = appPtr?.reinterpret() ?: createGtkAppPtr(id)
    override val gAppPtr: CPointer<GApplication>
        get() = gtkAppPtr.reinterpret()

    /**
     * Use a [Application] instance before closing it.
     * @param init Initialization block for the [Application] instance.
     */
    @Suppress("unused")
    fun use(init: Application.() -> Unit) {
        this.init()
        g_object_unref(gtkAppPtr)
        disposeEmptyDataRef()
    }

    private fun createGtkAppPtr(id: String): CPointer<GtkApplication> {
        if (id.trim().isEmpty()) {
            g_printerr("GTK Application ID cannot be empty!\n")
            exitProcess(-1)
        }
        return gtk_application_new(id, G_APPLICATION_FLAGS_NONE)!!
    }
}
