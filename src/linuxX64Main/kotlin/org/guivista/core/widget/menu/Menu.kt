package org.guivista.core.widget.menu

import gtk3.GtkWidget
import gtk3.gtk_menu_new
import kotlinx.cinterop.CPointer

/** A menu widget. */
class Menu : MenuBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>?
        get() = gtk_menu_new()
}
