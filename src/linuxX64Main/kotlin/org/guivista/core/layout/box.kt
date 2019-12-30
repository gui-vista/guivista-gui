package org.guivista.core.layout

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.widget.Widget

/** A container for packing widgets in a single row or column. */
class Box(orientation: GtkOrientation = GtkOrientation.GTK_ORIENTATION_HORIZONTAL) : Container {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = gtk_box_new(orientation, 0)
}

fun boxLayout(orientation: GtkOrientation = GtkOrientation.GTK_ORIENTATION_HORIZONTAL, init: Box.() -> Unit): Box {
    val box = Box(orientation)
    box.init()
    return box
}
