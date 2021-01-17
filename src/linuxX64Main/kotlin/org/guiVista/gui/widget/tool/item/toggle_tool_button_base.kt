package org.guiVista.gui.widget.tool.item

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

public actual interface ToggleToolButtonBase : ToolButtonBase {
    public val gtkToggleToolButtonPtr: CPointer<GtkToggleToolButton>?

    /** If the toggle tool button should be pressed in. Default value is *false*. */
    public var active: Boolean
        get() = gtk_toggle_tool_button_get_active(gtkToggleToolButtonPtr) == TRUE
        set(value) = gtk_toggle_tool_button_set_active(gtkToggleToolButtonPtr, if (value) TRUE else FALSE)

    /**
     * Connects the *toggled* event to a [handler] on a toggle tool button. This event is used when the button changes
     * state.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectToggledEvent(handler: CPointer<ToggledHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkToggleToolButtonPtr, signal = ToggleToolButtonBaseEvent.toggled, slot = handler,
            data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkToggleToolButtonPtr, handlerId)
    }
}

/**
 * The event handler for the *toggled* event. Arguments:
 * 1. toggleToolButton: CPointer<GtkToggleToolButton>
 * 2. userData: gpointer
 */
public typealias ToggledHandler = CFunction<(button: CPointer<GtkToggleToolButton>?, userData: gpointer) -> Unit>
