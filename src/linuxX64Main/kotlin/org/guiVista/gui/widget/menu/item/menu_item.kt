package org.guiVista.gui.widget.menu.item

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class MenuItem private constructor(menuItemPtr: CPointer<GtkMenuItem>? = null) : MenuItemBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = menuItemPtr?.reinterpret() ?: gtk_menu_item_new()

    public actual companion object {
        public fun fromPointer(menuItemPtr: CPointer<GtkMenuItem>?): MenuItem = MenuItem(menuItemPtr)

        public actual fun create(): MenuItem = MenuItem(gtk_menu_item_new()?.reinterpret())

        public actual fun fromLabel(label: String): MenuItem =
            MenuItem(gtk_menu_item_new_with_label(label)?.reinterpret())

        public actual fun fromMnemonic(label: String): MenuItem =
            MenuItem(gtk_menu_item_new_with_mnemonic(label)?.reinterpret())

    }
}

public fun menuItem(
    menuItemPtr: CPointer<GtkMenuItem>? = null,
    label: String = "",
    mnemonic: String = "",
    init: MenuItem.() -> Unit = {}
): MenuItem {
    val menuItem = when {
        menuItemPtr != null -> MenuItem.fromPointer(menuItemPtr)
        label.isNotEmpty() -> MenuItem.fromLabel(label)
        mnemonic.isNotEmpty() -> MenuItem.fromMnemonic(mnemonic)
        else -> MenuItem.create()
    }
    menuItem.init()
    return menuItem
}
