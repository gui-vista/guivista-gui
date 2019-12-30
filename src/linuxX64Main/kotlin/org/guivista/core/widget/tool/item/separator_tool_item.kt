package org.guivista.core.widget.tool.item

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** A toolbar item that separates groups of other toolbar items. */
class SeparatorToolItem : ToolItemBase {
    override val gtkToolItemPtr: CPointer<GtkToolItem>? = gtk_separator_tool_item_new()
    val gtkSeparatorToolItemPtr: CPointer<GtkSeparatorToolItem>?
        get() = gtkToolItemPtr?.reinterpret()
    override val gtkWidgetPtr: CPointer<GtkWidget>? = null
    /** Whether the separator is drawn, or just blank. */
    var draw: Boolean
        get() = gtk_separator_tool_item_get_draw(gtkSeparatorToolItemPtr) == TRUE
        set(value) = gtk_separator_tool_item_set_draw(gtkSeparatorToolItemPtr, if (value) TRUE else FALSE)
}

fun separatorToolItemWidget(init: SeparatorToolItem.() -> Unit): SeparatorToolItem {
    val toolItem = SeparatorToolItem()
    toolItem.init()
    return toolItem
}
