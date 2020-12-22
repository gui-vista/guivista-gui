package org.guiVista.gui.widget.menu.item

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal
import org.guiVista.gui.layout.Container
import org.guiVista.gui.widget.menu.Menu

public actual interface MenuItemBase : Container {
    public val gtkMenuItemPtr: CPointer<GtkMenuItem>?
        get() = gtkWidgetPtr?.reinterpret()

    /** Whether the menu item reserves space for the submenu indicator regardless if it has a submenu or not. */
    public var reserveIndicator: Boolean
        get() = gtk_menu_item_get_reserve_indicator(gtkMenuItemPtr) == TRUE
        set(value) = gtk_menu_item_set_reserve_indicator(gtkMenuItemPtr, if (value) TRUE else FALSE)

    /** The text for the child label. Default value is *""* (an empty String). */
    public var label: String
        get() = gtk_menu_item_get_label(gtkMenuItemPtr)?.toKString() ?: ""
        set(value) = gtk_menu_item_set_label(gtkMenuItemPtr, value)

    /** When *true* the text is underlined to indicate mnemonics. Default value is *false*. */
    public var useUnderline: Boolean
        get() = gtk_menu_item_get_use_underline(gtkMenuItemPtr) == TRUE
        set(value) = gtk_menu_item_set_use_underline(gtkMenuItemPtr, if (value) TRUE else FALSE)

    /**
     * Sets the accelerator path of the menu item, through which runtime changes of the menu item's accelerator caused
     * by the user can be identified and saved to persistent storage. Default value is *""* (an empty String).
     */
    public var accelPath: String
        get() = gtk_menu_item_get_accel_path(gtkMenuItemPtr)?.toKString() ?: ""
        set(value) = gtk_menu_item_set_accel_path(gtkMenuItemPtr, value)

    /** Sets whether the menu item appears justified at the right side of a menu bar. Default value is *false*. */
    public var rightJustified: Boolean
        get() = gtk_menu_item_get_right_justified(gtkMenuItemPtr) == TRUE
        set(value) = gtk_menu_item_set_right_justified(gtkMenuItemPtr, if (value) TRUE else FALSE)

    /** Sets or replaces the menu item’s submenu, or removes it when a *null* submenu is passed. */
    public var subMenu: Menu?
        get() {
            val ptr = gtk_menu_item_get_submenu(gtkMenuItemPtr)
            return if (ptr != null) Menu(widgetPtr = ptr) else null
        }
        set(value) = gtk_menu_item_set_submenu(gtkMenuItemPtr, value?.gtkWidgetPtr)

    /**
     * Connects the *activate* signal to a [slot] on a menu item. This signal is used when the item is activated.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectActivateSignal(slot: CPointer<ActivateSlot>, userData: gpointer): UInt =
        connectGSignal(obj = gtkMenuItemPtr, signal = "activate", slot = slot, data = userData)

    /**
     * Connects the *activate-item* signal to a [slot] on a menu item. This signal is used when the item is activated,
     * and if the item has a submenu. For normal applications use the "activate" signal.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectActivateItemSignal(slot: CPointer<ActivateItemSlot>, userData: gpointer): UInt =
        connectGSignal(obj = gtkMenuItemPtr, signal = "activate-item", slot = slot, data = userData)

    /**
     * Connects the *deselect* signal to a [slot] on a menu item. This signal is used when a item is deselected.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectDeselectSignal(slot: CPointer<DeselectSlot>, userData: gpointer): UInt =
        connectGSignal(obj = gtkMenuItemPtr, signal = "deselect", slot = slot, data = userData)

    /**
     * Connects the *select* signal to a [slot] on a menu item. This signal is used when a item is selected.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectSelectSignal(slot: CPointer<SelectSlot>, userData: gpointer): UInt =
        connectGSignal(obj = gtkMenuItemPtr, signal = "select", slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkMenuItemPtr, handlerId.toUInt())
    }
}

/**
 * The event handler for the *activate* signal. Arguments:
 * 1. menuItem: CPointer<GtkMenuItem>
 * 2. userData: gpointer
 */
public typealias ActivateSlot = CFunction<(menuItem: CPointer<GtkMenuItem>, userData: gpointer) -> Unit>

/**
 * The event handler for the *activate-item* signal. Arguments:
 * 1. menuItem: CPointer<GtkMenuItem>
 * 2. userData: gpointer
 */
public typealias ActivateItemSlot = CFunction<(menuItem: CPointer<GtkMenuItem>, userData: gpointer) -> Unit>

/**
 * The event handler for the *deselect* signal. Arguments:
 * 1. menuItem: CPointer<GtkMenuItem>
 * 2. userData: gpointer
 */
public typealias DeselectSlot = CFunction<(menuItem: CPointer<GtkMenuItem>, userData: gpointer) -> Unit>

/**
 * The event handler for the *select* signal. Arguments:
 * 1. menuItem: CPointer<GtkMenuItem>
 * 2. userData: gpointer
 */
public typealias SelectSlot = CFunction<(menuItem: CPointer<GtkMenuItem>, userData: gpointer) -> Unit>
