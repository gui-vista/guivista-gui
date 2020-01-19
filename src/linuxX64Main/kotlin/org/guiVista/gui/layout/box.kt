package org.guiVista.gui.layout

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** A container for packing widgets in a single row or column. */
class Box(boxPtr: CPointer<GtkBox>? = null, orientation: GtkOrientation = GtkOrientation.GTK_ORIENTATION_HORIZONTAL) :
    BoxBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = boxPtr?.reinterpret() ?: gtk_box_new(orientation, 0)
}

fun boxLayout(
    boxPtr: CPointer<GtkBox>? = null,
    orientation: GtkOrientation = GtkOrientation.GTK_ORIENTATION_HORIZONTAL,
    init: Box.() -> Unit
): Box {
    val box = Box(boxPtr, orientation)
    box.init()
    return box
}
