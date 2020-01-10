package org.guiVista.core.widget.tool.item

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal

private const val SHOW_MENU_SIGNAL = "show-menu"

/** A tool item containing a button with an additional drop down menu. */
class MenuToolButton(iconWidget: CPointer<GtkWidget>?, label: String) : ToolButtonBase {
    override val gtkToolItemPtr: CPointer<GtkToolItem>? = gtk_menu_tool_button_new(iconWidget, label)
    val gtkMenuToolButtonPtr: CPointer<GtkMenuToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    override val gtkToolButtonPtr: CPointer<GtkToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    var menu: CPointer<GtkWidget>?
        get() = gtk_menu_tool_button_get_menu(gtkMenuToolButtonPtr)
        set(value) = gtk_menu_tool_button_set_menu(gtkMenuToolButtonPtr, value)

    /**
     * Changes the tooltip text to be used as tooltip for the arrow button which pops up the menu. See
     * `gtk_tool_item_set_tooltip_text()` for setting a tooltip on the whole [MenuToolButton].
     * @param text Text to be used as tooltip text for button’s arrow button.
     */
    fun changeArrowTooltipText(text: String) {
        gtk_menu_tool_button_set_arrow_tooltip_text(gtkMenuToolButtonPtr, text)
    }

    /**
     * Connects the *show-menu* signal to a [slot] on a [MenuToolButton]. This signal is used to populate the menu on
     * demand when [menu] is set. Note that even if you populate the menu dynamically in this way, you **must** set an
     * empty menu on the [MenuToolButton] beforehand, since the arrow is made insensitive if the menu is not set.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectShowMenuSignal(slot: CPointer<ShowMenuSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkMenuToolButtonPtr, signal = SHOW_MENU_SIGNAL, slot = slot, data = userData)
}

fun menuToolButtonWidget(iconWidget: CPointer<GtkWidget>?, label: String,
                         init: MenuToolButton.() -> Unit): MenuToolButton {
    val menuToolButton = MenuToolButton(iconWidget, label)
    menuToolButton.init()
    return menuToolButton
}

/**
 * The event handler for the *show-menu* signal. Arguments:
 * 1. button: CPointer<GtkMenuToolButton>
 * 2. userData: gpointer
 */
typealias ShowMenuSlot = CFunction<(button: CPointer<GtkMenuToolButton>?, userData: gpointer) -> Unit>
