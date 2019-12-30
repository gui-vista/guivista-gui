package org.guivista.core.widget.tool.item

import gtk3.GtkToolItem
import gtk3.GtkWidget
import gtk3.gtk_tool_item_new
import kotlinx.cinterop.CPointer

class ToolItem : ToolItemBase {
    override val gtkToolItemPtr: CPointer<GtkToolItem>? = gtk_tool_item_new()
    override val gtkWidgetPtr: CPointer<GtkWidget>? = null
}

fun toolItemWidget(init: ToolItem.() -> Unit): ToolItem {
    val toolItem = ToolItem()
    toolItem.init()
    return toolItem
}
