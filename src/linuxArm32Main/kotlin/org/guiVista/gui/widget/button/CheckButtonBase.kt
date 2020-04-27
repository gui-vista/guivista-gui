package org.guiVista.gui.widget.button

import gtk3.GtkCheckButton
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

actual interface CheckButtonBase : ToggleButtonBase {
    val gtkCheckButtonPtr: CPointer<GtkCheckButton>?
        get() = gtkWidgetPtr?.reinterpret()
}
