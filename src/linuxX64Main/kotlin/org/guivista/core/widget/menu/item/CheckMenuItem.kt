package org.guivista.core.widget.menu.item

import gtk3.GtkWidget
import gtk3.gtk_check_menu_item_new
import gtk3.gtk_check_menu_item_new_with_mnemonic
import kotlinx.cinterop.CPointer

/** A menu item with a check box. */
class CheckMenuItem(label: String = "", mnemonic: Boolean = false) : CheckMenuItemBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        if (label.isNotEmpty() && mnemonic) gtk_check_menu_item_new_with_mnemonic(label)
        else if (label.isNotEmpty() && !mnemonic) gtk_check_menu_item_new_with_mnemonic(label)
        else gtk_check_menu_item_new()
}
