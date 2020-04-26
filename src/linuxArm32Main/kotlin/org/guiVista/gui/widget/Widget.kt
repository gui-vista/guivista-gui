package org.guiVista.gui.widget

import gtk3.GtkWidget
import kotlinx.cinterop.CPointer

actual class Widget(widgetPtr: CPointer<GtkWidget>) : WidgetBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = widgetPtr
}
