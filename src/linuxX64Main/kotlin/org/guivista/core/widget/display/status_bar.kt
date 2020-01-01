package org.guivista.core.widget.display

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.layout.Container

/** Report messages of minor importance to the user. */
class StatusBar(statusBarPtr: CPointer<GtkStatusbar>? = null) : Container {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = statusBarPtr?.reinterpret() ?: gtk_statusbar_new()
    val gtkStatusBarPtr: CPointer<GtkStatusbar>?
        get() = gtkWidgetPtr?.reinterpret()
    /** Retrieves the box containing the label widget. */
    val messageArea: CPointer<GtkWidget>?
        get() = gtk_statusbar_get_message_area(gtkStatusBarPtr)

    /**
     * Gets a new context identifier given a description of the actual context. Note that the description is not shown
     * in the UI.
     * @param contextDescription Textual description of what context the new message is being used in.
     * @return A ID.
     */
    fun fetchContextId(contextDescription: String): UInt =
            gtk_statusbar_get_context_id(gtkStatusBarPtr, contextDescription)

    /**
     * Pushes a new message onto a statusbar’s stack.
     * @param contextId The message’s context ID as returned by [fetchContextId].
     * @param text The message to add to the [StatusBar].
     * @return A message ID that can be used with [remove].
     */
    fun push(contextId: UInt, text: String): UInt =
            gtk_statusbar_push(statusbar = gtkStatusBarPtr, context_id = contextId, text = text)

    /**
     * Removes the first message in the [StatusBar's][StatusBar] stack with the given context ID. Note that this may
     * not change the displayed message, if the message at the top of the stack has a different context ID.
     * @param contextId A context identifier.
     */
    fun pop(contextId: UInt) {
        gtk_statusbar_pop(statusbar = gtkStatusBarPtr, context_id = contextId)
    }

    /**
     * Forces the removal of a message from a [StatusBar's][StatusBar] stack. The exact [contextId] and [messageId]
     * must be specified.
     * @param contextId A context identifier.
     * @param messageId The message identifier as returned by [push].
     */
    fun remove(contextId: UInt, messageId: UInt) {
        gtk_statusbar_remove(statusbar = gtkStatusBarPtr, context_id = contextId, message_id = messageId)
    }

    /**
     * Forces the removal of all messages from a [StatusBar's][StatusBar] stack with the exact [contextId] .
     * @param contextId The context identifier.
     */
    fun removeAll(contextId: UInt) {
        gtk_statusbar_remove_all(gtkStatusBarPtr, contextId)
    }
}

fun statusBarWidget(statusBarPtr: CPointer<GtkStatusbar>? = null, init: StatusBar.() -> Unit): StatusBar {
    val statusBar = StatusBar(statusBarPtr)
    statusBar.init()
    return statusBar
}
