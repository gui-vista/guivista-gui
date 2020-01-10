package org.guiVista.gui.layout

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.widget.WidgetBase

/** Packs widgets in rows and columns. */
class Grid(gridPtr: CPointer<GtkGrid>? = null) : Container {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = gridPtr?.reinterpret() ?: gtk_grid_new()
    val gtkGridPtr: CPointer<GtkGrid>?
        get() = gtkWidgetPtr?.reinterpret()
    /** The row to align the to the baseline when valign is GTK_ALIGN_BASELINE. Default value is *0*. */
    var baselineRow: Int
        set(value) = gtk_grid_set_baseline_row(gtkGridPtr, value)
        get() = gtk_grid_get_baseline_row(gtkGridPtr)
    /** If set to *true* then all columns are the same width. Default value is *false*. */
    var columnHomogeneous: Boolean
        set(value) = gtk_grid_set_column_homogeneous(gtkGridPtr, if (value) TRUE else FALSE)
        get() = gtk_grid_get_column_homogeneous(gtkGridPtr) == TRUE
    /** If set to *true* then all row are the same height. Default value is *false*. */
    var rowHomogeneous: Boolean
        set(value) = gtk_grid_set_row_homogeneous(gtkGridPtr, if (value) TRUE else FALSE)
        get() = gtk_grid_get_row_homogeneous(gtkGridPtr) == TRUE
    /** The amount of space between two consecutive columns. Default value is *0*. */
    var columnSpacing: UInt
        set(value) = gtk_grid_set_column_spacing(gtkGridPtr, value)
        get() = gtk_grid_get_column_spacing(gtkGridPtr)
    /** The amount of space between two consecutive rows. Default value is *0*. */
    var rowSpacing: UInt
        set(value) = gtk_grid_set_row_spacing(gtkGridPtr, value)
        get() = gtk_grid_get_row_spacing(gtkGridPtr)

    /**
     * Inserts a row at the specified position. Children which are attached at or below this position are moved one row
     * down. Children which span across this position are grown to span the new row.
     * @param pos The position to insert the row.
     */
    fun insertRow(pos: Int) {
        gtk_grid_insert_row(gtkGridPtr, pos)
    }

    /**
     * Inserts a column at the specified position. Children which are attached at or to the right of this position are
     * moved one column to the right. Children which span across this position are grown to span the new column.
     * @param pos The position to insert the column.
     */
    fun insertColumn(pos: Int) {
        gtk_grid_insert_column(gtkGridPtr, pos)
    }

    /**
     * Removes a row from the grid. Children that are placed in this row are removed, spanning children that overlap
     * this row have their height reduced by one, and children below the row are moved up.
     * @param pos The position of the row to remove.
     */
    fun removeRow(pos: Int) {
        gtk_grid_remove_row(gtkGridPtr, pos)
    }

    /**
     * Removes a column from the grid. Children that are placed in this column are removed, spanning children that
     * overlap this column have their width reduced by one, and children after the column are moved to the left.
     * @param pos The position of the column to remove.
     */
    fun removeColumn(pos: Int) {
        gtk_grid_remove_column(gtkGridPtr, pos)
    }

    /**
     * Adds a [child] to the grid. The position of the [child] is determined by [left] and [top]. The number
     * of cells that the [child] will occupy is determined by [width] and [height].
     * @param child The widget to add.
     * @param left The column number to attach the left side of the [child] to.
     * @param top The row number to attach the top side of the [child] to.
     */
    fun attachChild(child: WidgetBase, left: Int, top: Int, width: Int, height: Int) {
        gtk_grid_attach(grid = gtkGridPtr, child = child.gtkWidgetPtr, left = left, top = top, width = width,
            height = height)
    }

    /**
     * Adds a [child] to the grid. The [child] is placed next to its [sibling] on the side determined by side.
     * When [sibling] is null the [child] is placed in row (for left or right placement) or column 0 (for top or
     * bottom placement), at the end indicated by side. Attaching widgets labeled [1], [2], [3] with sibling == null
     * and side == `GTK_POS_LEFT` yields a layout of 3[1].
     * @param child The widget to add.
     * @param sibling The widget in the grid that the added [child] will be placed next to, or null to place the
     * [child] at the beginning or end.
     * @param width The number of columns that [child] will span.
     * @param height The number of rows that [child] will span.
     */
    fun attachChildNextTo(child: WidgetBase, sibling: WidgetBase, side: GtkPositionType, width: Int, height: Int) {
        gtk_grid_attach_next_to(
            grid = gtkGridPtr,
            child = child.gtkWidgetPtr,
            sibling = sibling.gtkWidgetPtr,
            side = side,
            width = width,
            height = height
        )
    }

    /**
     * Inserts a row or column at the specified position. The new row or column is placed next to sibling on the side
     * determined by side . If side is `GTK_POS_TOP` or `GTK_POS_BOTTOM` a row is inserted. If side is `GTK_POS_LEFT`
     * of `GTK_POS_RIGHT` a column is inserted.
     * @param sibling The child of the grid that the new row, or column will be placed next to.
     * @param side The side of [sibling] that the child is positioned next to.
     */
    fun insertNextTo(sibling: WidgetBase, side: GtkPositionType) {
        gtk_grid_insert_next_to(grid = gtkGridPtr, sibling = sibling.gtkWidgetPtr, side = side)
    }

    /**
     * Changes how the baseline should be positioned on row of the grid, in case that row is assigned more space than
     * is requested.
     * @param row The row index.
     * @param pos The baseline position to use.
     */
    fun changeRowBaselinePosition(row: Int, pos: GtkBaselinePosition) {
        gtk_grid_set_row_baseline_position(grid = gtkGridPtr, row = row, pos = pos)
    }

    /**
     * Fetches the baseline position of the [row].
     * @param row The row index.
     * @return The baseline position if it is set, otherwise the default value `GTK_BASELINE_POSITION_CENTER` is
     * returned.
     */
    fun fetchRowBaselinePosition(row: Int): GtkBaselinePosition = gtk_grid_get_row_baseline_position(gtkGridPtr, row)
}

fun gridLayout(gridPtr: CPointer<GtkGrid>? = null, init: Grid.() -> Unit): Grid {
    val grid = Grid(gridPtr)
    grid.init()
    return grid
}
