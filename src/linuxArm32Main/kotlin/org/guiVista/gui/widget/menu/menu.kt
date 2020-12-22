package org.guiVista.gui.widget.menu

import gtk3.GtkMenu
import gtk3.GtkWidget
import gtk3.gtk_menu_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class Menu(menuPtr: CPointer<GtkMenu>? = null, widgetPtr: CPointer<GtkWidget>?) : MenuBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        if (menuPtr != null && widgetPtr == null) menuPtr.reinterpret()
        else if (widgetPtr != null && menuPtr == null) widgetPtr
        else gtk_menu_new()
}

public fun menuWidget(
    menuPtr: CPointer<GtkMenu>? = null,
    widgetPtr: CPointer<GtkWidget>? = null,
    init: Menu.() -> Unit
): Menu {
    val menu = Menu(menuPtr, widgetPtr)
    menu.init()
    return menu
}
