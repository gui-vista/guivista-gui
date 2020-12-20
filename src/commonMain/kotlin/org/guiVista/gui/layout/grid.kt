package org.guiVista.gui.layout

import org.guiVista.gui.widget.WidgetBase

/** Packs widgets in rows and columns. */
public expect class Grid : Container {
    /** The row to align the to the baseline when valign is GTK_ALIGN_BASELINE. Default value is *0*. */
    public var baselineRow: Int

    /** If set to *true* then all columns are the same width. Default value is *false*. */
    public var columnHomogeneous: Boolean

    /** If set to *true* then all row are the same height. Default value is *false*. */
    public var rowHomogeneous: Boolean

    /** The amount of space between two consecutive columns. Default value is *0*. */
    public var columnSpacing: UInt

    /** The amount of space between two consecutive rows. Default value is *0*. */
    public var rowSpacing: UInt

    /**
     * Inserts a column at the specified position. Children which are attached at or to the right of this position are
     * moved one column to the right. Children which span across this position are grown to span the new column.
     * @param pos The position to insert the column.
     */
    public infix fun insertColumn(pos: Int)

    /**
     * Removes a row from the grid. Children that are placed in this row are removed, spanning children that overlap
     * this row have their height reduced by one, and children below the row are moved up.
     * @param pos The position of the row to remove.
     */
    public infix fun removeRow(pos: Int)

    /**
     * Removes a column from the grid. Children that are placed in this column are removed, spanning children that
     * overlap this column have their width reduced by one, and children after the column are moved to the left.
     * @param pos The position of the column to remove.
     */
    public infix fun removeColumn(pos: Int)

    /**
     * Adds a [child] to the grid. The position of the [child] is determined by [left] and [top]. The number
     * of cells that the [child] will occupy is determined by [width] and [height].
     * @param child The widget to add.
     * @param left The column number to attach the left side of the [child] to.
     * @param top The row number to attach the top side of the [child] to.
     */
    public fun attachChild(child: WidgetBase, left: Int, top: Int, width: Int, height: Int)

    /**
     * Inserts a row at the specified position. Children which are attached at or below this position are moved one row
     * down. Children which span across this position are grown to span the new row.
     * @param pos The position to insert the row.
     */
    public infix fun insertRow(pos: Int)
}