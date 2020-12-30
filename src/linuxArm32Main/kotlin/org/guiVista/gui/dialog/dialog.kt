package org.guiVista.gui.dialog

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase
import org.guiVista.gui.window.WindowBase

private const val CLOSE_SIGNAL = "close"
private const val RESPONSE_SIGNAL = "response"

public actual class Dialog(dialogPtr: CPointer<GtkDialog>? = null) : DialogBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = dialogPtr?.reinterpret() ?: gtk_dialog_new()
    override val headerBar: WidgetBase?
        get() {
            val ptr = gtk_dialog_get_header_bar(gtkDialogPtr)
            return if (ptr != null) Widget(ptr) else null
        }
    override val contentArea: WidgetBase?
        get() {
            val ptr = gtk_dialog_get_content_area(gtkDialogPtr)
            return if (ptr != null) Widget(ptr) else null
        }
    override val gtkWindowPtr: CPointer<GtkWindow>?
        get() = gtkWidgetPtr?.reinterpret()

    override fun run(): Int = gtk_dialog_run(gtkDialogPtr)

    override fun response(responseId: Int) {
        gtk_dialog_response(gtkDialogPtr, responseId)
    }

    override fun addButton(buttonText: String, responseId: Int): WidgetBase? {
        val ptr = gtk_dialog_add_button(dialog = gtkDialogPtr, button_text = buttonText, response_id = responseId)
        return if (ptr != null) Widget(ptr) else null
    }

    override fun addActionWidget(child: WidgetBase, responseId: Int) {
        gtk_dialog_add_action_widget(dialog = gtkDialogPtr, child = child.gtkWidgetPtr, response_id = responseId)
    }

    override fun changeDefaultResponse(responseId: Int) {
        gtk_dialog_set_default_response(gtkDialogPtr, responseId)
    }

    override fun changeResponseSensitive(responseId: Int, setting: Boolean) {
        gtk_dialog_set_response_sensitive(dialog = gtkDialogPtr, response_id = responseId,
            setting = if (setting) TRUE else FALSE)
    }

    override fun fetchResponseForWidget(widget: WidgetBase): Int =
        gtk_dialog_get_response_for_widget(gtkDialogPtr, widget.gtkWidgetPtr)

    override fun fetchWidgetForResponse(responseId: Int): WidgetBase? {
        val ptr = gtk_dialog_get_widget_for_response(gtkDialogPtr, responseId)
        return if (ptr != null) Widget(ptr) else null
    }

    override fun createUi(init: WindowBase.() -> Unit) {
        visible = true
    }

    /**
     * Connects the *close* signal to a [slot] on a [Dialog]. This signal occurs when the user uses a keybinding to
     * close the dialog. The default binding for this signal is the **Escape** key.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectCloseSignal(slot: CPointer<CloseSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkDialogPtr, signal = CLOSE_SIGNAL, slot = slot, data = userData).toULong()

    /**
     * Connects the *response* signal to a [slot] on a [Dialog]. This signal occurs when an action widget is clicked,
     * the dialog receives a delete event, or the application programmer calls [response]. On a delete event the
     * response ID is `GTK_RESPONSE_DELETE_EVENT`. Otherwise it depends on which action widget was clicked.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectResponseSignal(slot: CPointer<ResponseSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkDialogPtr, signal = RESPONSE_SIGNAL, slot = slot, data = userData).toULong()

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkDialogPtr, handlerId.toUInt())
    }
}

/**
 * The event handler for the *close* signal. Arguments:
 * 1. dialog: CPointer<GtkDialog>
 * 2. userData: gpointer
 */
public typealias CloseSlot = CFunction<(dialog: CPointer<GtkDialog>, userData: gpointer) -> Unit>

/**
 * The event handler for the *response* signal. Arguments:
 * 1. dialog: CPointer<GtkDialog>
 * 2. responseId: Int
 * 3. userData: gpointer
 */
public typealias ResponseSlot = CFunction<(dialog: CPointer<GtkDialog>, responseId: Int, userData: gpointer) -> Unit>
