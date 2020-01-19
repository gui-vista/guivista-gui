package org.guiVista.gui.widget.menu

import gtk3.GtkMenu
import gtk3.GtkWidget
import gtk3.gtk_menu_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** A menu widget. */
class Menu(menuPtr: CPointer<GtkMenu>? = null) : MenuBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = menuPtr?.reinterpret() ?: gtk_menu_new()
}

fun menuWidget(menuPtr: CPointer<GtkMenu>? = null, init: Menu.() -> Unit): Menu {
    val menu = Menu(menuPtr)
    menu.init()
    return menu
}
