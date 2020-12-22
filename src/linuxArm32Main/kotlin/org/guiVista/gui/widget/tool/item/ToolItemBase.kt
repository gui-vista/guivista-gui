package org.guiVista.gui.widget.tool.item

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.SizeGroup
import org.guiVista.gui.layout.Container
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase

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

    /**
     * Sets the markup text to be displayed as a tooltip on the item.
     * @param markup The markup text to be used for the tooltip.
     */
    public infix fun changeTooltipMarkup(markup: String) {
        gtk_tool_item_set_tooltip_markup(gtkToolItemPtr, markup)
    }

    /**
     * Sets the menu item used in the toolbar overflow menu. The [id] is used to identify the caller of this function,
     * and should also be used with [fetchProxyMenuItemById].
     * @param id A string used to identify the menu item.
     * @param menuItem The menu item to use in the overflow menu.
     */
    public fun changeProxyMenuItem(id: String, menuItem: WidgetBase) {
        gtk_tool_item_set_proxy_menu_item(tool_item = gtkToolItemPtr, menu_item_id = id,
            menu_item = menuItem.gtkWidgetPtr)
    }

    /**
     * If [id] matches the string passed to [changeProxyMenuItem] then return the corresponding menu item.
     * Custom implementations of [ToolItemBase] should use this function to update their menu item when the
     * tool item changes. Maps to [gtk_tool_item_get_proxy_menu_item](https://developer.gnome.org/gtk3/3.20/GtkToolItem.html#gtk-tool-item-get-proxy-menu-item).
     * @param id A String used to identify the menu item.
     * @return The menu item passed to [changeProxyMenuItem] if the id matches.
     */
    public fun fetchProxyMenuItemById(id: String): WidgetBase? {
        val ptr = gtk_tool_item_get_proxy_menu_item(gtkToolItemPtr, id)
        return if (ptr != null) Widget(ptr) else null
    }

    /**
     * Returns the menu item that was last set by [changeProxyMenuItem], ie. the menu item that is going to appear in
     * the overflow menu. Maps to [gtk_tool_item_retrieve_proxy_menu_item](https://developer.gnome.org/gtk3/3.20/GtkToolItem.html#gtk-tool-item-retrieve-proxy-menu-item).
     * @return The menu item that is going to appear in the overflow menu for tool item.
     */
    public fun fetchProxyMenuItem(): WidgetBase? {
        val ptr = gtk_tool_item_retrieve_proxy_menu_item(gtkToolItemPtr)
        return if (ptr != null) Widget(ptr) else null
    }
}
