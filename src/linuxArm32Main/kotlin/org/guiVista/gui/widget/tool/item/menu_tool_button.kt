package org.guiVista.gui.widget.tool.item

import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

public actual class MenuToolButton(iconWidget: CPointer<GtkWidget>?, label: String) : ToolButtonBase {
    override val gtkToolItemPtr: CPointer<GtkToolItem>? = gtk_menu_tool_button_new(iconWidget, label)
    public val gtkMenuToolButtonPtr: CPointer<GtkMenuToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    override val gtkToolButtonPtr: CPointer<GtkToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    public var menu: CPointer<GtkWidget>?
        get() = gtk_menu_tool_button_get_menu(gtkMenuToolButtonPtr)
        set(value) = gtk_menu_tool_button_set_menu(gtkMenuToolButtonPtr, value)

    public actual infix fun changeArrowTooltipText(text: String) {
        gtk_menu_tool_button_set_arrow_tooltip_text(gtkMenuToolButtonPtr, text)
    }

    /**
     * Connects the *show-menu* event to a [handler] on a [MenuToolButton]. This event is used to populate the menu on
     * demand when [menu] is set. Note that even if you populate the menu dynamically in this way, you **must** set an
     * empty menu on the [MenuToolButton] beforehand, since the arrow is made insensitive if the menu is not set.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectShowMenuEvent(handler: CPointer<ShowMenuHandler>, userData: gpointer): UInt =
        connectGSignal(obj = gtkMenuToolButtonPtr, signal = MenuToolButtonEvent.showMenu, slot = handler,
            data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkMenuToolButtonPtr, handlerId.toUInt())
    }
}

public fun menuToolButtonWidget(
    iconWidget: CPointer<GtkWidget>?,
    label: String,
    init: MenuToolButton.() -> Unit = {}
): MenuToolButton {
    val menuToolButton = MenuToolButton(iconWidget, label)
    menuToolButton.init()
    return menuToolButton
}

/**
 * The event handler for the *show-menu* event. Arguments:
 * 1. button: CPointer<GtkMenuToolButton>
 * 2. userData: gpointer
 */
public typealias ShowMenuHandler = CFunction<(button: CPointer<GtkMenuToolButton>?, userData: gpointer) -> Unit>
