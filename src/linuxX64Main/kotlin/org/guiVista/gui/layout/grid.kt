package org.guiVista.gui.layout

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.widget.WidgetBase

public actual class Grid(gridPtr: CPointer<GtkGrid>? = null) : Container {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = gridPtr?.reinterpret() ?: gtk_grid_new()
    public val gtkGridPtr: CPointer<GtkGrid>?
        get() = gtkWidgetPtr?.reinterpret()
    public actual var baselineRow: Int
        set(value) = gtk_grid_set_baseline_row(gtkGridPtr, value)
        get() = gtk_grid_get_baseline_row(gtkGridPtr)
    public actual var columnHomogeneous: Boolean
        set(value) = gtk_grid_set_column_homogeneous(gtkGridPtr, if (value) TRUE else FALSE)
        get() = gtk_grid_get_column_homogeneous(gtkGridPtr) == TRUE
    public actual var rowHomogeneous: Boolean
        set(value) = gtk_grid_set_row_homogeneous(gtkGridPtr, if (value) TRUE else FALSE)
        get() = gtk_grid_get_row_homogeneous(gtkGridPtr) == TRUE
    public actual var columnSpacing: UInt
        set(value) = gtk_grid_set_column_spacing(gtkGridPtr, value)
        get() = gtk_grid_get_column_spacing(gtkGridPtr)
    public actual var rowSpacing: UInt
        set(value) = gtk_grid_set_row_spacing(gtkGridPtr, value)
        get() = gtk_grid_get_row_spacing(gtkGridPtr)

    public actual infix fun insertRow(pos: Int) {
        gtk_grid_insert_row(gtkGridPtr, pos)
    }

    public actual infix fun insertColumn(pos: Int) {
        gtk_grid_insert_column(gtkGridPtr, pos)
    }

    public actual infix fun removeRow(pos: Int) {
        gtk_grid_remove_row(gtkGridPtr, pos)
    }

    public actual infix fun removeColumn(pos: Int) {
        gtk_grid_remove_column(gtkGridPtr, pos)
    }

    public actual fun attachChild(child: WidgetBase, left: Int, top: Int, width: Int, height: Int) {
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
    public fun attachChildNextTo(child: WidgetBase, sibling: WidgetBase, side: GtkPositionType, width: Int, height: Int) {
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
    public fun insertNextTo(sibling: WidgetBase, side: GtkPositionType) {
        gtk_grid_insert_next_to(grid = gtkGridPtr, sibling = sibling.gtkWidgetPtr, side = side)
    }

    /**
     * Changes how the baseline should be positioned on row of the grid, in case that row is assigned more space than
     * is requested.
     * @param row The row index.
     * @param pos The baseline position to use.
     */
    public fun changeRowBaselinePosition(row: Int, pos: GtkBaselinePosition) {
        gtk_grid_set_row_baseline_position(grid = gtkGridPtr, row = row, pos = pos)
    }

    /**
     * Fetches the baseline position of the [row].
     * @param row The row index.
     * @return The baseline position if it is set, otherwise the default value `GTK_BASELINE_POSITION_CENTER` is
     * returned.
     */
    public fun fetchRowBaselinePosition(row: Int): GtkBaselinePosition = gtk_grid_get_row_baseline_position(gtkGridPtr, row)
}

public fun gridLayout(gridPtr: CPointer<GtkGrid>? = null, init: Grid.() -> Unit): Grid {
    val grid = Grid(gridPtr)
    grid.init()
    return grid
}
