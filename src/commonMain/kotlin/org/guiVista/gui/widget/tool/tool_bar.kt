package org.guiVista.gui.widget.tool

import org.guiVista.gui.layout.Container
import org.guiVista.gui.widget.tool.item.ToolItemBase

/** Create bars of buttons and other widgets. */
public expect class ToolBar : Container, ToolShell {
    /** If an arrow should be shown if the toolbar doesn't fit. Default value is *true*. */
    public var showArrow: Boolean

    /** The number of items on the toolbar. */
    public val totalItems: Int

    /**
     * Unsets a toolbar style set with `style` so that user preferences will be used to determine the toolbar style.
     */
    public fun unsetStyle()

    /**
     * Insert a [ToolItemBase] into the [ToolBar] at [position][pos]. If [pos] is 0 then the item is prepended to the
     * start of the [ToolBar]. If [pos] is negative then the item is appended to the end of the [ToolBar].
     * @param item A tool item.
     * @param pos The position of the new item.
     */
    public fun insert(item: ToolItemBase, pos: Int)

    /**
     * Fetches the position of the item on the [ToolBar], starting from 0. It is an error if the item is not a child of
     * the [ToolBar].
     * @param item A tool item that is a child of the [ToolBar].
     * @return The position of item on the [ToolBar].
     */
    public fun fetchItemIndex(item: ToolItemBase): Int

    /**
     * Fetches the position corresponding to the indicated point on [ToolBar]. This is useful when dragging items to
     * the [ToolBar]. This function returns the position a new item should be inserted. x and y are in toolbar coordinates.
     * @param x The x coordinate (horizontal) of a point on the [ToolBar].
     * @param y The y coordinate (vertical) of a point on the [ToolBar].
     * @return The position corresponding to the point ([x], [y]) on the [ToolBar].
     */
    public fun fetchDropIndex(x: Int, y: Int): Int

    /**
     * Highlights [ToolBar] to give an idea of what it would look like if the [item][toolItem] was added to the
     * [ToolBar] at the position indicated by [index]. If [item][toolItem] is *null* then highlighting is turned off.
     * In that case [index] is ignored.
     *
     * The [item][toolItem] passed to this function must not be part of any widget hierarchy. When an [item][toolItem]
     * is set as drop highlight item it can not added to any widget hierarchy, or used as highlight item for another
     * [ToolBar].
     */
    public fun changeDropHighlightItem(toolItem: ToolItemBase, index: Int)

    /**
     * Unsets toolbar icon size set with `iconSize` so that user preferences will be used to determine the icon size.
     */
    public fun unsetIconSize()
}