package org.guiVista.gui.widget.button

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class Button private constructor(buttonPtr: CPointer<GtkButton>?) : ButtonBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = buttonPtr?.reinterpret()

    public actual companion object {
        public fun fromPointer(buttonPtr: CPointer<GtkButton>?): Button = Button(buttonPtr)

        public actual fun create(): Button = Button(gtk_button_new()?.reinterpret())

        public actual fun fromLabel(label: String): Button = Button(gtk_button_new_with_label(label)?.reinterpret())

        public actual fun fromMnemonic(label: String): Button =
            Button(gtk_button_new_with_mnemonic(label)?.reinterpret())
    }
}

public fun buttonWidget(
    buttonPtr: CPointer<GtkButton>? = null,
    label: String = "",
    mnemonic: String = "",
    init: Button.() -> Unit = {}
): Button {
    val button = when {
        buttonPtr != null -> Button.fromPointer(buttonPtr)
        label.isNotEmpty() -> Button.fromLabel(label)
        mnemonic.isNotEmpty() -> Button.fromMnemonic(mnemonic)
        else -> Button.create()
    }
    button.init()
    return button
}
