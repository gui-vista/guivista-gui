package org.gui_vista.core.widget.button

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** A widget that emits a signal when clicked on. */
class Button(buttonPtr: CPointer<GtkButton>? = null, label: String = "", mnemonic: Boolean = false) : ButtonBase {
    @Suppress("IfThenToElvis")
    override val gtkWidgetPtr: CPointer<GtkWidget>? = when {
        buttonPtr != null -> buttonPtr.reinterpret()
        label.isNotEmpty() && mnemonic -> gtk_button_new_with_mnemonic(label)
        label.isNotEmpty() && !mnemonic -> gtk_button_new_with_label(label)
        else -> gtk_button_new()
    }
}

fun buttonWidget(
    buttonPtr: CPointer<GtkButton>? = null,
    label: String = "",
    mnemonic: Boolean = false,
    init: Button.() -> Unit
): Button {
    val button = Button(buttonPtr = buttonPtr, label = label, mnemonic = mnemonic)
    button.init()
    return button
}
