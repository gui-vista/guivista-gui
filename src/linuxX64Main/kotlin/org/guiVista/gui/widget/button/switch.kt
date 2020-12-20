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
import org.guiVista.gui.widget.WidgetBase

private const val STATE_SET_SIGNAL = "state-set"

public actual class Switch(switchPtr: CPointer<GtkSwitch>? = null) : WidgetBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = switchPtr?.reinterpret() ?: gtk_switch_new()
    public val gtkSwitchPtr: CPointer<GtkSwitch>?
        get() = gtkWidgetPtr?.reinterpret()
    public actual var active: Boolean
        get() = gtk_switch_get_active(gtkSwitchPtr) == TRUE
        set(value) = gtk_switch_set_active(gtkSwitchPtr, if (value) TRUE else FALSE)
    public actual var state: Boolean
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
    public fun connectStateSetSignal(slot: CPointer<StateSetSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkSwitchPtr, signal = STATE_SET_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkSwitchPtr, handlerId)
    }
}

public fun switchWidget(switchPtr: CPointer<GtkSwitch>? = null, init: Switch.() -> Unit): Switch {
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
public typealias StateSetSlot = CFunction<(switch: CPointer<GtkSwitch>, state: Boolean, userData: gpointer) -> Unit>
