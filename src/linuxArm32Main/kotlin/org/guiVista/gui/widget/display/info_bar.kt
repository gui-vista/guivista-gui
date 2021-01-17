package org.guiVista.gui.widget.display

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal
import org.guiVista.gui.layout.Container

public actual class InfoBar(infoBarPtr: CPointer<GtkInfoBar>? = null) : Container {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = infoBarPtr?.reinterpret() ?: gtk_info_bar_new()
    public val gtkInfoBarPtr: CPointer<GtkInfoBar>?
        get() = gtkWidgetPtr?.reinterpret()

    /**
     * Type of the message. The type may be used to determine the appearance of the [Info Bar][InfoBar]. Default value
     * is *GtkMessageType.GTK_MESSAGE_INFO*.
     */
    public var messageType: GtkMessageType
        get() = gtk_info_bar_get_message_type(gtkInfoBarPtr)
        set(value) = gtk_info_bar_set_message_type(gtkInfoBarPtr, value)
    public actual var showCloseButton: Boolean
        get() = gtk_info_bar_get_show_close_button(gtkInfoBarPtr) == TRUE
        set(value) = gtk_info_bar_set_show_close_button(gtkInfoBarPtr, if (value) TRUE else FALSE)

    /** The action area of the [InfoBar]. */
    public val actionArea: CPointer<GtkWidget>?
        get() = gtk_info_bar_get_action_area(gtkInfoBarPtr)

    /** The content area of the [InfoBar]. */
    public val contentArea: CPointer<GtkWidget>?
        get() = gtk_info_bar_get_content_area(gtkInfoBarPtr)

    /**
     * Connects the *close* event to a [handler] on a [InfoBar]. This event is used when a user uses a key binding
     * to dismiss the info bar.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectCloseEvent(handler: CPointer<CloseHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkInfoBarPtr, signal = InfoBarEvent.close, slot = handler, data = userData).toULong()

    /**
     * Connects the *response* event to a [handler] on a [InfoBar]. This event is used when an action widget
     * is clicked, or the application programmer calls `gtk_dialog_response()`. The responseId depends on which action
     * widget was clicked.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectResponseEvent(handler: CPointer<ResponseHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkInfoBarPtr, signal = InfoBarEvent.response, slot = handler, data = userData).toULong()

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkInfoBarPtr, handlerId.toUInt())
    }

    /**
     * Adds a button with the given text, and sets things up so that clicking the button will emit the “response”
     * event with the given [responseId]. The button is appended to the end of the info bars's action area.
     * @param buttonText The button's text.
     * @param responseId Response ID to use for the button.
     * @return A button widget. Usually you don't need it.
     */
    public fun addButton(buttonText: String, responseId: Int): CPointer<GtkButton>? = gtk_info_bar_add_button(
        info_bar = gtkInfoBarPtr,
        button_text = buttonText,
        response_id = responseId
    )?.reinterpret()

    public actual fun addMultipleButtons(vararg buttons: Pair<Int, String>) {
        buttons.forEach { (responseId, txt) -> addButton(txt, responseId) }
    }

    public actual fun response(responseId: Int) {
        gtk_info_bar_response(gtkInfoBarPtr, responseId)
    }
}

public fun infoBarWidget(infoBarPtr: CPointer<GtkInfoBar>? = null, init: InfoBar.() -> Unit): InfoBar {
    val infoBar = InfoBar(infoBarPtr)
    infoBar.init()
    return infoBar
}

/**
 * The event handler for the *close* event. Arguments:
 * 1. infoBar: CPointer<GtkInfoBar>
 * 2. userData: gpointer
 */
public typealias CloseHandler = CFunction<(infoBar: CPointer<GtkInfoBar>, userData: gpointer) -> Unit>

/**
 * The event handler for the *response* event. Arguments:
 * 1. infoBar: CPointer<GtkInfoBar>
 * 2. responseId: Int
 * 3. userData: gpointer
 */
public typealias ResponseHandler = CFunction<(
    infoBar: CPointer<GtkInfoBar>,
    responseId: Int,
    userData: gpointer
) -> Unit>
