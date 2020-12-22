package org.guiVista.gui

import glib2.FALSE
import glib2.TRUE
import glib2.g_object_unref
import gtk3.*
import kotlinx.cinterop.CPointer
import org.guiVista.core.ObjectBase
import org.guiVista.core.dataType.SinglyLinkedList
import org.guiVista.gui.widget.WidgetBase

public actual class SizeGroup(sizeGroupPtr: CPointer<GtkSizeGroup>? = null,
                              mode: GtkSizeGroupMode = GtkSizeGroupMode.GTK_SIZE_GROUP_NONE) : ObjectBase {
    public val gtkSizeGroupPtr: CPointer<GtkSizeGroup>? = sizeGroupPtr ?: gtk_size_group_new(mode)

    /**
     * The directions in which the size group affects the requested sizes of its component widgets. Default value is
     * *GtkSizeGroupMode.GTK_SIZE_GROUP_HORIZONTAL*
     */
    public var mode: GtkSizeGroupMode
        get() = gtk_size_group_get_mode(gtkSizeGroupPtr)
        set(value) = gtk_size_group_set_mode(gtkSizeGroupPtr, value)
    public actual var ignoreHidden: Boolean
        get() = gtk_size_group_get_ignore_hidden(gtkSizeGroupPtr) == TRUE
        set(value) = gtk_size_group_set_ignore_hidden(gtkSizeGroupPtr, if (value) TRUE else FALSE)
    public actual val widgets: SinglyLinkedList
        get() = SinglyLinkedList(gtk_size_group_get_widgets(gtkSizeGroupPtr))

    public actual operator fun plusAssign(widget: WidgetBase) {
        addWidget(widget)
    }

    public actual infix fun addWidget(widget: WidgetBase) {
        gtk_size_group_add_widget(gtkSizeGroupPtr, widget.gtkWidgetPtr)
    }

    public actual operator fun minusAssign(widget: WidgetBase) {
        removeWidget(widget)
    }

    public actual infix fun removeWidget(widget: WidgetBase) {
        gtk_size_group_remove_widget(gtkSizeGroupPtr, widget.gtkWidgetPtr)
    }

    override fun close() {
        g_object_unref(gtkSizeGroupPtr)
    }
}

public fun createSizeGroup(
    sizeGroupPtr: CPointer<GtkSizeGroup>? = null,
    mode: GtkSizeGroupMode = GtkSizeGroupMode.GTK_SIZE_GROUP_NONE,
    init: SizeGroup.() -> Unit
): SizeGroup {
    val sizeGroup = SizeGroup(sizeGroupPtr, mode)
    sizeGroup.init()
    return sizeGroup
}
