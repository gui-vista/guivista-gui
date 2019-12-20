package org.guivista.core.window

import gtk3.GtkApplicationWindow
import gtk3.GtkWidget
import gtk3.gtk_application_window_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.Application

open class AppWindow(private val app: Application) : Window() {
    private var _gtkWidgetPtr: CPointer<GtkWidget>? = null
    override val gtkWidgetPtr: CPointer<GtkWidget>?
        get() = _gtkWidgetPtr?.reinterpret()
    val gtkAppWinPtr: CPointer<GtkApplicationWindow>?
        get() = gtkWidgetPtr?.reinterpret()

    override fun createUi(init: Window.() -> Unit) {
        _gtkWidgetPtr = gtk_application_window_new(app.gtkAppPtr)
        this.init()
        val mainLayout = createMainLayout()
        if (mainLayout != null) addChild(mainLayout)
        if (visible) showAll()
    }
}
