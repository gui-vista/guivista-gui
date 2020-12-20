package org.guiVista.gui.widget.menu.item

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

public actual interface CheckMenuItemBase : MenuItemBase {
    public val gtkCheckMenuItemPtr: CPointer<GtkCheckMenuItem>?
        get() = gtkWidgetPtr?.reinterpret()

    /** Whether the menu item is checked. Default value is *false*. */
    public var active: Boolean
        get() = gtk_check_menu_item_get_active(gtkCheckMenuItemPtr) == TRUE
        set(value) = gtk_check_menu_item_set_active(gtkCheckMenuItemPtr, if (value) TRUE else FALSE)

    /** Whether the menu item looks like a radio menu item. Default value is *false*. */
    public var drawAsRadio: Boolean
        get() = gtk_check_menu_item_get_draw_as_radio(gtkCheckMenuItemPtr) == TRUE
        set(value) = gtk_check_menu_item_set_draw_as_radio(gtkCheckMenuItemPtr, if (value) TRUE else FALSE)

    /** Whether to display an "inconsistent" state. Default value is *false*. */
    public var inconsistent: Boolean
        get() = gtk_check_menu_item_get_inconsistent(gtkCheckMenuItemPtr) == TRUE
        set(value) = gtk_check_menu_item_set_inconsistent(gtkCheckMenuItemPtr, if (value) TRUE else FALSE)

    /**
     * Connects the *toggled* signal to a [slot] on a check menu item. This signal is used when that state of the check
     * box is changed.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectToggledSignal(slot: CPointer<ToggledSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkCheckMenuItemPtr, signal = "toggled", slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkCheckMenuItemPtr, handlerId)
    }
}

/**
 * The event handler for the *toggled* signal. Arguments:
 * 1. checkMenuItem: CPointer<GtkCheckMenuItem>
 * 2. userData: gpointer
 */
public typealias ToggledSlot = CFunction<(checkMenuItem: CPointer<GtkCheckMenuItem>, userData: gpointer) -> Unit>
