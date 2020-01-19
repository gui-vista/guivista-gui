package org.guiVista.gui

import gio2.GApplication
import gio2.G_APPLICATION_FLAGS_NONE
import glib2.g_object_unref
import glib2.g_printerr
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.disposeEmptyDataRef
import org.guiVista.io.application.ApplicationBase
import kotlin.system.exitProcess

/** Handles a GTK application including application lifecycle, and session management. */
class GuiApplication(appPtr: CPointer<GApplication>? = null, id: String = "org.example.gui-app") : ApplicationBase {
    val gtkApplicationPtr: CPointer<GtkApplication>? = appPtr?.reinterpret() ?: createGtkApplicationPtr(id)
    override val gApplicationPtr: CPointer<GApplication>?
        get() = gtkApplicationPtr?.reinterpret()

    /**
     * Use a [GuiApplication] instance before closing it.
     * @param init Initialization block for the [GuiApplication] instance.
     */
    @Suppress("unused")
    fun use(init: GuiApplication.() -> Unit) {
        this.init()
        close()
    }

    private fun createGtkApplicationPtr(id: String): CPointer<GtkApplication>? {
        if (id.trim().isEmpty()) {
            g_printerr("GTK Application ID cannot be empty!\n")
            exitProcess(-1)
        }
        return gtk_application_new(id, G_APPLICATION_FLAGS_NONE)
    }

    override fun close() {
        g_object_unref(gtkApplicationPtr)
        disposeEmptyDataRef()
    }
}
