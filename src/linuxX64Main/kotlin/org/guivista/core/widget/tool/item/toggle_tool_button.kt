package org.guivista.core.widget.tool.item

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** A tool item containing a toggle button. */
class ToggleToolButton : ToggleToolButtonBase {
    override val gtkToolItemPtr: CPointer<GtkToolItem>? = gtk_toggle_tool_button_new()
    override val gtkToggleToolButtonPtr: CPointer<GtkToggleToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    override val gtkToolButtonPtr: CPointer<GtkToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    override val gtkWidgetPtr: CPointer<GtkWidget>? = null
}

fun toggleToolButtonWidget(init: ToggleToolButton.() -> Unit): ToggleToolButton {
    val button = ToggleToolButton()
    button.init()
    return button
}
