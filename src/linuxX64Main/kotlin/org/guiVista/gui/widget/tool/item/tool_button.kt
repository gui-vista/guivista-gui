package org.guiVista.gui.widget.tool.item

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.widget.WidgetBase

public actual class ToolButton(
    toolButtonPtr: CPointer<GtkToolButton>? = null,
    iconWidget: WidgetBase?,
    label: String
) : ToolButtonBase {
    override val gtkToolItemPtr: CPointer<GtkToolItem>? =
        toolButtonPtr?.reinterpret() ?: gtk_tool_button_new(iconWidget?.gtkWidgetPtr, label)
    override val gtkToolButtonPtr: CPointer<GtkToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
}

public fun toolButtonWidget(
    toolButtonPtr: CPointer<GtkToolButton>? = null,
    iconWidget: WidgetBase?,
    label: String,
    init: ToolButton.() -> Unit = {}
): ToolButton {
    val toolButton = ToolButton(toolButtonPtr = toolButtonPtr, iconWidget = iconWidget, label = label)
    toolButton.init()
    return toolButton
}
