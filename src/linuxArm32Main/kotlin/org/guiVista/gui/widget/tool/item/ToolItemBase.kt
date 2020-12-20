package org.guiVista.gui.widget.tool.item

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.SizeGroup
import org.guiVista.gui.layout.Container

public actual interface ToolItemBase : Container {
    public val gtkToolItemPtr: CPointer<GtkToolItem>?
    override val gtkWidgetPtr: CPointer<GtkWidget>?
        get() = gtkToolItemPtr?.reinterpret()

    /**
     * The size group used for labels in the tool item. Custom implementations of [ToolItemBase] should call this
     * function, and use the size group for labels.
     */
    public val textSizeGroup: SizeGroup
        get() = SizeGroup(gtk_tool_item_get_text_size_group(gtkToolItemPtr))

    /**
     * The relief style of the tool item . Custom implementations of [ToolItemBase] should call this function in the
     * handler of the “toolbar_reconfigured” signal to find out the relief style of buttons.
     * @see [org.guiVista.gui.widget.button.Button.relief]
     */
    public val reliefStyle: GtkReliefStyle
        get() = gtk_tool_item_get_relief_style(gtkToolItemPtr)

    /**
     * Whether the toolbar item is considered important. When *true* the toolbar buttons show text in
     * `GTK_TOOLBAR_BOTH_HORIZ` mode. Default value is *false*.
     */
    public var isImportant: Boolean
        get() = gtk_tool_item_get_is_important(gtkToolItemPtr) == TRUE
        set(value) = gtk_tool_item_set_is_important(gtkToolItemPtr, if (value) TRUE else FALSE)

    /**
     * Whether the toolbar item is visible when the [ToolBar][org.guiVista.gui.widget.tool.ToolBar] is in a horizontal
     * orientation. Default value is *true*.
     */
    public var visibleHorizontal: Boolean
        get() = gtk_tool_item_get_visible_horizontal(gtkToolItemPtr) == TRUE
        set(value) = gtk_tool_item_set_visible_horizontal(gtkToolItemPtr, if (value) TRUE else FALSE)

    /**
     * Whether the toolbar item is visible when the [ToolBar][org.guiVista.gui.widget.tool.ToolBar] is in a vertical
     * orientation. Default value is *true*.
     */
    public var visibleVertical: Boolean
        get() = gtk_tool_item_get_visible_vertical(gtkToolItemPtr) == TRUE
        set(value) = gtk_tool_item_set_visible_vertical(gtkToolItemPtr, if (value) TRUE else FALSE)

    /**
     * Whether tool item is allocated extra space when there is more room on the
     * [ToolBar][org.guiVista.gui.widget.tool.ToolBar] then needed for the items. The effect is that the item gets
     * bigger when the [ToolBar][org.guiVista.gui.widget.tool.ToolBar] gets bigger, and smaller when the
     * [ToolBar][org.guiVista.gui.widget.tool.ToolBar] gets smaller.
     */
    public var expand: Boolean
        get() = gtk_tool_item_get_expand(gtkToolItemPtr) == TRUE
        set(value) = gtk_tool_item_set_expand(gtkToolItemPtr, if (value) TRUE else FALSE)

    /**
     * Whether tool item is to be allocated the same size as other homogeneous items. The effect is that all
     * homogeneous items will have the same width as the widest item.
     */
    public var homogeneous: Boolean
        get() = gtk_tool_item_get_homogeneous(gtkToolItemPtr) == TRUE
        set(value) = gtk_tool_item_set_homogeneous(gtkToolItemPtr, if (value) TRUE else FALSE)

    /**
     * Whether tool item has a drag window. When *true* the tool item can be used as a drag source through
     * `gtk_drag_source_set()`. When tool item has a drag window it will intercept all events, even those that would
     * otherwise be sent to a child of tool item.
     */
    public var useDragWindow: Boolean
        get() = gtk_tool_item_get_use_drag_window(gtkToolItemPtr) == TRUE
        set(value) = gtk_tool_item_set_use_drag_window(gtkToolItemPtr, if (value) TRUE else FALSE)

    /**
     * Fetches the icon size used for tool item. Custom implementations of [ToolItemBase] should call this function to
     * find out what size icons they should use.
     */
    public val iconSize: GtkIconSize
        get() = gtk_tool_item_get_icon_size(gtkToolItemPtr)

    /**
     * Fetches the orientation used for tool item. Custom implementations of [ToolItemBase] should call this function
     * to find out what size icons they should use.
     */
    public val orientation: GtkOrientation
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
    public val toolBarStyle: GtkToolbarStyle
        get() = gtk_tool_item_get_toolbar_style(gtkToolItemPtr)

    /**
     * Fetches the text alignment used for tool item. Custom implementations of [ToolItemBase] should call this
     * function to find out how text should be aligned.
     */
    public val textAlignment: Float
        get() = gtk_tool_item_get_text_alignment(gtkToolItemPtr)

    /**
     * Fetches the text orientation used for tool item. Custom subclasses of [ToolItemBase] should call this function
     * to find out how text should be orientated.
     */
    public val textOrientation: GtkOrientation
        get() = gtk_tool_item_get_text_orientation(gtkToolItemPtr)

    /**
     * Calling this function signals to the toolbar that the overflow menu item for the tool item has changed. If the
     * overflow menu is visible when this function it called, then the menu will be rebuilt. The function must be
     * called when the tool item changes what it will do in response to the “create-menu-proxy” signal.
     */
    public fun rebuildMenu() {
        gtk_tool_item_rebuild_menu(gtkToolItemPtr)
    }

    /**
     * Changes the text to be displayed as tooltip on the item. See `gtk_widget_set_tooltip_text()`.
     * @param text The text to be used as tooltip for the tool item.
     */
    public infix fun changeTooltipText(text: String) {
        gtk_tool_item_set_tooltip_text(gtkToolItemPtr, text)
    }
}
