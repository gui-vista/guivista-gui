package org.guiVista.gui.widget.tool.item

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.dataType.SinglyLinkedList

public actual class RadioToolButton(radioToolButtonPtr: CPointer<GtkRadioToolButton>? = null,
                                    group: RadioToolButton? = null) : ToggleToolButtonBase {
    @Suppress("IfThenToElvis")
    override val gtkToolItemPtr: CPointer<GtkToolItem>? = when {
        radioToolButtonPtr != null -> radioToolButtonPtr.reinterpret()
        group != null -> gtk_radio_tool_button_new_from_widget(group.gtkRadioToolButtonPtr)
        else -> gtk_radio_tool_button_new(null)
    }
    override val gtkToolButtonPtr: CPointer<GtkToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    override val gtkToggleToolButtonPtr: CPointer<GtkToggleToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    public val gtkRadioToolButtonPtr: CPointer<GtkRadioToolButton>?
        get() = gtkToolItemPtr?.reinterpret()
    public actual var group: SinglyLinkedList?
        get() {
            val ptr = gtk_radio_tool_button_get_group(gtkRadioToolButtonPtr)
            return if (ptr != null) SinglyLinkedList(ptr) else null
        }
        set(value) = gtk_radio_tool_button_set_group(gtkRadioToolButtonPtr, value?.gSListPtr)
}

public fun radioToolButtonWidget(
    radioToolButtonPtr: CPointer<GtkRadioToolButton>? = null,
    group: RadioToolButton? = null,
    init: RadioToolButton.() -> Unit = {}
): RadioToolButton {
    val button = RadioToolButton(radioToolButtonPtr, group)
    button.init()
    return button
}
