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

public actual interface MenuShell : Container {
    public val gtkMenuShellPtr: CPointer<GtkMenuShell>?
        get() = gtkWidgetPtr?.reinterpret()

    /** Determines whether the menu, and its submenus grab the keyboard focus. Default value is *true*. */
    public var takeFocus: Boolean
        get() = gtk_menu_shell_get_take_focus(gtkMenuShellPtr) == TRUE
        set(value) = gtk_menu_shell_set_take_focus(gtkMenuShellPtr, if (value) TRUE else FALSE)

    /** The currently selected item in the [MenuShell] */
    public val selectedItem: WidgetBase?
        get() {
            val tmp = gtk_menu_shell_get_selected_item(gtkMenuShellPtr)
            return if (tmp != null) Widget(tmp) else null
        }

    /** The parent [MenuShell]. A submenu parent is the GtkMenu or GtkMenuBar from which it was opened up. */
    public val parentShell: WidgetBase?
        get() {
            val tmp = gtk_menu_shell_get_parent_shell(gtkMenuShellPtr)
            return if (tmp != null) Widget(tmp) else null
        }

    /**
     * Adds a new GtkMenuItem to the end of the menu shell's item list.
     * @param child The menu item to append.
     */
    public infix fun append(child: WidgetBase) {
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
    public infix fun prepend(child: WidgetBase) {
        gtk_menu_shell_prepend(gtkMenuShellPtr, child.gtkWidgetPtr)
    }

    /**
     * Adds a new menu item to the menu shell’s item list at the position indicated by position.
     * @param child The menu item to insert.
     * @param position The position in the item list where [child] is added. Positions are numbered from 0 to n-1.
     */
    public fun insert(child: WidgetBase, position: Int) {
        gtk_menu_shell_insert(menu_shell = gtkMenuShellPtr, child = child.gtkWidgetPtr, position = position)
    }

    /** Deactivates the menu shell. Typically this results in the menu shell being erased from the screen. */
    public fun deactivate() {
        gtk_menu_shell_deactivate(gtkMenuShellPtr)
    }

    /**
     * Selects the menu item from the menu shell.
     * @param menuItem The menu item to select.
     */
    public infix fun selectItem(menuItem: WidgetBase) {
        gtk_menu_shell_select_item(gtkMenuShellPtr, menuItem.gtkWidgetPtr)
    }

    /**
     * Select the first visible or selectable child of the menu shell. Don’t select tear off items unless the only item
     * is a tearoff item.
     * @param searchSensitive If *true* search for the first selectable menu item, otherwise select nothing if the
     * first item isn’t sensitive. This should be *false* if the menu is being popped up initially.
     */
    public fun selectFirst(searchSensitive: Boolean = true) {
        gtk_menu_shell_select_first(gtkMenuShellPtr, if (searchSensitive) TRUE else FALSE)
    }

    /** Deselects the currently selected item from the menu shell. */
    public fun deselect() {
        gtk_menu_shell_deselect(gtkMenuShellPtr)
    }

    /**
     * Activates the menu item within the menu shell.
     * @param menuItem The menu item to activate.
     * @param forceDeactivate If *true* force the deactivation of the menu shell after the menu item is activated.
     */
    public fun activateItem(menuItem: WidgetBase, forceDeactivate: Boolean) {
        gtk_menu_shell_activate_item(menu_shell = gtkMenuShellPtr, menu_item = menuItem.gtkWidgetPtr,
            force_deactivate = if (forceDeactivate) TRUE else FALSE)
    }

    /** Cancels the selection within the menu shell. */
    public fun cancel() {
        gtk_menu_shell_cancel(gtkMenuShellPtr)
    }

    /**
     * Connects the *cancel* event to a [handler] on a [MenuShell]. This event is used when selection is
     * cancelled in a [MenuShell].
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectCancelEvent(handler: CPointer<CancelHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkMenuShellPtr, signal = MenuShellEvent.cancel, slot = handler, data = userData)

    /**
     * Connects the *selection-done* event to a [handler] on a [MenuShell]. This event is used when selection is
     * completed within a [MenuShell].
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectSelectionDoneEvent(handler: CPointer<SelectionDoneHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkMenuShellPtr, signal = MenuShellEvent.selectionDone, slot = handler, data = userData)

    /**
     * Connects the *activate-current* event to a [handler] on a [MenuShell]. This event is used when the current item
     * is activated within the [MenuShell].
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectActivateCurrentEvent(handler: CPointer<ActivateCurrentHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkMenuShellPtr, signal = MenuShellEvent.activateCurrent, slot = handler, data = userData)

    /**
     * Connects the *deactivate* event to a [handler] on a [MenuShell]. This event is used when the [MenuShell] is
     * deactivated.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectDeactivateEvent(handler: CPointer<DeactivateHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkMenuShellPtr, signal = MenuShellEvent.deactivate, slot = handler, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkMenuShellPtr, handlerId)
    }
}

/**
 * The event handler for the *cancel* event. Arguments:
 * 1. menuShell: CPointer<GtkMenuShell>
 * 2. userData: gpointer
 */
public typealias CancelHandler = CFunction<(menuShell: CPointer<GtkMenuShell>, userData: gpointer) -> Unit>

/**
 * The event handler for the *selection-done* event. Arguments:
 * 1. menuShell: CPointer<GtkMenuShell>
 * 2. userData: gpointer
 */
public typealias SelectionDoneHandler = CFunction<(menuShell: CPointer<GtkMenuShell>, userData: gpointer) -> Unit>

/**
 * The event handler for the *activate-current* event. Arguments:
 * 1. menuShell: CPointer<GtkMenuShell>
 * 2. forceHide: Boolean
 * 3. userData: gpointer
 */
public typealias ActivateCurrentHandler =
    CFunction<(menuShell: CPointer<GtkMenuShell>, forceHide: Boolean, userData: gpointer) -> Unit>

/**
 * The event handler for the *deactivate* event. Arguments:
 * 1. menuShell: CPointer<GtkMenuShell>
 * 2. userData: gpointer
 */
public typealias DeactivateHandler =
    CFunction<(menuShell: CPointer<GtkMenuShell>, userData: gpointer) -> Unit>
