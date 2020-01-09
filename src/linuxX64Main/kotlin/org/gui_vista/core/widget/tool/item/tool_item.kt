package org.gui_vista.core.widget.tool.item

import gtk3.GtkToolItem
import gtk3.gtk_tool_item_new
import kotlinx.cinterop.CPointer

/** A widget that can be added to [ToolShell][org.gui_vista.core.widget.tool.ToolShell]. */
class ToolItem(toolItemPtr: CPointer<GtkToolItem>? = null) : ToolItemBase {
    override val gtkToolItemPtr: CPointer<GtkToolItem>? = toolItemPtr ?: gtk_tool_item_new()
}

fun toolItemWidget(toolItemPtr: CPointer<GtkToolItem>? = null, init: ToolItem.() -> Unit): ToolItem {
    val toolItem = ToolItem(toolItemPtr)
    toolItem.init()
    return toolItem
}
