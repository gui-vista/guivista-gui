package org.guivista.core.layout

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.widget.WidgetBase

/** Base class for widgets which contain other widgets. Deals with basic layout. */
interface Container : WidgetBase {
    val gtkContainerPtr: CPointer<GtkContainer>?
        get() = gtkWidgetPtr?.reinterpret()
    /** The width of the empty border outside the container's children. Default value is *0*. */
    var borderWidth: UInt
        set(value) = gtk_container_set_border_width(gtkContainerPtr, value)
        get() = gtk_container_get_border_width(gtkContainerPtr)
    /** Specify how resize events are handled. Default value is *GtkResizeMode.GTK_RESIZE_PARENT*. */
    var resizeMode: GtkResizeMode
        get() = gtk_container_get_resize_mode(gtkContainerPtr)
        set(value) = gtk_container_set_resize_mode(gtkContainerPtr, value)
}
