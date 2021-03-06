package org.guiVista.gui.layout

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase

public actual interface BoxBase : Container {
    public val gtkBoxPtr: CPointer<GtkBox>?
        get() = gtkWidgetPtr?.reinterpret()

    /**
     * A child widget that will be centered with respect to the full width of the box, even if the children at either
     * side take up different amounts of space.
     */
    public var centerWidget: WidgetBase?
        get() {
            val ptr = gtk_box_get_center_widget(gtkBoxPtr)
            return if (ptr != null) Widget(ptr) else null
        }
        set(value) = gtk_box_set_center_widget(gtkBoxPtr, value?.gtkWidgetPtr)

    /** The amount of space between children. Default value is *0*. */
    public var spacing: Int
        set(value) = gtk_box_set_spacing(gtkBoxPtr, value)
        get() = gtk_box_get_spacing(gtkBoxPtr)

    /** If set to *true* then all the children are the same size. Default value is *false*. */
    public var homogeneous: Boolean
        set(value) = gtk_box_set_homogeneous(gtkBoxPtr, if (value) TRUE else FALSE)
        get() = gtk_box_get_homogeneous(gtkBoxPtr) == TRUE

    /**
     * The position of the baseline aligned widgets if extra space is available. Default value is
     * *GtkBaselinePosition.GTK_BASELINE_POSITION_CENTER*.
     */
    public var baselinePosition: GtkBaselinePosition
        set(value) = gtk_box_set_baseline_position(gtkBoxPtr, value)
        get() = gtk_box_get_baseline_position(gtkBoxPtr)

    /**
     * Moves child to a new position in the list of box children. The list contains widgets packed `GTK_PACK_START` as
     * well as widgets packed `GTK_PACK_END`, in the order that these widgets were added to box. A widget’s position
     * in the box children list determines where the widget is packed into box. A child widget at some position in the
     * list will be packed just after all other widgets of the same packing type that appear earlier in the list.
     * @param child The child to move.
     * @param pos The new position for the child in the box starting from 0. If negative this indicates the end of the
     * list.
     */
    public fun reorderChild(child: WidgetBase, pos: Int) {
        gtk_box_reorder_child(box = gtkBoxPtr, child = child.gtkWidgetPtr, position = pos)
    }

    /**
     * With a vertical box a [child] is added to the *top* of the GTK Box. If the box is horizontal then a [child] is
     * added to the *left* of the GTK Box.
     * @param child The widget to prepend.
     * @param fill If *true* then the added [child] will be sized to use all of the available space.
     * @param expand If *true* then the added [child] will be resized every time the box is resized.
     * @param padding The amount of padding to use for the [child] which is in pixels. By default no padding is used.
     */
    public fun prependChild(
        child: WidgetBase,
        fill: Boolean = true,
        expand: Boolean = false,
        padding: UInt = 0u
    ): Unit = gtk_box_pack_start(
        box = gtkBoxPtr,
        child = child.gtkWidgetPtr,
        expand = if (expand) TRUE else FALSE,
        fill = if (fill) TRUE else FALSE,
        padding = padding
    )

    /**
     * With a vertical box a [child] is added to the *bottom* of the GTK Box. If the box is horizontal then a [child]
     * is added to the *right* of the GTK Box.
     * @param child The widget to append.
     * @param fill If *true* then the added [child] will be sized to use all of the available space.
     * @param expand If *true* then the added [child] will be resized every time the box is resized.
     * @param padding The amount of padding to use for the [child] which is in pixels. By default no padding is used.
     */
    public fun appendChild(
        child: WidgetBase,
        fill: Boolean = true,
        expand: Boolean = false,
        padding: UInt = 0u
    ): Unit = gtk_box_pack_end(
        box = gtkBoxPtr,
        child = child.gtkWidgetPtr,
        expand = if (expand) TRUE else FALSE,
        fill = if (fill) TRUE else FALSE,
        padding = padding
    )
}
