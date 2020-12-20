package org.guiVista.gui.widget.button

import gtk3.GtkCheckButton
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual interface CheckButtonBase : ToggleButtonBase {
    public val gtkCheckButtonPtr: CPointer<GtkCheckButton>?
        get() = gtkWidgetPtr?.reinterpret()
}
