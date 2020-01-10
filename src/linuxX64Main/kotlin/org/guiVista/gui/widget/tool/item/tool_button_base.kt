package org.guiVista.gui.widget.tool.item

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import org.guiVista.gui.connectGSignal
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase

private const val CLICKED_SIGNAL = "clicked"

/** Base interface for tool button objects. */
interface ToolButtonBase : ToolItemBase {
    val gtkToolButtonPtr: CPointer<GtkToolButton>?
    /**
     * The name of the themed icon displayed on the item. This property only has an effect if not overridden by
     * [labelWidget], or [iconWidget] properties. Default value is *""* (an empty String).
     */
    var iconName: String
        get() = gtk_tool_button_get_icon_name(gtkToolButtonPtr)?.toKString() ?: ""
        set(value) = gtk_tool_button_set_icon_name(gtkToolButtonPtr, value)
    /** Icon widget to display in the item. */
    var iconWidget: WidgetBase?
        get() {
            val tmp = gtk_tool_button_get_icon_widget(gtkToolButtonPtr)
            return if (tmp != null) Widget(tmp) else null
        }
        set(value) = gtk_tool_button_set_icon_widget(gtkToolButtonPtr, value?.gtkWidgetPtr)
    /** Text to show in the item. Default value is *""* (an empty String). */
    var label: String
        get() = gtk_tool_button_get_label(gtkToolButtonPtr)?.toKString() ?: ""
        set(value) = gtk_tool_button_set_label(gtkToolButtonPtr, value)
    /** Widget to use as the item label. */
    var labelWidget: WidgetBase?
        get() {
            val tmp = gtk_tool_button_get_label_widget(gtkToolButtonPtr)
            return if (tmp != null) Widget(tmp) else null
        }
        set(value) = gtk_tool_button_set_label_widget(gtkToolButtonPtr, value?.gtkWidgetPtr)
    /**
     * If set an underline in the [label] property indicates that the next character should be used for the mnemonic
     * accelerator key in the overflow menu. Default value is *false*.
     */
    var useUnderline: Boolean
        get() = gtk_tool_button_get_use_underline(gtkToolButtonPtr) == TRUE
        set(value) = gtk_tool_button_set_use_underline(gtkToolButtonPtr, if (value) TRUE else FALSE)

    /**
     * Connects the *clicked* signal to a [slot] on a tool button. This signal is used when a user has clicked on the
     * tool button.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectClickedSignal(slot: CPointer<ClickedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkToolButtonPtr, signal = CLICKED_SIGNAL, slot = slot, data = userData)
}

/**
 * The event handler for the *clicked* signal. Arguments:
 * 1. toolButton: CPointer<GtkToolButton>
 * 2. userData: gpointer
 */
typealias ClickedSlot = CFunction<(toolButton: CPointer<GtkToolButton>?, userData: gpointer) -> Unit>
