package org.guiVista.gui.widget.button

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

private const val ACTIVATE_LINK_SIGNAL = "activate-link"

public actual class LinkButton(
    linkButtonPtr: CPointer<GtkLinkButton>? = null,
    uri: String = "",
    label: String = ""
) : ButtonBase {
    @Suppress("IfThenToElvis")
    override val gtkWidgetPtr: CPointer<GtkWidget>? = when {
        linkButtonPtr != null -> linkButtonPtr.reinterpret()
        uri.isNotEmpty() && label.isNotEmpty() -> gtk_link_button_new_with_label(uri, label)
        else -> createLinkButton(uri)
    }
    public val gtkLinkButtonPtr: CPointer<GtkLinkButton>?
        get() = gtkWidgetPtr?.reinterpret()
    public actual var uri: String
        get() = gtk_link_button_get_uri(gtkLinkButtonPtr)?.toKString() ?: ""
        set(value) = gtk_link_button_set_uri(gtkLinkButtonPtr, value)
    public actual var visited: Boolean
        get() = gtk_link_button_get_visited(gtkLinkButtonPtr) == TRUE
        set(value) = gtk_link_button_set_visited(gtkLinkButtonPtr, if (value) TRUE else FALSE)

    private fun createLinkButton(uri: String) =
        if (uri.isEmpty()) throw IllegalArgumentException("Cannot have empty uri.")
        else gtk_link_button_new(uri)

    /**
     * Connects the *activate-link* signal to a [slot] on a [LinkButton]. This signal is used when the [LinkButton] has
     * been clicked. The default handler will call `gtk_show_uri_on_window()` with the URI stored inside the
     * [uri property][uri]. To override the default behavior you can connect to the ::activate-link signal and stop the
     * propagation of the signal by returning *true* from your handler.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectActivateLinkSignal(slot: CPointer<ActivateLinkSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkLinkButtonPtr, signal = ACTIVATE_LINK_SIGNAL, slot = slot, data = userData).toULong()

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkLinkButtonPtr, handlerId.toUInt())
    }
}

public fun linkButtonWidget(
    linkButtonPtr: CPointer<GtkLinkButton>? = null,
    label: String = "",
    uri: String = "",
    init: LinkButton.() -> Unit
): LinkButton {
    val linkButton = LinkButton(linkButtonPtr = linkButtonPtr, label = label, uri = uri)
    linkButton.init()
    return linkButton
}

/**
 * The event handler for the *activate-link* signal. Arguments:
 * 1. linkButton: CPointer<GtkLinkButton>
 * 2. userData: gpointer
 */
public typealias ActivateLinkSlot = CFunction<(linkButton: CPointer<GtkLinkButton>, userData: gpointer) -> Unit>
