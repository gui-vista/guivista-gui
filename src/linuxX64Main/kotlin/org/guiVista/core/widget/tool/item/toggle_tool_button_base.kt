package org.guiVista.core.widget.tool.item

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

private const val TOGGLED_SIGNAL = "toggled"

/** Base interface for toggle tool button objects. */
interface ToggleToolButtonBase : ToolButtonBase {
    val gtkToggleToolButtonPtr: CPointer<GtkToggleToolButton>?
    /** If the toggle tool button should be pressed in. Default value is *false*. */
    var active: Boolean
        get() = gtk_toggle_tool_button_get_active(gtkToggleToolButtonPtr) == TRUE
        set(value) = gtk_toggle_tool_button_set_active(gtkToggleToolButtonPtr, if (value) TRUE else FALSE)

    /**
     * Connects the *toggled* signal to a [slot] on a toggle tool button. This signal is used when the button changes
     * state.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectToggledSignal(slot: CPointer<ToggledSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkToggleToolButtonPtr, signal = TOGGLED_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkToggleToolButtonPtr, handlerId)
    }
}

/**
 * The event handler for the *toggled* signal. Arguments:
 * 1. toggleToolButton: CPointer<GtkToggleToolButton>
 * 2. userData: gpointer
 */
typealias ToggledSlot = CFunction<(button: CPointer<GtkToggleToolButton>?, userData: gpointer) -> Unit>
