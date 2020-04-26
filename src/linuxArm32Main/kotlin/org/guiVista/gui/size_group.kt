package org.guiVista.gui

import glib2.g_object_unref
import gtk3.*
import kotlinx.cinterop.CPointer
import org.guiVista.core.ObjectBase
import org.guiVista.core.dataType.SinglyLinkedList
import org.guiVista.gui.widget.WidgetBase

actual class SizeGroup(sizeGroupPtr: CPointer<GtkSizeGroup>? = null,
                       mode: GtkSizeGroupMode = GtkSizeGroupMode.GTK_SIZE_GROUP_NONE) : ObjectBase {
    val gtkSizeGroupPtr: CPointer<GtkSizeGroup>? = sizeGroupPtr ?: gtk_size_group_new(mode)

    /**
     * The directions in which the size group affects the requested sizes of its component widgets. Default value is
     * *GtkSizeGroupMode.GTK_SIZE_GROUP_HORIZONTAL*
     */
    var mode: GtkSizeGroupMode
        get() = gtk_size_group_get_mode(gtkSizeGroupPtr)
        set(value) = gtk_size_group_set_mode(gtkSizeGroupPtr, value)
    actual val widgets: SinglyLinkedList
        get() = SinglyLinkedList(gtk_size_group_get_widgets(gtkSizeGroupPtr))

    actual operator fun plusAssign(widget: WidgetBase) {
        addWidget(widget)
    }

    actual infix fun addWidget(widget: WidgetBase) {
        gtk_size_group_add_widget(gtkSizeGroupPtr, widget.gtkWidgetPtr)
    }

    actual operator fun minusAssign(widget: WidgetBase) {
        removeWidget(widget)
    }

    actual infix fun removeWidget(widget: WidgetBase) {
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
