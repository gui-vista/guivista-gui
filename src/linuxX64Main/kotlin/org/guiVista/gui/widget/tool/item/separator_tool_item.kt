package org.guiVista.gui.widget.tool.item

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** A toolbar item that separates groups of other toolbar items. */
class SeparatorToolItem(separatorToolItemPtr: CPointer<GtkSeparatorToolItem>? = null) : ToolItemBase {
    override val gtkToolItemPtr: CPointer<GtkToolItem>? =
        separatorToolItemPtr?.reinterpret() ?: gtk_separator_tool_item_new()
    val gtkSeparatorToolItemPtr: CPointer<GtkSeparatorToolItem>?
        get() = gtkToolItemPtr?.reinterpret()
    /** Whether the separator is drawn, or just blank. */
    var draw: Boolean
        get() = gtk_separator_tool_item_get_draw(gtkSeparatorToolItemPtr) == TRUE
        set(value) = gtk_separator_tool_item_set_draw(gtkSeparatorToolItemPtr, if (value) TRUE else FALSE)
}

fun separatorToolItemWidget(separatorToolItemPtr: CPointer<GtkSeparatorToolItem>? = null,
                            init: SeparatorToolItem.() -> Unit): SeparatorToolItem {
    val toolItem = SeparatorToolItem(separatorToolItemPtr)
    toolItem.init()
    return toolItem
}
