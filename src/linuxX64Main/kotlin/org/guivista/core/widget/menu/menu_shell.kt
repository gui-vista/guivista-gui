package org.guivista.core.widget.menu

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.connectGObjectSignal
import org.guivista.core.layout.Container
import org.guivista.core.widget.Widget

/** Base interface for menu objects. */
interface MenuShell : Container {
    val gtkMenuShellPtr: CPointer<GtkMenuShell>?
        get() = gtkWidgetPtr?.reinterpret()
    /**
     * Determines whether the menu, and its submenus grab the keyboard focus. See `gtk_menu_shell_set_take_focus()`,
     * and `gtk_menu_shell_get_take_focus().`
     */
    var takeFocus: Boolean
        get() = gtk_menu_shell_get_take_focus(gtkMenuShellPtr) == TRUE
        set(value) = gtk_menu_shell_set_take_focus(gtkMenuShellPtr, if (value) TRUE else FALSE)
    /** The currently selected item in the [MenuShell] */
    val selectedItem: CPointer<GtkWidget>?
        get() = gtk_menu_shell_get_selected_item(gtkMenuShellPtr)
    /** The parent [MenuShell]. A submenu parent is the GtkMenu or GtkMenuBar from which it was opened up. */
    val parentShell: CPointer<GtkWidget>?
        get() = gtk_menu_shell_get_parent_shell(gtkMenuShellPtr)

    /**
     * Adds a new GtkMenuItem to the end of the menu shell's item list.
     * @param child The menu item to append.
     */
    fun append(child: Widget) {
        gtk_menu_shell_append(gtkMenuShellPtr, child.gtkWidgetPtr)
    }

    /**
     * Adds a new menu item to the beginning of the menu shell's item list.
     * @param child The menu item to prepend.
     */
    fun prepend(child: Widget) {
        gtk_menu_shell_prepend(gtkMenuShellPtr, child.gtkWidgetPtr)
    }

    /**
     * Adds a new menu item to the menu shell’s item list at the position indicated by position.
     * @param child The menu item to insert.
     * @param position The position in the item list where [child] is added. Positions are numbered from 0 to n-1.
     */
    fun insert(child: Widget, position: Int) {
        gtk_menu_shell_insert(menu_shell = gtkMenuShellPtr, child = child.gtkWidgetPtr, position = position)
    }

    /** Deactivates the menu shell. Typically this results in the menu shell being erased from the screen. */
    fun deactivate() {
        gtk_menu_shell_deactivate(gtkMenuShellPtr)
    }

    /**
     * Selects the menu item from the menu shell.
     * @param menuItem The menu item to select.
     */
    fun selectItem(menuItem: Widget) {
        gtk_menu_shell_select_item(gtkMenuShellPtr, menuItem.gtkWidgetPtr)
    }

    /**
     * Select the first visible or selectable child of the menu shell. Don’t select tear off items unless the only item
     * is a tearoff item.
     * @param searchSensitive If *true* search for the first selectable menu item, otherwise select nothing if the
     * first item isn’t sensitive. This should be *false* if the menu is being popped up initially.
     */
    fun selectFirst(searchSensitive: Boolean) {
        gtk_menu_shell_select_first(gtkMenuShellPtr, if (searchSensitive) TRUE else FALSE)
    }

    /** Deselects the currently selected item from the menu shell. */
    fun deselect() {
        gtk_menu_shell_deselect(gtkMenuShellPtr)
    }

    /**
     * Activates the menu item within the menu shell.
     * @param menuItem The menu item to activate.
     * @param forceDeactivate If *true* force the deactivation of the menu shell after the menu item is activated.
     */
    fun activateItem(menuItem: Widget, forceDeactivate: Boolean) {
        gtk_menu_shell_activate_item(menu_shell = gtkMenuShellPtr, menu_item = menuItem.gtkWidgetPtr,
            force_deactivate = if (forceDeactivate) TRUE else FALSE)
    }

    /** Cancels the selection within the menu shell. */
    fun cancel() {
        gtk_menu_shell_cancel(gtkMenuShellPtr)
    }

    /**
     * Connects the *cancel* signal to a [slot] on a [MenuShell]. This signal is used when selection is
     * cancelled in a [MenuShell].
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectCancelSignal(slot: CPointer<CancelSlot>, userData: gpointer): ULong =
        connectGObjectSignal(obj = gtkMenuShellPtr, signal = "cancel", slot = slot, data = userData)

    /**
     * Connects the *selection-done* signal to a [slot] on a [MenuShell]. This signal is used when selection is
     * completed within a [MenuShell].
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectSelectionDoneSignal(slot: CPointer<SelectionDoneSlot>, userData: gpointer): ULong =
        connectGObjectSignal(obj = gtkMenuShellPtr, signal = "selection-done", slot = slot, data = userData)

    /**
     * Connects the *activate-current* signal to a [slot] on a [MenuShell]. This signal is used when the current item
     * is activated within the [MenuShell].
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectActivateCurrentSignal(slot: CPointer<ActivateCurrentSlot>, userData: gpointer): ULong =
        connectGObjectSignal(obj = gtkMenuShellPtr, signal = "activate-current", slot = slot, data = userData)

    /**
     * Connects the *deactivate* signal to a [slot] on a [MenuShell]. This signal is used when the [MenuShell] is
     * deactivated.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectDeactivateSignal(slot: CPointer<DeactivateSlot>, userData: gpointer): ULong =
        connectGObjectSignal(obj = gtkMenuShellPtr, signal = "deactivate", slot = slot, data = userData)
}

/**
 * The event handler for the *cancel* signal. Arguments:
 * 1. menuShell: CPointer<GtkMenuShell>
 * 2. userData: gpointer
 */
typealias CancelSlot = CFunction<(menuShell: CPointer<GtkMenuShell>, userData: gpointer) -> Unit>

/**
 * The event handler for the *selection-done* signal. Arguments:
 * 1. menuShell: CPointer<GtkMenuShell>
 * 2. userData: gpointer
 */
typealias SelectionDoneSlot = CFunction<(menuShell: CPointer<GtkMenuShell>, userData: gpointer) -> Unit>

/**
 * The event handler for the *activate-current* signal. Arguments:
 * 1. menuShell: CPointer<GtkMenuShell>
 * 2. forceHide: Boolean
 * 3. userData: gpointer
 */
typealias ActivateCurrentSlot =
    CFunction<(menuShell: CPointer<GtkMenuShell>, forceHide: Boolean, userData: gpointer) -> Unit>

/**
 * The event handler for the *deactivate* signal. Arguments:
 * 1. menuShell: CPointer<GtkMenuShell>
 * 2. userData: gpointer
 */
typealias DeactivateSlot =
    CFunction<(menuShell: CPointer<GtkMenuShell>, userData: gpointer) -> Unit>
