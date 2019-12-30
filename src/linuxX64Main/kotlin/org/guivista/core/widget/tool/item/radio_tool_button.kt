package org.guivista.core.widget.tool.item

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

class RadioToolButton(group: RadioToolButton? = null) : ToggleToolButtonBase {
    override val gtkToolItemPtr: CPointer<GtkToolItem>? =
        if (group != null) gtk_radio_tool_button_new_from_widget(group.gtkRadioToolButton)
        else gtk_radio_tool_button_new(null)
    override val gtkToolButtonPtr: CPointer<GtkToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    override val gtkToggleToolButtonPtr: CPointer<GtkToggleToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    val gtkRadioToolButton: CPointer<GtkRadioToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    override val gtkWidgetPtr: CPointer<GtkWidget>? = null
}

fun radioToolButtonWidget(group: RadioToolButton? = null, init: RadioToolButton.() -> Unit): RadioToolButton {
    val button = RadioToolButton(group)
    button.init()
    return button
}
