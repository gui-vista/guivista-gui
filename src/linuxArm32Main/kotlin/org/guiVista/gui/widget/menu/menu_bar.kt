package org.guiVista.gui.widget.menu

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

actual class MenuBar(menuBarPtr: CPointer<GtkMenuBar>? = null) : MenuShell {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = menuBarPtr?.reinterpret() ?: gtk_menu_bar_new()
    val gtkMenuBarPtr: CPointer<GtkMenuBar>?
        get() = gtkWidgetPtr?.reinterpret()

    /**
     * The child pack direction of the [MenuBar]. It determines how the widgets contained in child menu items are
     * arranged. Default value is *GtkPackDirection.GTK_PACK_DIRECTION_LTR*.
     */
    var childPackDirection: GtkPackDirection
        get() = gtk_menu_bar_get_child_pack_direction(gtkMenuBarPtr)
        set(value) = gtk_menu_bar_set_child_pack_direction(gtkMenuBarPtr, value)

    /**
     * The pack direction of the menubar. It determines how menuitems are arranged in the menubar. Default value is
     * *GtkPackDirection.GTK_PACK_DIRECTION_LTR*.
     */
    var packDirection: GtkPackDirection
        get() = gtk_menu_bar_get_pack_direction(gtkMenuBarPtr)
        set(value) = gtk_menu_bar_set_pack_direction(gtkMenuBarPtr, value)
}

fun menuBarWidget(menuBarPtr: CPointer<GtkMenuBar>? = null, init: MenuBar.() -> Unit): MenuBar {
    val menuBar = MenuBar(menuBarPtr)
    menuBar.init()
    return menuBar
}
