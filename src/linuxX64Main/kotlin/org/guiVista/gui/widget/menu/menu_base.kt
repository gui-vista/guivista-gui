package org.guiVista.gui.widget.menu

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
import org.guiVista.gui.keyboard.AcceleratorGroup
import org.guiVista.gui.layout.Container
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase

public actual interface MenuBase : Container {
    public val gtkMenuPtr: CPointer<GtkMenu>?
        get() = gtkWidgetPtr?.reinterpret()

    /** The monitor the menu will be popped up on. Default value is *-1*. */
    public var monitor: Int
        set(value) = gtk_menu_set_monitor(gtkMenuPtr, value)
        get() = gtk_menu_get_monitor(gtkMenuPtr)

    /**
     * A Boolean that indicates whether the menu reserves space for toggles and icons, regardless of their actual
     * presence. This property should only be changed from its default value for special-purposes such as tabular
     * menus. Regular menus that are connected to a menu bar, or context menus should reserve toggle space for
     * consistency.
     */
    public var reserveToggleSize: Boolean
        get() = gtk_menu_get_reserve_toggle_size(gtkMenuPtr) == TRUE
        set(value) = gtk_menu_set_reserve_toggle_size(gtkMenuPtr, if (value) TRUE else FALSE)

    /**
     * An accel path used to conveniently construct accel paths of child items. Default value is *""* (an empty
     * String).
     */
    public var accelPath: String
        get() = gtk_menu_get_accel_path(gtkMenuPtr)?.toKString() ?: ""
        set(value) = gtk_menu_set_accel_path(gtkMenuPtr, value)

    /** The accelerator group that holds global accelerators for this menu. */
    public var accelGroup: AcceleratorGroup
        get() = AcceleratorGroup(gtk_menu_get_accel_group(gtkMenuPtr))
        set(value) = gtk_menu_set_accel_group(gtkMenuPtr, value.gtkAcceleratorGroupPtr)

    /**
     * Moves child to a new [position][pos] in the list of menu children.
     * @param child The menu item to move.
     * @param pos The new position to place the [child]. Positions are numbered from 0 to n - 1.
     */
    public fun reorderChild(child: WidgetBase, pos: Int) {
        gtk_menu_reorder_child(menu = gtkMenuPtr, child = child.gtkWidgetPtr, position = pos)
    }

    /**
     * Adds a new menu item to a (table) menu. The number of **cells** that an item will occupy is specified by
     * [leftAttach], [rightAttach], [topAttach], and [bottomAttach]. Each of these represent the leftmost, rightmost,
     * uppermost, and lower column and row numbers of the table. (Columns and rows are indexed from zero).
     *
     * Note that this function isn't related to [detach].
     * @param child The menu item to attach.
     * @param leftAttach The column number to attach the left side of the item to.
     * @param rightAttach The column number to attach the right side of the item to.
     * @param topAttach The row number to attach the top of the item to.
     * @param bottomAttach The row number to attach the bottom of the item to.
     */
    public fun attach(child: WidgetBase, leftAttach: UInt, rightAttach: UInt, topAttach: UInt, bottomAttach: UInt) {
        gtk_menu_attach(
            menu = gtkMenuPtr,
            child = child.gtkWidgetPtr,
            left_attach = leftAttach,
            right_attach = rightAttach,
            top_attach = topAttach,
            bottom_attach = bottomAttach
        )
    }

    /**
     * Gets the selected menu item from the menu. This is used by the GtkComboBox.
     * @return The menu item that was last selected in the menu. If a selection has not yet been made then the first
     * menu item is selected.
     */
    public fun fetchActive(): WidgetBase? {
        val ptr = gtk_menu_get_active(gtkMenuPtr)
        return if (ptr != null) Widget(ptr) else null
    }

    /**
     * Selects the specified menu item within the menu. This is used by the GtkComboBox and should not be used by
     * anyone else.
     * @param index The index of the menu item to select. Index values are from 0 to n-1.
     */
    public fun changeActive(index: UInt) {
        gtk_menu_set_active(gtkMenuPtr, index)
    }

    /** Gets the GtkWidget that the menu is attached to. */
    public fun fetchAttachWidget(): WidgetBase? {
        val ptr = gtk_menu_get_attach_widget(gtkMenuPtr)
        return if (ptr != null) Widget(ptr) else null
    }

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkMenuPtr, handlerId)
    }

    /** Removes the menu from the screen. */
    public fun popDown() {
        gtk_menu_popdown(gtkMenuPtr)
    }

    /** Repositions the menu according to its position function. */
    public fun reposition() {
        gtk_menu_reposition(gtkMenuPtr)
    }

    /**
     * Detaches the menu from the widget to which it had been attached. This function will call the callback function
     * *detacher*, which is provided when the `gtk_menu_attach_to_widget` function was called.
     */
    public fun detach() {
        gtk_menu_detach(gtkMenuPtr)
    }
}
