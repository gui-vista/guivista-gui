package org.guivista.core

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlin.system.exitProcess

actual class Application actual constructor(val id: String) : ApplicationBase {
    val gtkAppPtr: CPointer<GtkApplication> = createGtkAppPtr()
    override val gAppPtr: CPointer<GApplication>
        get() = gtkAppPtr.reinterpret()

    fun use(init: Application.() -> Unit) {
        this.init()
        g_object_unref(gtkAppPtr)
    }

    private fun createGtkAppPtr(): CPointer<GtkApplication> {
        if (id.trim().isEmpty()) {
            g_printerr("GTK Application ID cannot be empty!\n")
            exitProcess(-1)
        }
        return gtk_application_new(id, G_APPLICATION_FLAGS_NONE)!!
    }
}
