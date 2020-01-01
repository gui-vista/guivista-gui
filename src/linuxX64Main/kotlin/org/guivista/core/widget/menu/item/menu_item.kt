package org.guivista.core.widget.menu.item

import gtk3.GtkMenuItem
import gtk3.GtkWidget
import gtk3.gtk_menu_item_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.widget.menu.item.MenuItemBase

/** The widget used for item in menus. */
class MenuItem(menuItemPtr: CPointer<GtkMenuItem>? = null) : MenuItemBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = menuItemPtr?.reinterpret() ?: gtk_menu_item_new()
}

fun menuItem(menuItemPtr: CPointer<GtkMenuItem>? = null, init: MenuItem.() -> Unit): MenuItem {
    val menuItem = MenuItem(menuItemPtr)
    menuItem.init()
    return menuItem
}
