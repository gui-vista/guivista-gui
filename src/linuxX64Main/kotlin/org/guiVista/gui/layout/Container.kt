package org.guiVista.gui.layout

import glib2.GType
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.dataType.DoublyLinkedList
import org.guiVista.gui.Adjustment
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase

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
     * Fetches the current focus child widget inside [Container]. This is **not** the currently focused widget. That
     * can be obtained using the [focus][org.guiVista.gui.window.WindowBase.focus] property from
     * [WindowBase][org.guiVista.gui.window.WindowBase].
     */
    var focusChild: WidgetBase?
        get() {
            val tmp = gtk_container_get_focus_child(gtkContainerPtr)
            return if (tmp != null) Widget(tmp) else null
        }
        set(value) = gtk_container_set_focus_child(gtkContainerPtr, value?.gtkWidgetPtr)
    /** The vertical focus adjustment for the [Container]. */
    var focusVAdjustment: Adjustment?
        get() {
            val tmp = gtk_container_get_focus_vadjustment(gtkContainerPtr)
            return if (tmp != null) Adjustment(tmp) else null
        }
        set(value) = gtk_container_set_focus_vadjustment(gtkContainerPtr, value?.gtkAdjustmentPtr)
    /** The horizontal focus adjustment for the [Container]. */
    var focusHAdjustment: Adjustment?
        get() {
            val tmp = gtk_container_get_focus_hadjustment(gtkContainerPtr)
            return if (tmp != null) Adjustment(tmp) else null
        }
        set(value) = gtk_container_set_focus_hadjustment(gtkContainerPtr, value?.gtkAdjustmentPtr)

    /**
     * Gets the type of the children supported by the container.
     * @return The data type, or *GType.G_TYPE_NONE* if no more children can be added.
     */
    fun childType(): GType = gtk_container_child_type(gtkContainerPtr)

    /**
     * Adds [widget] to [container][Container]. Typically used for simple containers such as
     * [window][org.guiVista.gui.window.WindowBase], GtkFrame, or
     * [button][org.guiVista.gui.widget.button.ButtonBase]. For more complicated layout containers such as [Box], or
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

    /** Emits the *check-resize* signal on the [Container]. */
    fun checkResize() {
        gtk_container_check_resize(gtkContainerPtr)
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
