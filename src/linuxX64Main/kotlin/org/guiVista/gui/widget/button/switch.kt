package org.guiVista.gui.widget.button

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.connectGSignal
import org.guiVista.gui.disconnectGSignal
import org.guiVista.gui.widget.WidgetBase

private const val STATE_SET_SIGNAL = "state-set"

/** A “light switch” style toggle. */
class Switch(switchPtr: CPointer<GtkSwitch>? = null) : WidgetBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = switchPtr?.reinterpret() ?: gtk_switch_new()
    val gtkSwitchPtr: CPointer<GtkSwitch>?
        get() = gtkWidgetPtr?.reinterpret()
    /** Whether the GtkSwitch widget is in its on or off state. Default value is *false*. */
    var active: Boolean
        get() = gtk_switch_get_active(gtkSwitchPtr) == TRUE
        set(value) = gtk_switch_set_active(gtkSwitchPtr, if (value) TRUE else FALSE)
    /**
     * The backend state that is controlled by the switch. Normally this is the same as “active”, unless the switch is
     * set up for delayed state changes. This function is typically called from a “state-set” signal handler. Default
     * value is *false*.
     */
    var state: Boolean
        get() = gtk_switch_get_state(gtkSwitchPtr) == TRUE
        set(value) = gtk_switch_set_state(gtkSwitchPtr, if (value) TRUE else FALSE)

    /**
     * Connects the *state-set* signal to a [slot] on a [Switch]. This signal is used when the user changes the switch
     * position. The default handler keeps the state in sync with the [active property][active]. To implement delayed
     * state change applications can connect to this signal, initiate the change of the underlying state, and call
     * `gtk_switch_set_state()` when the underlying state change is complete. The signal handler should return *true*
     * to prevent the default handler from running.
     *
     * Visually, the underlying state is represented by the true color of the switch, while the
     * [active property][active] is represented by the position of the [Switch].
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectStateSetSignal(slot: CPointer<StateSetSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkSwitchPtr, signal = STATE_SET_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkSwitchPtr, handlerId)
    }
}

fun switchWidget(switchPtr: CPointer<GtkSwitch>? = null, init: Switch.() -> Unit): Switch {
    val switch = Switch(switchPtr)
    switch.init()
    return switch
}

/**
 * The event handler for the *state-set* signal. Arguments:
 * 1. switch: CPointer<GtkSwitch>
 * 2. state: Boolean
 * 3. userData: gpointer
 */
typealias StateSetSlot = CFunction<(switch: CPointer<GtkSwitch>, state: Boolean, userData: gpointer) -> Unit>
