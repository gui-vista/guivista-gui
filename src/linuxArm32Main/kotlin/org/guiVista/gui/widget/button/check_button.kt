package org.guiVista.gui.widget.button

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class CheckButton(
    checkButtonPtr: CPointer<GtkCheckButton>? = null,
    label: String = "",
    mnemonic: Boolean = false
) : CheckButtonBase {
    @Suppress("IfThenToElvis")
    override val gtkWidgetPtr: CPointer<GtkWidget>? = when {
        checkButtonPtr != null -> checkButtonPtr.reinterpret()
        label.isNotEmpty() && mnemonic -> gtk_check_button_new_with_mnemonic(label)
        label.isNotEmpty() && !mnemonic -> gtk_check_button_new_with_label(label)
        else -> gtk_check_button_new()
    }
}

public fun checkButtonWidget(
    checkButtonPtr: CPointer<GtkCheckButton>? = null,
    label: String = "",
    mnemonic: Boolean = false,
    init: CheckButton.() -> Unit = {}
): CheckButton {
    val checkButton = CheckButton(checkButtonPtr = checkButtonPtr, label = label, mnemonic = mnemonic)
    checkButton.init()
    return checkButton
}
