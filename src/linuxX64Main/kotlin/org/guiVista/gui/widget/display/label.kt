package org.guiVista.gui.widget.display

import gtk3.GtkLabel
import gtk3.GtkWidget
import gtk3.gtk_label_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class Label(labelPtr: CPointer<GtkLabel>? = null, text: String) : LabelBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = labelPtr?.reinterpret() ?: gtk_label_new(text)
}

public fun labelWidget(labelPtr: CPointer<GtkLabel>? = null, text: String, init: Label.() -> Unit = {}): Label {
    val lbl = Label(labelPtr, text)
    lbl.init()
    return lbl
}
