package org.guiVista.gui.widget.tool

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.layout.Container
import org.guiVista.gui.widget.tool.item.ToolItemBase

public actual class ToolBar(toolBarPtr: CPointer<GtkToolbar>? = null) : Container, ToolShell {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = toolBarPtr?.reinterpret() ?: gtk_toolbar_new()
    override val gtkToolShellPtr: CPointer<GtkToolShell>?
        get() = gtkWidgetPtr?.reinterpret()
    public val gtkToolBarPtr: CPointer<GtkToolbar>?
        get() = gtkWidgetPtr?.reinterpret()

    /**
     * The size of the icons in a [ToolBar] is normally determined by the [iconSize] setting. When this property is set
     * it overrides the setting. This should only be used for special-purpose toolbars. Normal application toolbars
     * should respect the user preferences for the size of icons. Default value
     * is *GtkIconSize.GTK_ICON_SIZE_LARGE_TOOLBAR*.
     */
    override var iconSize: GtkIconSize
        get() = gtk_toolbar_get_icon_size(gtkToolBarPtr)
        set(value) = gtk_toolbar_set_icon_size(gtkToolBarPtr, value)
    public actual var showArrow: Boolean
        get() = gtk_toolbar_get_show_arrow(gtkToolBarPtr) == TRUE
        set(value) = gtk_toolbar_set_show_arrow(gtkToolBarPtr, if (value) TRUE else FALSE)

    /** How to draw the toolbar. Default value is *GtkToolbarStyle.GTK_TOOLBAR_BOTH_HORIZ*. */
    public var toolBarStyle: GtkToolbarStyle
        get() = gtk_toolbar_get_style(gtkToolBarPtr)
        set(value) = gtk_toolbar_set_style(gtkToolBarPtr, value)

    public actual fun unsetStyle() {
        gtk_toolbar_unset_style(gtkToolBarPtr)
    }

    public actual fun insert(item: ToolItemBase, pos: Int) {
        gtk_toolbar_insert(toolbar = gtkToolBarPtr, item = item.gtkToolItemPtr, pos = pos)
    }

    public actual fun fetchItemIndex(item: ToolItemBase): Int =
        gtk_toolbar_get_item_index(gtkToolBarPtr, item.gtkToolItemPtr)

    /**
     * Fetches the item by [position][pos] on the [ToolBar], or *null* if the [ToolBar] doesn't contain the item.
     * @param pos The position on the [ToolBar].
     * @return The item on the [ToolBar], or *null* if the item doesn't exist at the [position][pos] on the [ToolBar].
     */
    public fun fetchItemAtPosition(pos: Int): CPointer<GtkToolItem>? = gtk_toolbar_get_nth_item(gtkToolBarPtr, pos)

    public actual fun fetchDropIndex(x: Int, y: Int): Int =
        gtk_toolbar_get_drop_index(toolbar = gtkToolBarPtr, x = x, y = y)

    public actual fun changeDropHighlightItem(toolItem: ToolItemBase, index: Int) {
        gtk_toolbar_set_drop_highlight_item(toolbar = gtkToolBarPtr, tool_item = toolItem.gtkToolItemPtr,
            index_ = index)
    }

    public actual fun unsetIconSize() {
        gtk_toolbar_unset_icon_size(gtkToolBarPtr)
    }
}

public fun toolBarWidget(toolBarPtr: CPointer<GtkToolbar>? = null, init: ToolBar.() -> Unit): ToolBar {
    val toolBar = ToolBar(toolBarPtr)
    toolBar.init()
    return toolBar
}
