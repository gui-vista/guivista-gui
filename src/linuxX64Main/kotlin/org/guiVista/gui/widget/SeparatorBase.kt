package org.guiVista.gui.widget

import gtk3.GtkSeparator
import kotlinx.cinterop.CPointer

public actual interface SeparatorBase : WidgetBase {
    public val gtkSeparatorPtr: CPointer<GtkSeparator>?
}
