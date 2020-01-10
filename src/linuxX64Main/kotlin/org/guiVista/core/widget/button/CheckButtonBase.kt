package org.guiVista.core.widget.button

import gtk3.GtkCheckButton
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** Base interface for check button objects. */
interface CheckButtonBase : ToggleButtonBase {
    val gtkCheckButtonPtr: CPointer<GtkCheckButton>?
        get() = gtkWidgetPtr?.reinterpret()
}
