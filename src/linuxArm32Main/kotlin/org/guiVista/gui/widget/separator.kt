package org.guiVista.gui.widget

import gtk3.GtkOrientation
import gtk3.GtkSeparator
import gtk3.GtkWidget
import gtk3.gtk_separator_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class Separator(
    ptr: CPointer<GtkSeparator>? = null,
    orientation: GtkOrientation = GtkOrientation.GTK_ORIENTATION_HORIZONTAL
) : SeparatorBase {
    override val gtkSeparatorPtr: CPointer<GtkSeparator>? = ptr ?: gtk_separator_new(orientation)?.reinterpret()
    override val gtkWidgetPtr: CPointer<GtkWidget>?
        get() = gtkSeparatorPtr?.reinterpret()
}

public fun separatorWidget(
    ptr: CPointer<GtkSeparator>? = null,
    orientation: GtkOrientation = GtkOrientation.GTK_ORIENTATION_HORIZONTAL,
    init: Separator.() -> Unit = {}
): Separator {
    val result = Separator(ptr, orientation)
    result.init()
    return result
}
