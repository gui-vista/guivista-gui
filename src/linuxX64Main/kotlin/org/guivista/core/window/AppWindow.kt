package org.guivista.core.window

import gtk3.GtkWidget
import gtk3.gtk_application_window_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.Application

class AppWindow(private val app: Application) : Window() {
    private var _widgetPtr: CPointer<GtkWidget>? = null
    override val widgetPtr: CPointer<GtkWidget>?
        get() = _widgetPtr?.reinterpret()

    override fun createUi(init: Window.() -> Unit) {
        _widgetPtr = gtk_application_window_new(app.gtkAppPtr)
        this.init()
    }
}
