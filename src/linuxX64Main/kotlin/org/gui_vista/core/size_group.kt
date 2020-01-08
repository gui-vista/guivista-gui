package org.gui_vista.core

import gtk3.*
import kotlinx.cinterop.CPointer
import org.gui_vista.core.data_type.SinglyLinkedList
import org.gui_vista.core.widget.Widget

/** Grouping widgets so they request the same size. */
class SizeGroup(sizeGroupPtr: CPointer<GtkSizeGroup>? = null,
                mode: GtkSizeGroupMode = GtkSizeGroupMode.GTK_SIZE_GROUP_NONE) : ObjectBase {
    val gtkSizeGroupPtr: CPointer<GtkSizeGroup>? = sizeGroupPtr ?: gtk_size_group_new(mode)
    /**
     * The directions in which the size group affects the requested sizes of its component widgets. Default value is
     * *GtkSizeGroupMode.GTK_SIZE_GROUP_HORIZONTAL*
     */
    var mode: GtkSizeGroupMode
        get() = gtk_size_group_get_mode(gtkSizeGroupPtr)
        set(value) = gtk_size_group_set_mode(gtkSizeGroupPtr, value)
    /**
     * The list of widgets associated with [SizeGroup]. The list is owned by GTK and should **NOT** be modified!
     */
    val widgets: SinglyLinkedList
        get() = SinglyLinkedList(gtk_size_group_get_widgets(gtkSizeGroupPtr))

    /**
     * Adds a [widget] to a [SizeGroup].
     * @param widget The widget to add.
     * @see addWidget
     */
    operator fun plusAssign(widget: Widget) {
        addWidget(widget)
    }

    /**
     * Adds a [widget] to a [SizeGroup]. In the future the requisition of the [widget] will be determined as the
     * maximum of its requisition, and the requisition of the other widgets in the size group. Whether this applies
     * horizontally, vertically, or in both directions depends on the mode of the [SizeGroup].
     *
     * When the widget is destroyed, or no longer referenced elsewhere it will be removed from the [SizeGroup].
     * @param widget The widget to add.
     * @see mode
     */
    fun addWidget(widget: Widget) {
        gtk_size_group_add_widget(gtkSizeGroupPtr, widget.gtkWidgetPtr)
    }

    /**
     * Removes a widget from a GtkSizeGroup.
     * @param widget The widget to remove.
     * @see removeWidget
     */
    operator fun minusAssign(widget: Widget) {
        removeWidget(widget)
    }

    /**
     * Removes a widget from a GtkSizeGroup.
     * @param widget The widget to remove.
     */
    fun removeWidget(widget: Widget) {
        gtk_size_group_remove_widget(gtkSizeGroupPtr, widget.gtkWidgetPtr)
    }

    override fun close() {
        g_object_unref(gtkSizeGroupPtr)
    }
}

fun createSizeGroup(
    sizeGroupPtr: CPointer<GtkSizeGroup>? = null,
    mode: GtkSizeGroupMode = GtkSizeGroupMode.GTK_SIZE_GROUP_NONE,
    init: SizeGroup.() -> Unit
): SizeGroup {
    val sizeGroup = SizeGroup(sizeGroupPtr, mode)
    sizeGroup.init()
    return sizeGroup
}
