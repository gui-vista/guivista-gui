package org.guiVista.gui.widget.tool

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.layout.Container
import org.guiVista.gui.widget.tool.item.ToolItemBase

/** Create bars of buttons and other widgets. */
class ToolBar(toolBarPtr: CPointer<GtkToolbar>? = null) : Container, ToolShell {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = toolBarPtr?.reinterpret() ?: gtk_toolbar_new()
    override val gtkToolShellPtr: CPointer<GtkToolShell>?
        get() = gtkWidgetPtr?.reinterpret()
    val gtkToolBarPtr: CPointer<GtkToolbar>?
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
    /** If an arrow should be shown if the toolbar doesn't fit. Default value is *true*. */
    var showArrow: Boolean
        get() = gtk_toolbar_get_show_arrow(gtkToolBarPtr) == TRUE
        set(value) = gtk_toolbar_set_show_arrow(gtkToolBarPtr, if (value) TRUE else FALSE)
    /** How to draw the toolbar. Default value is *GtkToolbarStyle.GTK_TOOLBAR_BOTH_HORIZ*. */
    var toolBarStyle: GtkToolbarStyle
        get() = gtk_toolbar_get_style(gtkToolBarPtr)
        set(value) = gtk_toolbar_set_style(gtkToolBarPtr, value)

    /**
     * Unsets a toolbar style set with [style] so that user preferences will be used to determine the toolbar style.
     */
    fun unsetStyle() {
        gtk_toolbar_unset_style(gtkToolBarPtr)
    }

    /**
     * Insert a [ToolItemBase] into the [ToolBar] at [position][pos]. If [pos] is 0 then the item is prepended to the
     * start of the [ToolBar]. If [pos] is negative then the item is appended to the end of the [ToolBar].
     * @param item A tool item.
     * @param pos The position of the new item.
     */
    fun insert(item: ToolItemBase, pos: Int) {
        gtk_toolbar_insert(toolbar = gtkToolBarPtr, item = item.gtkToolItemPtr, pos = pos)
    }

    /**
     * Fetches the position of the item on the [ToolBar], starting from 0. It is an error if the item is not a child of
     * the [ToolBar].
     * @param item A tool item that is a child of the [ToolBar].
     * @return The position of item on the [ToolBar].
     */
    fun fetchItemIndex(item: ToolItemBase): Int = gtk_toolbar_get_item_index(gtkToolBarPtr, item.gtkToolItemPtr)

    /**
     * Fetches the item by [position][pos] on the [ToolBar], or *null* if the [ToolBar] doesn't contain the item.
     * @param pos The position on the [ToolBar].
     * @return The item on the [ToolBar], or *null* if the item doesn't exist at the [position][pos] on the [ToolBar].
     */
    fun fetchItemAtPosition(pos: Int): CPointer<GtkToolItem>? = gtk_toolbar_get_nth_item(gtkToolBarPtr, pos)

    /**
     * Fetches the position corresponding to the indicated point on [ToolBar]. This is useful when dragging items to
     * the [ToolBar]. This function returns the position a new item should be inserted. x and y are in toolbar coordinates.
     * @param x The x coordinate (horizontal) of a point on the [ToolBar].
     * @param y The y coordinate (vertical) of a point on the [ToolBar].
     * @return The position corresponding to the point ([x], [y]) on the [ToolBar].
     */
    fun fetchDropIndex(x: Int, y: Int): Int = gtk_toolbar_get_drop_index(toolbar = gtkToolBarPtr, x = x, y = y)

    /**
     * Highlights [ToolBar] to give an idea of what it would look like if the [item][toolItem] was added to the
     * [ToolBar] at the position indicated by [index]. If [item][toolItem] is *null* then highlighting is turned off.
     * In that case [index] is ignored.
     *
     * The [item][toolItem] passed to this function must not be part of any widget hierarchy. When an [item][toolItem]
     * is set as drop highlight item it can not added to any widget hierarchy, or used as highlight item for another
     * [ToolBar].
     */
    fun changeDropHighlightItem(toolItem: ToolItemBase, index: Int) {
        gtk_toolbar_set_drop_highlight_item(toolbar = gtkToolBarPtr, tool_item = toolItem.gtkToolItemPtr,
            index_ = index)
    }

    /**
     * Unsets toolbar icon size set with [iconSize] so that user preferences will be used to determine the icon size.
     */
    fun unsetIconSize() {
        gtk_toolbar_unset_icon_size(gtkToolBarPtr)
    }
}

fun toolBarWidget(toolBarPtr: CPointer<GtkToolbar>? = null, init: ToolBar.() -> Unit): ToolBar {
    val toolBar = ToolBar(toolBarPtr)
    toolBar.init()
    return toolBar
}
