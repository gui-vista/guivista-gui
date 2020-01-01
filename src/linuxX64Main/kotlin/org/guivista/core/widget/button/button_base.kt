package org.guivista.core.widget.button

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guivista.core.connectGSignal
import org.guivista.core.layout.Container

private const val CLICKED_SIGNAL = "clicked"

/** Base interface for button objects. */
interface ButtonBase : Container {
    val gtkButtonPtr: CPointer<GtkButton>?
        get() = gtkWidgetPtr?.reinterpret()
    /** Text of the label widget inside the button, if the button contains a label widget. */
    var label: String
        set(value) = gtk_button_set_label(gtkButtonPtr, value)
        get() = gtk_button_get_label(gtkButtonPtr)?.toKString() ?: ""
    /** The border relief style. */
    var relief: GtkReliefStyle
        get() = gtk_button_get_relief(gtkButtonPtr)
        set(value) = gtk_button_set_relief(gtkButtonPtr, value)

    /**
     * Connects the *clicked* signal to a [slot] on a button. This signal is used when a user has clicked on the button.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectClickedSignal(slot: CPointer<ClickedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkButtonPtr, signal = CLICKED_SIGNAL, slot = slot, data = userData)
}

/**
 * The event handler for the *clicked* signal. Arguments:
 * 1. button: CPointer<GtkButton>
 * 2. userData: gpointer
 */
typealias ClickedSlot = CFunction<(button: CPointer<GtkButton>?, userData: gpointer) -> Unit>
