package org.guiVista.gui.dialog

import org.guiVista.gui.widget.WidgetBase
import org.guiVista.gui.window.WindowBase

/** Base interface for popup windows (dialogs). */
public expect interface DialogBase : WindowBase {
    /**
     * Returns the header bar of the dialog. Note that the header bar is only used by the dialog if the
     * “use-header-bar” property is *true*.
     */
    public val headerBar: WidgetBase?

    /** The content area of the dialog. */
    public val contentArea: WidgetBase?

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
    public fun run(): Int

    /**
     * Emits the **response** signal with the given response ID. Used to indicate that the user has responded to the
     * dialog in some way; typically either you or [run] will be monitoring the ::response signal, and take appropriate
     * action.
     * @param responseId The response ID.
     */
    public fun response(responseId: Int)

    /**
     * Adds a button with the given text, and sets things up so that clicking the button will emit the **response**
     * signal with the given [responseId]. The button is appended to the end of the dialog’s action area. The button
     * widget is returned, but usually you don’t need it.
     * @param buttonText Text of the button.
     * @param responseId Response ID for the button.
     * @return The widget that was added.
     */
    public fun addButton(buttonText: String, responseId: Int): WidgetBase?

    /**
     * Adds an activatable widget to the action area of a dialog, connecting a signal handler that will emit the
     * **response** signal on the dialog when the widget is activated. The widget is appended to the end of the
     * dialog’s action area.
     * @param child An activatable widget.
     * @param responseId The response ID for the [child].
     */
    public fun addActionWidget(child: WidgetBase, responseId: Int)

    /**
     * Sets the last widget in the dialog’s action area with the given [responseId] as the default widget for the
     * dialog. Pressing **Enter** normally activates the default widget.
     * @param responseId A response ID.
     */
    public fun changeDefaultResponse(responseId: Int)

    /**
     * Calls `gtk_widget_set_sensitive(widget, @setting)` for each widget in the dialog’s action area with the given
     * [responseId]. A convenient way to sensitize/desensitize dialog buttons.
     * @param responseId A response ID.
     * @param setting If *true* then the response is sensitive.
     */
    public fun changeResponseSensitive(responseId: Int, setting: Boolean)

    /**
     * Gets the response id of a widget in the action area of a dialog.
     * @param widget A widget in the action area of the dialog.
     * @return The response id of the widget, or `GTK_RESPONSE_NONE` if the widget doesn’t have a response id set.
     */
    public fun fetchResponseForWidget(widget: WidgetBase): Int

    /**
     * Gets the widget button that uses the given [responseId] in the action area of a dialog.
     * @param responseId The response ID used by the dialog widget.
     * @return The widget button that uses the given [responseId], or *null*.
     */
    public fun fetchWidgetForResponse(responseId: Int): WidgetBase?
}
