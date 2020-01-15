package org.guiVista.gui.widget.menu

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal
import org.guiVista.gui.layout.Container
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase

/** Base interface for menu objects. */
interface MenuShell : Container {
    val gtkMenuShellPtr: CPointer<GtkMenuShell>?
        get() = gtkWidgetPtr?.reinterpret()
    /** Determines whether the menu, and its submenus grab the keyboard focus. Default value is *true*. */
    var takeFocus: Boolean
        get() = gtk_menu_shell_get_take_focus(gtkMenuShellPtr) == TRUE
        set(value) = gtk_menu_shell_set_take_focus(gtkMenuShellPtr, if (value) TRUE else FALSE)
    /** The currently selected item in the [MenuShell] */
    val selectedItem: WidgetBase?
        get() {
            val tmp = gtk_menu_shell_get_selected_item(gtkMenuShellPtr)
            return if (tmp != null) Widget(tmp) else null
        }
    /** The parent [MenuShell]. A submenu parent is the GtkMenu or GtkMenuBar from which it was opened up. */
    val parentShell: WidgetBase?
        get() {
            val tmp = gtk_menu_shell_get_parent_shell(gtkMenuShellPtr)
            return if (tmp != null) Widget(tmp) else null
        }

    /**
     * Adds a new GtkMenuItem to the end of the menu shell's item list.
     * @param child The menu item to append.
     */
    infix fun append(child: WidgetBase) {
        gtk_menu_shell_append(gtkMenuShellPtr, child.gtkWidgetPtr)
    }

    /**
     * Adds a new GtkMenuItem to the end of the menu shell's item list.
     * @param widget The menu item to append.
     */
    override operator fun plusAssign(widget: WidgetBase) {
        append(widget)
    }

    /**
     * Adds a new menu item to the beginning of the menu shell's item list.
     * @param child The menu item to prepend.
     */
    infix fun prepend(child: WidgetBase) {
        gtk_menu_shell_prepend(gtkMenuShellPtr, child.gtkWidgetPtr)
    }

    /**
     * Adds a new menu item to the menu shell’s item list at the position indicated by position.
     * @param child The menu item to insert.
     * @param position The position in the item list where [child] is added. Positions are numbered from 0 to n-1.
     */
    fun insert(child: WidgetBase, position: Int) {
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
    infix fun selectItem(menuItem: WidgetBase) {
        gtk_menu_shell_select_item(gtkMenuShellPtr, menuItem.gtkWidgetPtr)
    }

    /**
     * Select the first visible or selectable child of the menu shell. Don’t select tear off items unless the only item
     * is a tearoff item.
     * @param searchSensitive If *true* search for the first selectable menu item, otherwise select nothing if the
     * first item isn’t sensitive. This should be *false* if the menu is being popped up initially.
     */
    fun selectFirst(searchSensitive: Boolean = true) {
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
    fun activateItem(menuItem: WidgetBase, forceDeactivate: Boolean) {
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
        connectGSignal(obj = gtkMenuShellPtr, signal = "cancel", slot = slot, data = userData)

    /**
     * Connects the *selection-done* signal to a [slot] on a [MenuShell]. This signal is used when selection is
     * completed within a [MenuShell].
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectSelectionDoneSignal(slot: CPointer<SelectionDoneSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkMenuShellPtr, signal = "selection-done", slot = slot, data = userData)

    /**
     * Connects the *activate-current* signal to a [slot] on a [MenuShell]. This signal is used when the current item
     * is activated within the [MenuShell].
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectActivateCurrentSignal(slot: CPointer<ActivateCurrentSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkMenuShellPtr, signal = "activate-current", slot = slot, data = userData)

    /**
     * Connects the *deactivate* signal to a [slot] on a [MenuShell]. This signal is used when the [MenuShell] is
     * deactivated.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectDeactivateSignal(slot: CPointer<DeactivateSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkMenuShellPtr, signal = "deactivate", slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkMenuShellPtr, handlerId)
    }
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
