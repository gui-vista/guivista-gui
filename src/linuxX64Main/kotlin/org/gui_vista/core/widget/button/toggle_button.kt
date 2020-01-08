package org.gui_vista.core.widget.button

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** Create buttons which retain their state. */
class ToggleButton(
    toggleButtonPtr: CPointer<GtkToggleButton>? = null,
    label: String = "",
    mnemonic: Boolean = false
) : ToggleButtonBase {
    @Suppress("IfThenToElvis")
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        if (toggleButtonPtr != null) toggleButtonPtr.reinterpret()
        else if (label.isNotEmpty() && !mnemonic) gtk_toggle_button_new_with_label(label)
        else if (label.isNotEmpty() && mnemonic) gtk_toggle_button_new_with_mnemonic(label)
        else gtk_toggle_button_new()
}

fun toggleButtonWidget(
    toggleButtonPtr: CPointer<GtkToggleButton>? = null,
    label: String = "",
    mnemonic: Boolean = false,
    init: ToggleButton.() -> Unit
): ToggleButton {
    val toggleButton = ToggleButton(toggleButtonPtr = toggleButtonPtr, label = label, mnemonic = mnemonic)
    toggleButton.init()
    return toggleButton
}
