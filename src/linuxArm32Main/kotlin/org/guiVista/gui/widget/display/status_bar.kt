package org.guiVista.gui.widget.display

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.layout.Container
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase

public actual class StatusBar(statusBarPtr: CPointer<GtkStatusbar>? = null) : Container {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = statusBarPtr?.reinterpret() ?: gtk_statusbar_new()
    public val gtkStatusBarPtr: CPointer<GtkStatusbar>?
        get() = gtkWidgetPtr?.reinterpret()
    public actual val messageArea: WidgetBase?
        get() {
            val ptr = gtk_statusbar_get_message_area(gtkStatusBarPtr)
            return if (ptr != null) Widget(ptr) else null
        }

    public actual infix fun fetchContextId(contextDescription: String): UInt =
        gtk_statusbar_get_context_id(gtkStatusBarPtr, contextDescription)

    public actual fun push(contextId: UInt, text: String): UInt =
        gtk_statusbar_push(statusbar = gtkStatusBarPtr, context_id = contextId, text = text)

    public actual fun pop(contextId: UInt) {
        gtk_statusbar_pop(statusbar = gtkStatusBarPtr, context_id = contextId)
    }

    public actual fun remove(contextId: UInt, messageId: UInt) {
        gtk_statusbar_remove(statusbar = gtkStatusBarPtr, context_id = contextId, message_id = messageId)
    }

    public actual fun removeAll(contextId: UInt) {
        gtk_statusbar_remove_all(gtkStatusBarPtr, contextId)
    }
}

public fun statusBarWidget(statusBarPtr: CPointer<GtkStatusbar>? = null, init: StatusBar.() -> Unit): StatusBar {
    val statusBar = StatusBar(statusBarPtr)
    statusBar.init()
    return statusBar
}
