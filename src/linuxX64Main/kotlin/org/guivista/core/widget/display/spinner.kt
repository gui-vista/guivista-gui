package org.guivista.core.widget.display

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.widget.Widget

/** Show a spinner animation. */
class Spinner : Widget {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = gtk_spinner_new()
    val gtkSpinnerPtr: CPointer<GtkSpinner>?
        get() = gtkWidgetPtr?.reinterpret()

    /** Starts the animation of the spinner. */
    fun start() {
        gtk_spinner_start(gtkSpinnerPtr)
    }

    /** Stops the animation of the spinner. */
    fun stop() {
        gtk_spinner_stop(gtkSpinnerPtr)
    }
}

fun spinnerWidget(init: Spinner.() -> Unit): Spinner {
    val spinner = Spinner()
    spinner.init()
    return spinner
}
