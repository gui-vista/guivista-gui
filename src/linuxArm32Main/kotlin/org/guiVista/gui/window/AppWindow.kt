package org.guiVista.gui.window

import gtk3.GtkApplicationWindow
import gtk3.GtkWidget
import gtk3.GtkWindow
import gtk3.gtk_application_window_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.GuiApplication

actual open class AppWindow(private val app: GuiApplication) : WindowBase {
    private var _gtkWidgetPtr: CPointer<GtkWidget>? = null
    override val gtkWidgetPtr: CPointer<GtkWidget>?
        get() = _gtkWidgetPtr?.reinterpret()
    val gtkAppWinPtr: CPointer<GtkApplicationWindow>?
        get() = _gtkWidgetPtr?.reinterpret()
    override val gtkWindowPtr: CPointer<GtkWindow>? by lazy {
        _gtkWidgetPtr?.reinterpret()
    }

    override fun createUi(init: WindowBase.() -> Unit) {
        _gtkWidgetPtr = gtk_application_window_new(app.gtkApplicationPtr)
        this.init()
        val mainLayout = createMainLayout()
        if (mainLayout != null) this += mainLayout
        if (visible) {
            showAll()
            resetFocus()
        }
    }
}
