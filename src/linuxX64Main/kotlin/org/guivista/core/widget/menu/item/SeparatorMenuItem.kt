package org.guivista.core.widget.menu.item

import gtk3.GtkSeparatorMenuItem
import gtk3.GtkWidget
import gtk3.gtk_separator_menu_item_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.widget.menu.item.MenuItemBase

/** A separator used in menus. */
class SeparatorMenuItem : MenuItemBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = gtk_separator_menu_item_new()
    val gtkSeparatorMenuItemPtr: CPointer<GtkSeparatorMenuItem>?
        get() = gtkWidgetPtr?.reinterpret()
}
