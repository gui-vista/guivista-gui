package org.guivista.core.widget.menu

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** A subclass of GtkMenuShell which holds GtkMenuItem widgets */
class MenuBar : MenuShell {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = gtk_menu_bar_new()
    val gtkMenuBarPtr: CPointer<GtkMenuBar>?
        get() = gtkWidgetPtr?.reinterpret()
    /**
     * The child pack direction of the [MenuBar]. It determines how the widgets contained in child menu items are
     * arranged.
     */
    var childPackDirection: GtkPackDirection
        get() = gtk_menu_bar_get_child_pack_direction(gtkMenuBarPtr)
        set(value) = gtk_menu_bar_set_child_pack_direction(gtkMenuBarPtr, value)
    /** The pack direction of the menubar. It determines how menuitems are arranged in the menubar. */
    var packDirection: GtkPackDirection
        get() = gtk_menu_bar_get_pack_direction(gtkMenuBarPtr)
        set(value) = gtk_menu_bar_set_pack_direction(gtkMenuBarPtr, value)
}
