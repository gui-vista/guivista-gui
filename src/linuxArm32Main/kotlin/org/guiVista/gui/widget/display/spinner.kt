package org.guiVista.gui.widget.display

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.widget.WidgetBase

actual class Spinner(spinnerPtr: CPointer<GtkSpinner>? = null) : WidgetBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = spinnerPtr?.reinterpret() ?: gtk_spinner_new()
    val gtkSpinnerPtr: CPointer<GtkSpinner>?
        get() = gtkWidgetPtr?.reinterpret()

    actual fun start() {
        gtk_spinner_start(gtkSpinnerPtr)
    }

    actual fun stop() {
        gtk_spinner_stop(gtkSpinnerPtr)
    }
}

fun spinnerWidget(spinnerPtr: CPointer<GtkSpinner>? = null, init: Spinner.() -> Unit): Spinner {
    val spinner = Spinner(spinnerPtr)
    spinner.init()
    return spinner
}
