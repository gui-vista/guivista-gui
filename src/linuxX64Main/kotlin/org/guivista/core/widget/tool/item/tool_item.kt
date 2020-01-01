package org.guivista.core.widget.tool.item

import gtk3.GtkToolItem
import gtk3.GtkWidget
import gtk3.gtk_tool_item_new
import kotlinx.cinterop.CPointer

class ToolItem(toolItemPtr: CPointer<GtkToolItem>? = null) : ToolItemBase {
    override val gtkToolItemPtr: CPointer<GtkToolItem>? = toolItemPtr ?: gtk_tool_item_new()
    override val gtkWidgetPtr: CPointer<GtkWidget>? = null
}

fun toolItemWidget(toolItemPtr: CPointer<GtkToolItem>? = null, init: ToolItem.() -> Unit): ToolItem {
    val toolItem = ToolItem(toolItemPtr)
    toolItem.init()
    return toolItem
}
