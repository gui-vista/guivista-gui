package org.guiVista.gui

import org.guiVista.core.ObjectBase
import org.guiVista.core.dataType.SinglyLinkedList
import org.guiVista.gui.widget.WidgetBase

/** Grouping widgets so they request the same size. */
public expect class SizeGroup : ObjectBase {
    /**
     * The list of widgets associated with [SizeGroup]. The list is owned by GTK and should **NOT** be modified!
     */
    public val widgets: SinglyLinkedList

    /**
     * Adds a [widget] to a [SizeGroup]. In the future the requisition of the [widget] will be determined as the
     * maximum of its requisition, and the requisition of the other widgets in the size group. Whether this applies
     * horizontally, vertically, or in both directions depends on the mode of the [SizeGroup].
     *
     * When the widget is destroyed, or no longer referenced elsewhere it will be removed from the [SizeGroup]. Refer
     * to mode property.
     * @param widget The widget to add.
     */
    public infix fun addWidget(widget: WidgetBase)

    /**
     * Removes a widget from a GtkSizeGroup.
     * @param widget The widget to remove.
     * @see removeWidget
     */
    public operator fun minusAssign(widget: WidgetBase)

    /**
     * Adds a [widget] to a [SizeGroup].
     * @param widget The widget to add.
     * @see addWidget
     */
    public operator fun plusAssign(widget: WidgetBase)

    /**
     * Removes a widget from a GtkSizeGroup.
     * @param widget The widget to remove.
     */
    public infix fun removeWidget(widget: WidgetBase)
}
