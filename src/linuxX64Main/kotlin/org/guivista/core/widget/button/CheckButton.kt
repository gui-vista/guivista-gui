package org.guivista.core.widget.button

import gtk3.GtkWidget
import gtk3.gtk_check_button_new
import gtk3.gtk_check_button_new_with_label
import gtk3.gtk_check_button_new_with_mnemonic
import kotlinx.cinterop.CPointer

/** Create widgets with a discrete toggle button. */
class CheckButton(label: String = "", mnemonic: Boolean = false) : CheckButtonBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        if (label.isNotEmpty() && mnemonic) gtk_check_button_new_with_mnemonic(label)
        else if (label.isNotEmpty() && !mnemonic) gtk_check_button_new_with_label(label)
        else gtk_check_button_new()
}
