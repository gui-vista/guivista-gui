package org.guiVista.core.widget.menu.item

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guiVista.core.connectGSignal
import org.guiVista.core.layout.Container
import org.guiVista.core.widget.WidgetBase

/** Base interface for menu item objects. */
interface MenuItemBase : Container {
    val gtkMenuItemPtr: CPointer<GtkMenuItem>?
        get() = gtkWidgetPtr?.reinterpret()
    /** The text for the child label. Default value is *""* (an empty String). */
    var label: String
        get() = gtk_menu_item_get_label(gtkMenuItemPtr)?.toKString() ?: ""
        set(value) = gtk_menu_item_set_label(gtkMenuItemPtr, value)
    /** When *true* the text is underlined to indicate mnemonics. Default value is *false*. */
    var useUnderline: Boolean
        get() = gtk_menu_item_get_use_underline(gtkMenuItemPtr) == TRUE
        set(value) = gtk_menu_item_set_use_underline(gtkMenuItemPtr, if (value) TRUE else FALSE)
    /**
     * Sets the accelerator path of the menu item, through which runtime changes of the menu item's accelerator caused
     * by the user can be identified and saved to persistent storage. Default value is *""* (an empty String).
     */
    var accelPath: String
        get() = gtk_menu_item_get_accel_path(gtkMenuItemPtr)?.toKString() ?: ""
        set(value) = gtk_menu_item_set_accel_path(gtkMenuItemPtr, value)

    /**
     * Sets or replaces the menu item’s submenu, or removes it when a *null* submenu is passed.
     * @param submenu The submenu to use or *null*.
     */
    fun changeSubmenu(submenu: WidgetBase?) {
        gtk_menu_item_set_submenu(gtkMenuItemPtr, submenu?.gtkWidgetPtr)
    }

    /**
     * Gets the submenu underneath this menu item, if any.
     * @return Submenu for this menu item, or *null* if none.
     * @see gtk_menu_item_set_submenu
     */
    fun fetchSubmenu(): CPointer<GtkWidget>? = gtk_menu_item_get_submenu(gtkMenuItemPtr)

    /**
     * Connects the *activate* signal to a [slot] on a menu item. This signal is used when the item is activated.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectActivateSignal(slot: CPointer<ActivateSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkMenuItemPtr, signal = "activate", slot = slot, data = userData)

    /**
     * Connects the *activate-item* signal to a [slot] on a menu item. This signal is used when the item is activated,
     * and if the item has a submenu. For normal applications use the "activate" signal.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectActivateItemSignal(slot: CPointer<ActivateItemSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkMenuItemPtr, signal = "activate-item", slot = slot, data = userData)

    /**
     * Connects the *deselect* signal to a [slot] on a menu item. This signal is used when a item is deselected.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectDeselectSignal(slot: CPointer<DeselectSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkMenuItemPtr, signal = "deselect", slot = slot, data = userData)

    /**
     * Connects the *select* signal to a [slot] on a menu item. This signal is used when a item is selected.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectSelectSignal(slot: CPointer<SelectSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkMenuItemPtr, signal = "select", slot = slot, data = userData)
}

/**
 * The event handler for the *activate* signal. Arguments:
 * 1. menuItem: CPointer<GtkMenuItem>
 * 2. userData: gpointer
 */
typealias ActivateSlot = CFunction<(menuItem: CPointer<GtkMenuItem>, userData: gpointer) -> Unit>

/**
 * The event handler for the *activate-item* signal. Arguments:
 * 1. menuItem: CPointer<GtkMenuItem>
 * 2. userData: gpointer
 */
typealias ActivateItemSlot = CFunction<(infoBar: CPointer<GtkMenuItem>, userData: gpointer) -> Unit>

/**
 * The event handler for the *deselect* signal. Arguments:
 * 1. menuItem: CPointer<GtkMenuItem>
 * 2. userData: gpointer
 */
typealias DeselectSlot = CFunction<(menuItem: CPointer<GtkMenuItem>, userData: gpointer) -> Unit>

/**
 * The event handler for the *select* signal. Arguments:
 * 1. menuItem: CPointer<GtkMenuItem>
 * 2. userData: gpointer
 */
typealias SelectSlot = CFunction<(menuItem: CPointer<GtkMenuItem>, userData: gpointer) -> Unit>
