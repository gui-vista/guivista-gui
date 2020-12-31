package org.guiVista.gui.dialog

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase
import org.guiVista.gui.window.WindowBase

public actual class MessageDialog private constructor(
    messageDialogPtr: CPointer<GtkMessageDialog>? = null,
    parent: WindowBase? = null,
    flags: UInt = 0u,
    type: GtkMessageType = GtkMessageType.GTK_MESSAGE_INFO,
    buttons: GtkButtonsType = GtkButtonsType.GTK_BUTTONS_CLOSE,
    messageFormat: String? = null,
    useMarkup: Boolean = false
) : DialogBase {
    public companion object {
        public fun create(
            parent: WindowBase,
            type: GtkMessageType,
            flags: UInt = GTK_DIALOG_MODAL,
            buttons: GtkButtonsType = GtkButtonsType.GTK_BUTTONS_CLOSE,
            messageFormat: String? = null
        ): MessageDialog = MessageDialog(
            parent = parent,
            flags = flags,
            type = type,
            buttons = buttons,
            messageFormat = messageFormat
        )

        public fun createWithMarkup(
            parent: WindowBase,
            type: GtkMessageType,
            flags: UInt = GTK_DIALOG_MODAL,
            buttons: GtkButtonsType = GtkButtonsType.GTK_BUTTONS_CLOSE,
            messageFormat: String? = null
        ): MessageDialog = MessageDialog(
            parent = parent,
            flags = flags,
            type = type,
            buttons = buttons,
            messageFormat = messageFormat,
            useMarkup = true
        )
    }

    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        if (messageDialogPtr == null && !useMarkup) {
            gtk_message_dialog_new(
                parent = parent?.gtkWindowPtr,
                flags = flags,
                type = type,
                buttons = buttons,
                message_format = messageFormat
            )
        } else if (messageDialogPtr == null && useMarkup) {
            gtk_message_dialog_new_with_markup(
                parent = parent?.gtkWindowPtr,
                flags = flags,
                type = type,
                buttons = buttons,
                message_format = messageFormat
            )
        } else {
            messageDialogPtr?.reinterpret()
        }
    public val gtkMessageDialogPtr: CPointer<GtkMessageDialog>?
        get() = gtkWidgetPtr?.reinterpret()

    public actual val messageArea: WidgetBase?
        get() {
            val ptr = gtk_message_dialog_get_message_area(gtkMessageDialogPtr)
            return if (ptr != null) Widget(ptr) else null
        }

    public actual infix fun changeMarkup(str: String) {
        gtk_message_dialog_set_markup(gtkMessageDialogPtr, str)
    }
}
