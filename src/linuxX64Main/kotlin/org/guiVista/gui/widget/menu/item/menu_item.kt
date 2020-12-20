package org.guiVista.gui.widget.menu.item

import gtk3.GtkMenuItem
import gtk3.GtkWidget
import gtk3.gtk_menu_item_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class MenuItem(menuItemPtr: CPointer<GtkMenuItem>? = null) : MenuItemBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = menuItemPtr?.reinterpret() ?: gtk_menu_item_new()
}

public fun menuItem(menuItemPtr: CPointer<GtkMenuItem>? = null, init: MenuItem.() -> Unit): MenuItem {
    val menuItem = MenuItem(menuItemPtr)
    menuItem.init()
    return menuItem
}
