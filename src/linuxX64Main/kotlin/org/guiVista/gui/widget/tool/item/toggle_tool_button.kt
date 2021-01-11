package org.guiVista.gui.widget.tool.item

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class ToggleToolButton(toggleToolButtonPtr: CPointer<GtkToggleToolButton>? = null) : ToggleToolButtonBase {
    override val gtkToolItemPtr: CPointer<GtkToolItem>? =
        toggleToolButtonPtr?.reinterpret() ?: gtk_toggle_tool_button_new()
    override val gtkToggleToolButtonPtr: CPointer<GtkToggleToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    override val gtkToolButtonPtr: CPointer<GtkToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
}

public fun toggleToolButtonWidget(
    toggleToolButtonPtr: CPointer<GtkToggleToolButton>? = null,
    init: ToggleToolButton.() -> Unit = {}
): ToggleToolButton {
    val button = ToggleToolButton(toggleToolButtonPtr)
    button.init()
    return button
}
