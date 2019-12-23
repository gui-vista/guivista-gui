package org.guivista.core.widget

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guivista.core.connectGObjectSignal
import org.guivista.core.layout.Container

open class Button : Container {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = gtk_button_new()
    val gtkButtonPtr: CPointer<GtkButton>?
        get() = gtkWidgetPtr?.reinterpret()
    /** Text of the label widget inside the button, if the button contains a label widget. */
    var label: String
        set(value) = gtk_button_set_label(gtkButtonPtr, value)
        get() = gtk_button_get_label(gtkButtonPtr)?.toKString() ?: ""

    /**
     * Connects the *clicked* signal to a [slot] on a button. The *clicked* signal is used when a user has clicked on
     * the button.
     * @param slot The event handler for the signal. Arguments:
     * 1. app: CPointer<GtkButton>
     * 2. userData: gpointer
     * @param userData User data to pass through to the [slot].
     */
    fun connectClickedSignal(slot: CPointer<CFunction<(app: CPointer<GtkButton>, userData: gpointer) -> Unit>>,
                             userData: gpointer): ULong =
            connectGObjectSignal(obj = gtkButtonPtr, signal = "clicked", slot = slot, data = userData)
}

fun buttonWidget(init: Button.() -> Unit): Button {
    val btn = Button()
    btn.init()
    return btn
}
