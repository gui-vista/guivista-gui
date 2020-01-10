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

private const val TOGGLED_SIGNAL = "toggled"

/** Base interface for toggle button objects. */
interface ToggleButtonBase : ButtonBase {
    val gtkToggleButtonPtr: CPointer<GtkToggleButton>?
        get() = gtkWidgetPtr?.reinterpret()
    /** If the toggle button should be pressed in. Default value is *false*. */
    var active: Boolean
        get() = gtk_toggle_button_get_active(gtkToggleButtonPtr) == TRUE
        set(value) = gtk_toggle_button_set_active(gtkToggleButtonPtr, if (value) TRUE else FALSE)
    /** If the toggle button is in an "in between" state. Default value is *false*. */
    var inconsistent: Boolean
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
    var mode: Boolean
        get() = gtk_toggle_button_get_mode(gtkToggleButtonPtr) == TRUE
        set(value) = gtk_toggle_button_set_mode(gtkToggleButtonPtr, if (value) TRUE else FALSE)

    /**
     * Connects the *toggled* signal to a [slot] on a [ToggleButton]. This signal is used when the
     * [toggle button's][ToggleButton] state has changed.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     * @return The handler ID for the [slot].
     */
    fun connectToggledSignal(slot: CPointer<ToggledSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkToggleButtonPtr, signal = TOGGLED_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkToggleButtonPtr, handlerId)
    }
}

/**
 * The event handler for the *toggled* signal. Arguments:
 * 1. toggleButton: CPointer<GtkToggleButton>
 * 2. userData: gpointer
 */
typealias ToggledSlot = CFunction<(toggleButton: CPointer<GtkToggleButton>, userData: gpointer) -> Unit>
