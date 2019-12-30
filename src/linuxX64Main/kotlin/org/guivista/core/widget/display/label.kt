package org.guivista.core.widget.display

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guivista.core.widget.Widget

/** A widget that displays a small to medium amount of text. */
class Label(text: String) : LabelBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = gtk_label_new(text)
}

fun labelWidget(text: String, init: Label.() -> Unit): Label {
    val lbl = Label(text)
    lbl.init()
    return lbl
}
