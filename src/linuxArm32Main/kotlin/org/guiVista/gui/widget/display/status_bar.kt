package org.guiVista.gui.widget.display

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.layout.Container

actual class StatusBar(statusBarPtr: CPointer<GtkStatusbar>? = null) : Container {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = statusBarPtr?.reinterpret() ?: gtk_statusbar_new()
    val gtkStatusBarPtr: CPointer<GtkStatusbar>?
        get() = gtkWidgetPtr?.reinterpret()

    /** Retrieves the box containing the label widget. */
    val messageArea: CPointer<GtkWidget>?
        get() = gtk_statusbar_get_message_area(gtkStatusBarPtr)

    actual infix fun fetchContextId(contextDescription: String): UInt =
        gtk_statusbar_get_context_id(gtkStatusBarPtr, contextDescription)

    actual fun push(contextId: UInt, text: String): UInt =
        gtk_statusbar_push(statusbar = gtkStatusBarPtr, context_id = contextId, text = text)

    actual fun pop(contextId: UInt) {
        gtk_statusbar_pop(statusbar = gtkStatusBarPtr, context_id = contextId)
    }

    actual fun remove(contextId: UInt, messageId: UInt) {
        gtk_statusbar_remove(statusbar = gtkStatusBarPtr, context_id = contextId, message_id = messageId)
    }

    actual fun removeAll(contextId: UInt) {
        gtk_statusbar_remove_all(gtkStatusBarPtr, contextId)
    }
}

fun statusBarWidget(statusBarPtr: CPointer<GtkStatusbar>? = null, init: StatusBar.() -> Unit): StatusBar {
    val statusBar = StatusBar(statusBarPtr)
    statusBar.init()
    return statusBar
}
