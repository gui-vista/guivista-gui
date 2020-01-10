package org.guiVista.core.widget

import gtk3.GtkWidget
import kotlinx.cinterop.CPointer

/** Represents an existing widget (control). */
class Widget(widgetPtr: CPointer<GtkWidget>) : WidgetBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = widgetPtr
}
