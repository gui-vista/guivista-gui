package org.guiVista.gui.widget.tool.item

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase

public actual interface ToolButtonBase : ToolItemBase {
    public val gtkToolButtonPtr: CPointer<GtkToolButton>?

    /**
     * The name of the themed icon displayed on the item. This property only has an effect if not overridden by
     * [labelWidget], or [iconWidget] properties. Default value is *""* (an empty String).
     */
    public var iconName: String
        get() = gtk_tool_button_get_icon_name(gtkToolButtonPtr)?.toKString() ?: ""
        set(value) = gtk_tool_button_set_icon_name(gtkToolButtonPtr, value)

    /** Icon widget to display in the item. */
    public var iconWidget: WidgetBase?
        get() {
            val tmp = gtk_tool_button_get_icon_widget(gtkToolButtonPtr)
            return if (tmp != null) Widget(tmp) else null
        }
        set(value) = gtk_tool_button_set_icon_widget(gtkToolButtonPtr, value?.gtkWidgetPtr)

    /** Text to show in the item. Default value is *""* (an empty String). */
    public var label: String
        get() = gtk_tool_button_get_label(gtkToolButtonPtr)?.toKString() ?: ""
        set(value) = gtk_tool_button_set_label(gtkToolButtonPtr, value)

    /** Widget to use as the item label. */
    public var labelWidget: WidgetBase?
        get() {
            val tmp = gtk_tool_button_get_label_widget(gtkToolButtonPtr)
            return if (tmp != null) Widget(tmp) else null
        }
        set(value) = gtk_tool_button_set_label_widget(gtkToolButtonPtr, value?.gtkWidgetPtr)

    /**
     * If set an underline in the [label] property indicates that the next character should be used for the mnemonic
     * accelerator key in the overflow menu. Default value is *false*.
     */
    public var useUnderline: Boolean
        get() = gtk_tool_button_get_use_underline(gtkToolButtonPtr) == TRUE
        set(value) = gtk_tool_button_set_use_underline(gtkToolButtonPtr, if (value) TRUE else FALSE)

    /**
     * Connects the *clicked* event to a [handler] on a tool button. This event is used when a user has clicked on the
     * tool button.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectClickedEvent(handler: CPointer<ClickedHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkToolButtonPtr, signal = ToolButtonBaseEvent.clicked, slot = handler, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkToolButtonPtr, handlerId)
    }
}

/**
 * The event handler for the *clicked* event. Arguments:
 * 1. toolButton: CPointer<GtkToolButton>
 * 2. userData: gpointer
 */
public typealias ClickedHandler = CFunction<(toolButton: CPointer<GtkToolButton>?, userData: gpointer) -> Unit>
