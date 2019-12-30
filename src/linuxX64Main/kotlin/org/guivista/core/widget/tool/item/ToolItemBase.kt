package org.guivista.core.widget.tool.item

import gtk3.*
import kotlinx.cinterop.CPointer
import org.guivista.core.layout.Container

/** The base interface of widgets that can be added to a tool shell. */
interface ToolItemBase : Container {
    val gtkToolItemPtr: CPointer<GtkToolItem>?
    /**
     * Whether the toolbar item is considered important. When *true* the toolbar buttons show text in
     * `GTK_TOOLBAR_BOTH_HORIZ` mode.
     */
    var isImportant: Boolean
        get() = gtk_tool_item_get_is_important(gtkToolItemPtr) == TRUE
        set(value) = gtk_tool_item_set_is_important(gtkToolItemPtr, if (value) TRUE else FALSE)
    /**
     * Whether the toolbar item is visible when the [org.guivista.core.widget.tool.ToolBar] is in a horizontal orientation.
     */
    var visibleHorizontal: Boolean
        get() = gtk_tool_item_get_visible_horizontal(gtkToolItemPtr) == TRUE
        set(value) = gtk_tool_item_set_visible_horizontal(gtkToolItemPtr, if (value) TRUE else FALSE)
    /**
     * Whether the toolbar item is visible when the [org.guivista.core.widget.tool.ToolBar] is in a vertical orientation.
     */
    var visibleVertical: Boolean
        get() = gtk_tool_item_get_visible_vertical(gtkToolItemPtr) == TRUE
        set(value) = gtk_tool_item_set_visible_vertical(gtkToolItemPtr, if (value) TRUE else FALSE)
    /**
     * Whether tool item is allocated extra space when there is more room on the
     * [org.guivista.core.widget.tool.ToolBar] then needed for the items. The effect is that the item gets bigger when
     * the [org.guivista.core.widget.tool.ToolBar] gets bigger, and smaller when the
     * [org.guivista.core.widget.tool.ToolBar] gets smaller.
     */
    var expand: Boolean
        get() = gtk_tool_item_get_expand(gtkToolItemPtr) == TRUE
        set(value) = gtk_tool_item_set_expand(gtkToolItemPtr, if (value) TRUE else FALSE)
    /**
     * Whether tool item is to be allocated the same size as other homogeneous items. The effect is that all
     * homogeneous items will have the same width as the widest item.
     */
    var homogeneous: Boolean
        get() = gtk_tool_item_get_homogeneous(gtkToolItemPtr) == TRUE
        set(value) = gtk_tool_item_set_homogeneous(gtkToolItemPtr, if (value) TRUE else FALSE)
    /**
     * Whether tool item has a drag window. When *true* the tool item can be used as a drag source through
     * `gtk_drag_source_set()`. When tool item has a drag window it will intercept all events, even those that would
     * otherwise be sent to a child of tool item.
     */
    var useDragWindow: Boolean
        get() = gtk_tool_item_get_use_drag_window(gtkToolItemPtr) == TRUE
        set(value) = gtk_tool_item_set_use_drag_window(gtkToolItemPtr, if (value) TRUE else FALSE)
    /**
     * Fetches the icon size used for tool item. Custom implementations of [ToolItemBase] should call this function to
     * find out what size icons they should use.
     */
    val iconSize: GtkIconSize
        get() = gtk_tool_item_get_icon_size(gtkToolItemPtr)
    /**
     * Fetches the orientation used for tool item. Custom implementations of [ToolItemBase] should call this function
     * to find out what size icons they should use.
     */
    val orientation: GtkOrientation
        get() = gtk_tool_item_get_orientation(gtkToolItemPtr)
    /**
     * Returns the toolbar style used for tool item. Custom implementations of [ToolItemBase] should call this function
     * in the handler of the GtkToolItem::toolbar_reconfigured signal to find out in what style the toolbar is
     * displayed and change themselves accordingly. Below are the styles:
     * - **GTK_TOOLBAR_BOTH**: The tool item should show both an icon and a label, stacked vertically
     * - **GTK_TOOLBAR_ICONS**: The toolbar shows only icons
     * - **GTK_TOOLBAR_TEXT**: The tool item should only show text
     * - **GTK_TOOLBAR_BOTH_HORIZ**: The tool item should show both an icon and a label, arranged horizontally
     */
    val toolBarStyle: GtkToolbarStyle
        get() = gtk_tool_item_get_toolbar_style(gtkToolItemPtr)
    /**
     * Fetches the text alignment used for tool item. Custom implementations of [ToolItemBase] should call this
     * function to find out how text should be aligned.
     */
    val textAlignment: Float
        get() = gtk_tool_item_get_text_alignment(gtkToolItemPtr)
    /**
     * Fetches the text orientation used for tool item. Custom subclasses of [ToolItemBase] should call this function
     * to find out how text should be orientated.
     */
    val textOrientation: GtkOrientation
        get() = gtk_tool_item_get_text_orientation(gtkToolItemPtr)


    /**
     * Changes the text to be displayed as tooltip on the item. See `gtk_widget_set_tooltip_text()`.
     * @param text The text to be used as tooltip for the tool item.
     */
    fun changeTooltipText(text: String) {
        gtk_tool_item_set_tooltip_text(gtkToolItemPtr, text)
    }
}
