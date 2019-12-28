package org.guivista.core.widget.button

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guivista.core.connectGObjectSignal

class LinkButton(uri: String, label: String = "") : ButtonBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        if (label.isNotEmpty()) gtk_link_button_new_with_label(uri, label)
        else gtk_link_button_new(uri)
    val gtkLinkButtonPtr: CPointer<GtkLinkButton>?
        get() = gtkWidgetPtr?.reinterpret()
    /** The URI bound to this button. */
    var uri: String
        get() = gtk_link_button_get_uri(gtkLinkButtonPtr)?.toKString() ?: ""
        set(value) = gtk_link_button_set_uri(gtkLinkButtonPtr, value)
    /** The 'visited' state of this button. A visited link is drawn in a different color. */
    var visited: Boolean
        get() = gtk_link_button_get_visited(gtkLinkButtonPtr) == TRUE
        set(value) = gtk_link_button_set_visited(gtkLinkButtonPtr, if (value) TRUE else FALSE)

    /**
     * Connects the *activate-link* signal to a [slot] on a [LinkButton]. This signal is used when the [LinkButton] has
     * been clicked. The default handler will call `gtk_show_uri_on_window()` with the URI stored inside the
     * [uri property][uri]. To override the default behavior you can connect to the ::activate-link signal and stop the
     * propagation of the signal by returning *true* from your handler.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectActivateLinkSignal(slot: CPointer<ActivateLinkSlot>, userData: gpointer): ULong =
        connectGObjectSignal(obj = gtkLinkButtonPtr, signal = "activate-link", slot = slot, data = userData)
}

fun linkButtonWidget(label: String = "", uri: String, init: LinkButton.() -> Unit): LinkButton {
    val linkButton =
        if (label.isNotEmpty()) LinkButton(label = label, uri = uri)
        else LinkButton(uri)
    linkButton.init()
    return linkButton
}

/**
 * The event handler for the *activate-link* signal. Arguments:
 * 1. linkButton: CPointer<GtkLinkButton>
 * 2. userData: gpointer
 */
typealias ActivateLinkSlot = CFunction<(linkButton: CPointer<GtkLinkButton>, userData: gpointer) -> Unit>
