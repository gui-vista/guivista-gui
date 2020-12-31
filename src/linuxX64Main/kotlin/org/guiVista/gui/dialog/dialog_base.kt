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

public actual interface DialogBase : WindowBase {
    public val gtkDialogPtr: CPointer<GtkDialog>?
        get() = gtkWidgetPtr?.reinterpret()
    override val gtkWindowPtr: CPointer<GtkWindow>?
        get() = gtkWidgetPtr?.reinterpret()

    /**
     * Returns the header bar of the dialog. Note that the header bar is only used by the dialog if the
     * “use-header-bar” property is *true*.
     */
    public val headerBar: WidgetBase?
        get() {
            val ptr = gtk_dialog_get_header_bar(gtkDialogPtr)
            return if (ptr != null) Widget(ptr) else null
        }

    /** The content area of the dialog. */
    public val contentArea: WidgetBase?
        get() {
            val ptr = gtk_dialog_get_content_area(gtkDialogPtr)
            return if (ptr != null) Widget(ptr) else null
        }

    /**
     * Blocks in a recursive main loop until the dialog either emits the **response** signal, or is destroyed. If the
     * dialog is destroyed during the call to this function then the function returns `GTK_RESPONSE_NONE`. Otherwise
     * the function returns the response ID from the ::response signal emission. Before entering the recursive main
     * loop this function calls `gtk_widget_show()` on the dialog for you. Note that you still need to show any
     * children of the dialog yourself.
     *
     * During [run] the default behavior of **delete-event** is disabled; if the dialog receives ::delete_event it will
     * not be destroyed as windows usually are, and this function will return `GTK_RESPONSE_DELETE_EVENT`. Also during
     * [run] the dialog will be modal. You can force this function to return at any time by calling [response] to emit
     * the ::response signal. Destroying the dialog during [run] is a very bad idea, because your post-run code won’t
     * know whether the dialog was destroyed or not.
     *
     * After this function returns you are responsible for hiding, or destroying the dialog if you wish to do so.
     * @return The response ID.
     */
    public fun run(): Int = gtk_dialog_run(gtkDialogPtr)

    /**
     * Emits the **response** signal with the given response ID. Used to indicate that the user has responded to the
     * dialog in some way; typically either you or [run] will be monitoring the ::response signal, and take appropriate
     * action.
     * @param responseId The response ID.
     */
    public fun response(responseId: Int) {
        gtk_dialog_response(gtkDialogPtr, responseId)
    }

    /**
     * Adds a button with the given text, and sets things up so that clicking the button will emit the **response**
     * signal with the given [responseId]. The button is appended to the end of the dialog’s action area. The button
     * widget is returned, but usually you don’t need it.
     * @param buttonText Text of the button.
     * @param responseId Response ID for the button.
     * @return The widget that was added.
     */
    public fun addButton(buttonText: String, responseId: Int): WidgetBase? {
        val ptr = gtk_dialog_add_button(dialog = gtkDialogPtr, button_text = buttonText, response_id = responseId)
        return if (ptr != null) Widget(ptr) else null
    }

    /**
     * Adds an activatable widget to the action area of a dialog, connecting a signal handler that will emit the
     * **response** signal on the dialog when the widget is activated. The widget is appended to the end of the
     * dialog’s action area.
     * @param child An activatable widget.
     * @param responseId The response ID for the [child].
     */
    public fun addActionWidget(child: WidgetBase, responseId: Int) {
        gtk_dialog_add_action_widget(dialog = gtkDialogPtr, child = child.gtkWidgetPtr, response_id = responseId)
    }

    /**
     * Sets the last widget in the dialog’s action area with the given [responseId] as the default widget for the
     * dialog. Pressing **Enter** normally activates the default widget.
     * @param responseId A response ID.
     */
    public fun changeDefaultResponse(responseId: Int) {
        gtk_dialog_set_default_response(gtkDialogPtr, responseId)
    }

    /**
     * Calls `gtk_widget_set_sensitive(widget, @setting)` for each widget in the dialog’s action area with the given
     * [responseId]. A convenient way to sensitize/desensitize dialog buttons.
     * @param responseId A response ID.
     * @param setting If *true* then the response is sensitive.
     */
    public fun changeResponseSensitive(responseId: Int, setting: Boolean) {
        gtk_dialog_set_response_sensitive(dialog = gtkDialogPtr, response_id = responseId,
            setting = if (setting) TRUE else FALSE)
    }

    /**
     * Gets the response id of a widget in the action area of a dialog.
     * @param widget A widget in the action area of the dialog.
     * @return The response id of the widget, or `GTK_RESPONSE_NONE` if the widget doesn’t have a response id set.
     */
    public fun fetchResponseForWidget(widget: WidgetBase): Int =
        gtk_dialog_get_response_for_widget(gtkDialogPtr, widget.gtkWidgetPtr)

    /**
     * Gets the widget button that uses the given [responseId] in the action area of a dialog.
     * @param responseId The response ID used by the dialog widget.
     * @return The widget button that uses the given [responseId], or *null*.
     */
    public fun fetchWidgetForResponse(responseId: Int): WidgetBase? {
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
        connectGSignal(obj = gtkDialogPtr, signal = CLOSE_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *response* signal to a [slot] on a [Dialog]. This signal occurs when an action widget is clicked,
     * the dialog receives a delete event, or the application programmer calls [response]. On a delete event the
     * response ID is `GTK_RESPONSE_DELETE_EVENT`. Otherwise it depends on which action widget was clicked.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectResponseSignal(slot: CPointer<ResponseSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkDialogPtr, signal = RESPONSE_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkDialogPtr, handlerId)
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
