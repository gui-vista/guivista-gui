package org.guivista.core.widget.tool.item

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** A tool item that displays buttons. */
class ToolButton(iconWidget: CPointer<GtkWidget>?, label: String) : ToolButtonBase {
    override val gtkToolItemPtr: CPointer<GtkToolItem>? = gtk_tool_button_new(iconWidget, label)
    override val gtkToolButtonPtr: CPointer<GtkToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    override val gtkWidgetPtr: CPointer<GtkWidget>? = null
}

fun toolButtonWidget(iconWidget: CPointer<GtkWidget>?, label: String, init: ToolButton.() -> Unit): ToolButton {
    val toolButton = ToolButton(iconWidget, label)
    toolButton.init()
    return toolButton
}
