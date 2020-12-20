package org.guiVista.gui.widget.tool.item

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

private const val TOGGLED_SIGNAL = "toggled"

public actual interface ToggleToolButtonBase : ToolButtonBase {
    public val gtkToggleToolButtonPtr: CPointer<GtkToggleToolButton>?

    /** If the toggle tool button should be pressed in. Default value is *false*. */
    public var active: Boolean
        get() = gtk_toggle_tool_button_get_active(gtkToggleToolButtonPtr) == TRUE
        set(value) = gtk_toggle_tool_button_set_active(gtkToggleToolButtonPtr, if (value) TRUE else FALSE)

    /**
     * Connects the *toggled* signal to a [slot] on a toggle tool button. This signal is used when the button changes
     * state.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectToggledSignal(slot: CPointer<ToggledSlot>, userData: gpointer): UInt =
        connectGSignal(obj = gtkToggleToolButtonPtr, signal = TOGGLED_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkToggleToolButtonPtr, handlerId.toUInt())
    }
}

/**
 * The event handler for the *toggled* signal. Arguments:
 * 1. toggleToolButton: CPointer<GtkToggleToolButton>
 * 2. userData: gpointer
 */
public typealias ToggledSlot = CFunction<(button: CPointer<GtkToggleToolButton>?, userData: gpointer) -> Unit>
