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

private const val CLOSE_SIGNAL = "close"
private const val RESPONSE_SIGNAL = "response"

/** Report important messages to the user. */
class InfoBar(infoBarPtr: CPointer<GtkInfoBar>? = null) : Container {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = infoBarPtr?.reinterpret() ?: gtk_info_bar_new()
    val gtkInfoBarPtr: CPointer<GtkInfoBar>?
        get() = gtkWidgetPtr?.reinterpret()
    /**
     * Type of the message. The type may be used to determine the appearance of the [Info Bar][InfoBar]. Default value
     * is *GtkMessageType.GTK_MESSAGE_INFO*.
     */
    var messageType: GtkMessageType
        get() = gtk_info_bar_get_message_type(gtkInfoBarPtr)
        set(value) = gtk_info_bar_set_message_type(gtkInfoBarPtr, value)
    /** Controls whether the action bar shows its contents or not. Default value is *true*. */
    var revealed: Boolean
        get() = gtk_info_bar_get_revealed(gtkInfoBarPtr) == TRUE
        set(value) = gtk_info_bar_set_revealed(gtkInfoBarPtr, if (value) TRUE else FALSE)
    /** Whether to include a standard close button. Default value is *false*. */
    var showCloseButton: Boolean
        get() = gtk_info_bar_get_show_close_button(gtkInfoBarPtr) == TRUE
        set(value) = gtk_info_bar_set_show_close_button(gtkInfoBarPtr, if (value) TRUE else FALSE)
    /** The action area of the [InfoBar]. */
    val actionArea: CPointer<GtkWidget>?
        get() = gtk_info_bar_get_action_area(gtkInfoBarPtr)
    /** The content area of the [InfoBar]. */
    val contentArea: CPointer<GtkWidget>?
        get() = gtk_info_bar_get_content_area(gtkInfoBarPtr)

    /**
     * Connects the *close* signal to a [slot] on a [InfoBar]. This signal is used when a user uses a key binding
     * to dismiss the info bar.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectCloseSignal(slot: CPointer<CloseSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkInfoBarPtr, signal = CLOSE_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *response* signal to a [slot] on a [InfoBar]. This signal is used when an action widget
     * is clicked, or the application programmer calls `gtk_dialog_response()`. The responseId depends on which action
     * widget was clicked.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectResponseSignal(slot: CPointer<ResponseSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkInfoBarPtr, signal = RESPONSE_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkInfoBarPtr, handlerId)
    }

    /**
     * Adds a button with the given text, and sets things up so that clicking the button will emit the “response”
     * signal with the given [responseId]. The button is appended to the end of the info bars's action area.
     * @param buttonText The button's text.
     * @param responseId Response ID to use for the button.
     * @return A button widget. Usually you don't need it.
     */
    fun addButton(buttonText: String, responseId: Int): CPointer<GtkButton>? = gtk_info_bar_add_button(
        info_bar = gtkInfoBarPtr,
        button_text = buttonText,
        response_id = responseId
    )?.reinterpret()

    /**
     * Adds more buttons which is the same as calling [addButton] repeatedly. Does the same thing as
     * `gtk_info_bar_add_buttons`.
     * @param buttons Pairs where each one consists of a Response ID, and button text.
     */
    fun addMultipleButtons(vararg buttons: Pair<Int, String>) {
        buttons.forEach { (responseId, txt) -> addButton(txt, responseId) }
    }

    /** Emits the *response* signal with the given [Response ID][responseId] .*/
    fun response(responseId: Int) {
        gtk_info_bar_response(gtkInfoBarPtr, responseId)
    }
}

fun infoBarWidget(infoBarPtr: CPointer<GtkInfoBar>? = null, init: InfoBar.() -> Unit): InfoBar {
    val infoBar = InfoBar(infoBarPtr)
    infoBar.init()
    return infoBar
}

/**
 * The event handler for the *close* signal. Arguments:
 * 1. infoBar: CPointer<GtkInfoBar>
 * 2. userData: gpointer
 */
typealias CloseSlot = CFunction<(infoBar: CPointer<GtkInfoBar>, userData: gpointer) -> Unit>

/**
 * The event handler for the *response* signal. Arguments:
 * 1. infoBar: CPointer<GtkInfoBar>
 * 2. responseId: Int
 * 3. userData: gpointer
 */
typealias ResponseSlot = CFunction<(infoBar: CPointer<GtkInfoBar>, responseId: Int, userData: gpointer) -> Unit>
