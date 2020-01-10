package org.guiVista.core.widget.menu.item

import gtk3.GtkSeparatorMenuItem
import gtk3.GtkWidget
import gtk3.gtk_separator_menu_item_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** A separator used in menus. */
class SeparatorMenuItem(separatorMenuItemPtr: CPointer<GtkSeparatorMenuItem>? = null) : MenuItemBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        separatorMenuItemPtr?.reinterpret() ?: gtk_separator_menu_item_new()
    val gtkSeparatorMenuItemPtr: CPointer<GtkSeparatorMenuItem>?
        get() = gtkWidgetPtr?.reinterpret()
}

fun separatorMenuItem(separatorMenuItemPtr: CPointer<GtkSeparatorMenuItem>? = null,
                      init: SeparatorMenuItem.() -> Unit): SeparatorMenuItem {
    val menuItem = SeparatorMenuItem(separatorMenuItemPtr)
    menuItem.init()
    return menuItem
}
