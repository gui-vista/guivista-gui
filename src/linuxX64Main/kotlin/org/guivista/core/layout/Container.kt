package org.guivista.core.layout

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.data_type.DoublyLinkedList
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
    /** Gets the [container’s][Container] non internal children. */
    val children: DoublyLinkedList
        get() = DoublyLinkedList(gtk_container_get_children(gtkContainerPtr))

    /**
     * Adds [widget] to [container][Container]. Typically used for simple containers such as
     * [window][org.guivista.core.window.WindowBase], GtkFrame, or
     * [button][org.guivista.core.widget.button.ButtonBase]. For more complicated layout containers such as [Box], or
     * [Grid], this function will pick default packing parameters that may not be correct. So consider functions such
     * as `gtk_box_pack_start()`, and `gtk_grid_attach()` as an alternative to [add] in those cases. A widget may be
     * added to only one container at a time. You can’t place the same widget inside two different containers.
     *
     * Note that some containers such as GtkScrolledWindow, or GtkListBox **may** add intermediate children between the
     * added widget, and the container.
     * @param widget A widget to be placed inside container.
     */
    fun add(widget: WidgetBase) {
        gtk_container_add(gtkContainerPtr, widget.gtkWidgetPtr)
    }

    /**
     * Removes [widget] from [container][Container]. The [widget] must be inside the container. Note that container
     * will own a reference to [widget], and that this may be the last reference held. So removing a [widget] from its
     * container can destroy that [widget]. If you want to use the [widget] again you need to add a reference to it,
     * **before** removing it from a container using `g_object_ref()`. If you don’t want to use [widget] again it’s
     * usually more efficient to simply destroy it directly using `gtk_widget_destroy()` since this will remove it from
     * the container, and help break any circular reference count cycles.
     */
    fun remove(widget: WidgetBase) {
        gtk_container_remove(gtkContainerPtr, widget.gtkWidgetPtr)
    }

    /**
     * Adds [widget] to [container][Container].
     * @see add
     */
    operator fun plusAssign(widget: WidgetBase) {
        add(widget)
    }

    /**
     * Removes [widget] from [container][Container].
     * @see remove
     */
    operator fun minusAssign(widget: WidgetBase) {
        remove(widget)
    }
}
