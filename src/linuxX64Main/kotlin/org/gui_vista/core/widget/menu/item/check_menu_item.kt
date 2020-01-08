package org.gui_vista.core.widget.menu.item

import gtk3.GtkCheckMenuItem
import gtk3.GtkWidget
import gtk3.gtk_check_menu_item_new
import gtk3.gtk_check_menu_item_new_with_mnemonic
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** A menu item with a check box. */
class CheckMenuItem(checkMenuItemPtr: CPointer<GtkCheckMenuItem>? = null, label: String = "",
                    mnemonic: Boolean = false) : CheckMenuItemBase {
    @Suppress("IfThenToElvis")
    override val gtkWidgetPtr: CPointer<GtkWidget>? = when {
        checkMenuItemPtr != null -> checkMenuItemPtr.reinterpret()
        label.isNotEmpty() && mnemonic -> gtk_check_menu_item_new_with_mnemonic(label)
        label.isNotEmpty() && !mnemonic -> gtk_check_menu_item_new_with_mnemonic(label)
        else -> gtk_check_menu_item_new()
    }
}

fun checkMenuItem(checkMenuItemPtr: CPointer<GtkCheckMenuItem>? = null, label: String = "", mnemonic: Boolean = false,
                  init: CheckMenuItem.() -> Unit): CheckMenuItem {
    val menuItem = CheckMenuItem(checkMenuItemPtr = checkMenuItemPtr, label = label, mnemonic = mnemonic)
    menuItem.init()
    return menuItem
}
