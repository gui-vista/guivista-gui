package org.guiVista.gui.widget.menu.item

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class CheckMenuItem private constructor(checkMenuItemPtr: CPointer<GtkCheckMenuItem>?) :
    CheckMenuItemBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = checkMenuItemPtr?.reinterpret()

    public actual companion object {
        public fun fromPointer(checkMenuItemPtr: CPointer<GtkCheckMenuItem>?): CheckMenuItem =
            CheckMenuItem(checkMenuItemPtr)

        public actual fun create(): CheckMenuItem = CheckMenuItem(gtk_check_menu_item_new()?.reinterpret())

        public actual fun fromLabel(label: String): CheckMenuItem =
            CheckMenuItem(gtk_check_menu_item_new_with_label(label)?.reinterpret())

        public actual fun fromMnemonic(label: String): CheckMenuItem =
            CheckMenuItem(gtk_check_menu_item_new_with_mnemonic(label)?.reinterpret())
    }
}

public fun checkMenuItem(
    checkMenuItemPtr: CPointer<GtkCheckMenuItem>? = null,
    label: String = "",
    mnemonic: String = "",
    init: CheckMenuItem.() -> Unit = {}
): CheckMenuItem {
    val menuItem = when {
        checkMenuItemPtr != null -> CheckMenuItem.fromPointer(checkMenuItemPtr)
        label.isNotEmpty() -> CheckMenuItem.fromLabel(label)
        mnemonic.isNotEmpty() -> CheckMenuItem.fromMnemonic(mnemonic)
        else -> CheckMenuItem.create()
    }
    menuItem.init()
    return menuItem
}
