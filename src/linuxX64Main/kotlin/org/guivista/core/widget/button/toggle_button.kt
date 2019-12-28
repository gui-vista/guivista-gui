package org.guivista.core.widget.button

import gtk3.GtkWidget
import gtk3.gtk_toggle_button_new
import gtk3.gtk_toggle_button_new_with_label
import gtk3.gtk_toggle_button_new_with_mnemonic
import kotlinx.cinterop.CPointer

/** Create buttons which retain their state. */
class ToggleButton(label: String = "", mnemonic: Boolean = false) : ToggleButtonBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        if (label.isNotEmpty() && !mnemonic) gtk_toggle_button_new_with_label(label)
        else if (label.isNotEmpty() && mnemonic) gtk_toggle_button_new_with_mnemonic(label)
        else gtk_toggle_button_new()
}

fun toggleButtonWidget(label: String = "", mnemonic: Boolean = false, init: ToggleButton.() -> Unit): ToggleButton {
    val toggleButton =
        if (label.isNotEmpty() && mnemonic) ToggleButton(label = label, mnemonic = mnemonic)
        else if (label.isNotEmpty() && !mnemonic) ToggleButton(label = label, mnemonic = mnemonic)
        else ToggleButton()
    toggleButton.init()
    return toggleButton
}
