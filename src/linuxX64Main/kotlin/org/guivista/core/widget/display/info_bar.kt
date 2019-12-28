package org.guivista.core.widget.display

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.connectGObjectSignal
import org.guivista.core.layout.Container

/** Report important messages to the user. */
class InfoBar : Container {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = gtk_info_bar_new()
    val gtkInfoBarPtr: CPointer<GtkInfoBar>?
        get() = gtkWidgetPtr?.reinterpret()
    /** Type of the message. The type may be used to determine the appearance of the [Info Bar][InfoBar]. */
    var messageType: GtkMessageType
        get() = gtk_info_bar_get_message_type(gtkInfoBarPtr)
        set(value) = gtk_info_bar_set_message_type(gtkInfoBarPtr, value)
    /** Controls whether the action bar shows its contents or not. */
    var revealed: Boolean
        get() = gtk_info_bar_get_revealed(gtkInfoBarPtr) == TRUE
        set(value) = gtk_info_bar_set_revealed(gtkInfoBarPtr, if (value) TRUE else FALSE)
    /** Whether to include a standard close button. */
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
     * Connects the *close* signal to a [slot] on a info bar. The *close* signal is used when a user uses a key binding
     * to dismiss the info bar.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectCloseSignal(slot: CPointer<CloseSlot>, userData: gpointer): ULong =
            connectGObjectSignal(obj = gtkInfoBarPtr, signal = "close", slot = slot, data = userData)

    /**
     * Connects the *response* signal to a [slot] on a info bar. The *response* signal is used when an action widget
     * is clicked, or the application programmer calls `gtk_dialog_response()`. The responseId depends on which action
     * widget was clicked.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectResponseSignal(slot: CPointer<ResponseSlot>, userData: gpointer): ULong =
            connectGObjectSignal(obj = gtkInfoBarPtr, signal = "response", slot = slot, data = userData)

    /**
     * Adds a button with the given text, and sets things up so that clicking the button will emit the “response”
     * signal with the given [responseId]. The button is appended to the end of the info bars's action area.
     * @param buttonText The button's text.
     * @param responseId Response ID to use for the button.
     * @return A button widget. Usually you don't need it.
     */
    fun addButton(buttonText: String, responseId: Int): CPointer<GtkButton>? =
            gtk_info_bar_add_button(
                    info_bar = gtkInfoBarPtr,
                    button_text = buttonText,
                    response_id = responseId
            )?.reinterpret()

    /**
     * Adds more buttons which is the same as calling [addButton] repeatedly. The variable argument list should be null
     * terminated. Each button must have both text and response ID.
     * @param firstButtonText Button text or stock ID.
     * @param responseIds Response ID for first button, then more text-response_id pairs ending with *null*.
     */
    fun addMultipleButtons(firstButtonText: String, vararg responseIds: Int?) {
        gtk_info_bar_add_buttons(info_bar = gtkInfoBarPtr, first_button_text = firstButtonText,
                variadicArguments = *responseIds)
    }

    /** Emits the *response* signal with the given [Response ID][responseId] .*/
    fun response(responseId: Int) {
        gtk_info_bar_response(gtkInfoBarPtr, responseId)
    }
}

fun infoBarWidget(init: InfoBar.() -> Unit): InfoBar {
    val infoBar = InfoBar()
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