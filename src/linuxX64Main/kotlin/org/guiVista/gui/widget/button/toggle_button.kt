package org.guiVista.gui.widget.button

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class ToggleButton private constructor(toggleButtonPtr: CPointer<GtkToggleButton>?) : ToggleButtonBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = toggleButtonPtr?.reinterpret()

    public actual companion object {
        public fun fromPointer(toggleButtonPtr: CPointer<GtkToggleButton>?): ToggleButton =
            ToggleButton(toggleButtonPtr)

        public actual fun create(): ToggleButton =
            ToggleButton(gtk_toggle_button_new()?.reinterpret())

        public actual fun fromLabel(label: String): ToggleButton =
            ToggleButton(gtk_toggle_button_new_with_label(label)?.reinterpret())

        public actual fun fromMnemonic(label: String): ToggleButton =
            ToggleButton(gtk_toggle_button_new_with_mnemonic(label)?.reinterpret())
    }
}

public fun toggleButtonWidget(
    toggleButtonPtr: CPointer<GtkToggleButton>? = null,
    label: String = "",
    mnemonic: String = "",
    init: ToggleButton.() -> Unit = {}
): ToggleButton {
    val toggleButton = when {
        toggleButtonPtr != null -> ToggleButton.fromPointer(toggleButtonPtr)
        label.isNotEmpty() -> ToggleButton.fromLabel(label)
        mnemonic.isNotEmpty() -> ToggleButton.fromMnemonic(mnemonic)
        else -> ToggleButton.create()
    }
    toggleButton.init()
    return toggleButton
}
