package org.guivista.core.widget.button

import gtk3.GtkButton
import gtk3.gpointer
import gtk3.gtk_button_get_label
import gtk3.gtk_button_set_label
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guivista.core.connectGObjectSignal
import org.guivista.core.layout.Container

interface ButtonBase : Container {
    val gtkButtonPtr: CPointer<GtkButton>?
        get() = gtkWidgetPtr?.reinterpret()
    /** Text of the label widget inside the button, if the button contains a label widget. */
    var label: String
        set(value) = gtk_button_set_label(gtkButtonPtr, value)
        get() = gtk_button_get_label(gtkButtonPtr)?.toKString() ?: ""

    /**
     * Connects the *clicked* signal to a [slot] on a button. This signal is used when a user has clicked on the button.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectClickedSignal(slot: CPointer<ClickedSlot>, userData: gpointer): ULong =
        connectGObjectSignal(obj = gtkButtonPtr, signal = "clicked", slot = slot, data = userData)
}

/**
 * The event handler for the *clicked* signal. Arguments:
 * 1. button: CPointer<GtkButton>
 * 2. userData: gpointer
 */
typealias ClickedSlot = CFunction<(button: CPointer<GtkButton>?, userData: gpointer) -> Unit>