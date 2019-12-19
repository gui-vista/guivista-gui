package org.guivista.core.layout

import gtk3.GtkContainer
import gtk3.gtk_container_get_border_width
import gtk3.gtk_container_set_border_width
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.widget.Widget

/** Base class for widgets which contain other widgets. Deals with basic layout. */
interface Container : Widget {
    val containerPtr: CPointer<GtkContainer>?
        get() = widgetPtr?.reinterpret()
    /** The width of the empty border outside the container's children. */
    var borderWidth: UInt
        set(value) = gtk_container_set_border_width(containerPtr, value)
        get() = gtk_container_get_border_width(containerPtr)
}