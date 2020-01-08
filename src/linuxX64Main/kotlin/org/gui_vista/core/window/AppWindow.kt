package org.gui_vista.core.window

import gtk3.GtkApplicationWindow
import gtk3.GtkWidget
import gtk3.GtkWindow
import gtk3.gtk_application_window_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.gui_vista.core.Application

/**
 * A window with [Application] support. Maps to
 * [GtkApplicationWindow](https://developer.gnome.org/gtk3/stable/GtkApplicationWindow.html).
 */
open class AppWindow(private val app: Application) : WindowBase {
    private var _gtkWidgetPtr: CPointer<GtkWidget>? = null
    override val gtkWidgetPtr: CPointer<GtkWidget>?
        get() = _gtkWidgetPtr?.reinterpret()
    val gtkAppWinPtr: CPointer<GtkApplicationWindow>?
        get() = _gtkWidgetPtr?.reinterpret()
    override val gtkWindowPtr: CPointer<GtkWindow>? by lazy {
        // Have to manually specify the type for the reinterpret extension function for some strange reason.
        _gtkWidgetPtr?.reinterpret<GtkWindow>()
    }

    override fun createUi(init: WindowBase.() -> Unit) {
        _gtkWidgetPtr = gtk_application_window_new(app.gtkAppPtr)
        this.init()
        val mainLayout = createMainLayout()
        if (mainLayout != null) this += mainLayout
        if (visible) {
            showAll()
            resetFocus()
        }
    }
}
