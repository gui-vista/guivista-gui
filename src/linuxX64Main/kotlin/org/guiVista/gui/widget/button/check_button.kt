package org.guiVista.gui.widget.button

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class CheckButton private constructor(checkButtonPtr: CPointer<GtkCheckButton>?) : CheckButtonBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = checkButtonPtr?.reinterpret()

    public actual companion object {
        public fun fromPointer(checkButtonPtr: CPointer<GtkCheckButton>?): CheckButton = CheckButton(checkButtonPtr)

        public actual fun create(): CheckButton = CheckButton(gtk_check_button_new()?.reinterpret())

        public actual fun fromLabel(label: String): CheckButton =
            CheckButton(gtk_check_button_new_with_label(label)?.reinterpret())

        public actual fun fromMnemonic(label: String): CheckButton =
            CheckButton(gtk_check_button_new_with_mnemonic(label)?.reinterpret())
    }
}

public fun checkButtonWidget(
    checkButtonPtr: CPointer<GtkCheckButton>? = null,
    label: String = "",
    mnemonic: String = "",
    init: CheckButton.() -> Unit = {}
): CheckButton {
    val checkButton = when {
        checkButtonPtr != null -> CheckButton.fromPointer(checkButtonPtr)
        label.isNotEmpty() -> CheckButton.fromLabel(label)
        mnemonic.isNotEmpty() -> CheckButton.fromMnemonic(mnemonic)
        else -> CheckButton.create()
    }
    checkButton.init()
    return checkButton
}
