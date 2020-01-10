package org.guiVista.core.widget.display

import gtk3.GtkLabel
import gtk3.GtkWidget
import gtk3.gtk_label_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** A widget that displays a small to medium amount of text. */
class Label(labelPtr: CPointer<GtkLabel>? = null, text: String) : LabelBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = labelPtr?.reinterpret() ?: gtk_label_new(text)
}

fun labelWidget(labelPtr: CPointer<GtkLabel>? = null, text: String, init: Label.() -> Unit): Label {
    val lbl = Label(labelPtr, text)
    lbl.init()
    return lbl
}
