package org.guivista.core.widget.button

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

open class Button(label: String = "", mnemonic: Boolean = false) : ButtonBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        if (label.isNotEmpty() && mnemonic) gtk_button_new_with_mnemonic(label)
        else if (label.isNotEmpty() && !mnemonic) gtk_button_new_with_label(label)
        else gtk_button_new()
    override val gtkButtonPtr: CPointer<GtkButton>?
        get() = gtkWidgetPtr?.reinterpret()
}

fun buttonWidget(label: String = "", mnemonic: Boolean = false, init: Button.() -> Unit): Button {
    val button =
        if (label.isNotEmpty() && mnemonic) Button(label = label, mnemonic = mnemonic)
        else if (label.isNotEmpty() && !mnemonic) Button(label = label, mnemonic = mnemonic)
        else Button()
    button.init()
    return button
}
