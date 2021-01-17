package org.guiVista.gui.widget.button

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

public actual interface ToggleButtonBase : ButtonBase {
    public val gtkToggleButtonPtr: CPointer<GtkToggleButton>?
        get() = gtkWidgetPtr?.reinterpret()

    /** If the toggle button should be pressed in. Default value is *false*. */
    public var active: Boolean
        get() = gtk_toggle_button_get_active(gtkToggleButtonPtr) == TRUE
        set(value) = gtk_toggle_button_set_active(gtkToggleButtonPtr, if (value) TRUE else FALSE)

    /** If the toggle button is in an "in between" state. Default value is *false*. */
    public var inconsistent: Boolean
        get() = gtk_toggle_button_get_inconsistent(gtkToggleButtonPtr) == TRUE
        set(value) = gtk_toggle_button_set_inconsistent(gtkToggleButtonPtr, if (value) TRUE else FALSE)

    /**
     * Whether the button is displayed as a separate indicator and label. You can call this function on a check button
     * or a radiobutton with `drawIndicator = false` to make the button look like a normal button. This can be used to
     * create linked strip of buttons that work like a GtkStackSwitcher.
     *
     * This function only affects instances of classes like GtkCheckButton and GtkRadioButton that derive from
     * GtkToggleButton, not instances of GtkToggleButton itself.
     */
    public var mode: Boolean
        get() = gtk_toggle_button_get_mode(gtkToggleButtonPtr) == TRUE
        set(value) = gtk_toggle_button_set_mode(gtkToggleButtonPtr, if (value) TRUE else FALSE)

    /**
     * Connects the *toggled* event to a [handler] on a [ToggleButton]. This event is used when the
     * [toggle button's][ToggleButton] state has changed.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     * @return The handler ID for the [handler].
     */
    public fun connectToggledEvent(handler: CPointer<ToggledHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkToggleButtonPtr, signal = ToggleButtonBaseEvent.toggled, slot = handler,
            data = userData).toULong()

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkToggleButtonPtr, handlerId.toUInt())
    }
}

/**
 * The event handler for the *toggled* event. Arguments:
 * 1. toggleButton: CPointer<GtkToggleButton>
 * 2. userData: gpointer
 */
public typealias ToggledHandler = CFunction<(toggleButton: CPointer<GtkToggleButton>, userData: gpointer) -> Unit>
