package org.guiVista.gui.widget.tool.item

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class ToolButton(toolButtonPtr: CPointer<GtkToolButton>? = null, iconWidget: CPointer<GtkWidget>?,
                               label: String) : ToolButtonBase {
    override val gtkToolItemPtr: CPointer<GtkToolItem>? =
        toolButtonPtr?.reinterpret() ?: gtk_tool_button_new(iconWidget, label)
    override val gtkToolButtonPtr: CPointer<GtkToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
}

public fun toolButtonWidget(toolButtonPtr: CPointer<GtkToolButton>? = null, iconWidget: CPointer<GtkWidget>?, label: String,
                            init: ToolButton.() -> Unit): ToolButton {
    val toolButton = ToolButton(toolButtonPtr = toolButtonPtr, iconWidget = iconWidget, label = label)
    toolButton.init()
    return toolButton
}
