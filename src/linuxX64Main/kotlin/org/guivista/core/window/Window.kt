package org.guivista.core.window

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guivista.core.layout.Container
import org.guivista.core.widget.Widget

/**
 * Represents a top level window that can contain other widgets. Nearly always, the type of the window should be
 * GTK_WINDOW_TOPLEVEL. If you’re implementing something like a popup menu from scratch (which is a bad idea, just use
 * GtkMenu), you might use GTK_WINDOW_POPUP. GTK_WINDOW_POPUP is not for dialogs, though in some other toolkit dialogs
 * are called “popups”. In GTK, GTK_WINDOW_POPUP means a pop-up menu or pop-up tooltip. On X11, popup windows are not
 * controlled by the window manager.
 *
 * If you simply want an undecorated window (no window borders), use `gtk_window_set_decorated()`, don’t use
 * GTK_WINDOW_POPUP. All top level windows are stored in an internal top-level window list. This list can be obtained
 * from `gtk_window_list_toplevels()`. To delete a GtkWindow call `gtk_widget_destroy()`.
 */
abstract class Window(val winType: GtkWindowType = GtkWindowType.GTK_WINDOW_TOPLEVEL) : Container {
    private var _gtkWidgetPtr: CPointer<GtkWidget>? = null
    override val gtkWidgetPtr: CPointer<GtkWidget>?
        get() = _gtkWidgetPtr
    val gtkWinPtr: CPointer<GtkWindow>? by lazy {
        // Have to manually specify the type for the reinterpret function for some strange reason.
        gtkWidgetPtr?.reinterpret<GtkWindow>()
    }
    /** If set to *true* then the window should receive the input focus. */
    var acceptFocus: Boolean
        set(value) = gtk_window_set_accept_focus(gtkWinPtr, if (value) TRUE else FALSE)
        get() = gtk_window_get_accept_focus(gtkWinPtr) == TRUE
    /** Name of the window. */
    var title: String
        set(value) = gtk_window_set_title(gtkWinPtr, value)
        get() = gtk_window_get_title(gtkWinPtr)?.toKString() ?: ""
    /** If set to *true* then a user can resize the window. Default value is *true*. */
    var resizable: Boolean
        set(value) = gtk_window_set_resizable(gtkWinPtr, if (value) TRUE else FALSE)
        get() = gtk_window_get_resizable(gtkWinPtr) == TRUE
    /** If set to *true* then the window is maximized. */
    val isMaximized: Boolean
        get() = gtk_window_is_maximized(gtkWinPtr) == TRUE

    /**
     * Changes the default size of a window. If the window’s “natural” size (its size request) is larger than the
     * default, the default will be ignored. More generally, if the default size does not obey the geometry hints for
     * the window (`gtk_window_set_geometry_hints()` can be used to set these explicitly), the default size will be
     * clamped to the nearest permitted size. Unlike `gtk_widget_set_size_request()`, which sets a size request for a
     * widget and thus would keep users from shrinking the window. This function only sets the initial size just as if
     * the user had resized the window themselves. Users can still shrink the window again as they normally would.
     * Setting a default size of -1 means to use the “natural” default size (the size request of the window).
     *
     * For more control over a window’s initial size and how resizing works, investigate
     * `gtk_window_set_geometry_hints()`. For some uses, `gtk_window_resize()` is a more appropriate function. The
     * function `gtk_window_resize()` changes the current size of the window rather than the size to be used on initial
     * display, and always affects the window itself, not the geometry widget.
     *
     * The default size of a window only affects the first time a window is shown; if a window is hidden and re-shown,
     * it will remember the size it had prior to hiding, rather than using the default size. Windows can’t actually be
     * 0x0 in size, they must be at least 1x1, but passing 0 for width and height is OK, resulting in a 1x1 default
     * size. If you use this function to reestablish a previously saved window size, note that the appropriate size to
     * save is the one returned by `gtk_window_get_size()`. Using the window allocation directly will not work in all
     * circumstances and can lead to growing or shrinking windows.
     *
     * @param width Width in pixels, or -1 to unset the default width.
     * @param height Height in pixels, or -1 to unset the default height.
     */
    fun changeDefaultSize(width: Int, height: Int) {
        gtk_window_set_default_size(gtkWinPtr, width, height)
    }

    /** Closes the window. */
    fun close() {
        gtk_window_close(gtkWinPtr)
    }

    /**
     * Asks to maximize window so that it becomes full-screen. Note that you shouldn’t assume the window is definitely
     * maximized afterward, because other entities (e.g. the user or window manager) could unmaximize it again, and not
     * all window managers support maximization. But normally the window will end up maximized. Just don’t write code
     * that crashes if not.
     *
     * It’s permitted to call this function before showing a window, in which case the window will be maximized when it
     * appears onscreen initially. You can track maximization via the “window-state-event” signal on GtkWidget, or by
     * listening to notifications on the “is-maximized” property.
     */
    fun maximize() {
        gtk_window_maximize(gtkWinPtr)
    }

    /**
     * Asks to unmaximize window . Note that you shouldn’t assume the window is definitely unmaximized afterward
     * because other entities (e.g. the user or window manager) could maximize it again, and not all window managers
     * honor requests to unmaximize. But normally the window will end up unmaximized. Just don’t write code that
     * crashes if not. You can track maximization via the “window-state-event” signal on GtkWidget.
     */
    fun unmaximize() {
        gtk_window_unmaximize(gtkWinPtr)
    }

    /** Adds a new widget (window child) to the window. */
    fun addWidget(widget: Widget) {
        gtk_container_add(gtkContainerPtr, widget.gtkWidgetPtr)
    }

    /** Removes a widget (window child) from the window. */
    fun removeWidget(widget: Widget) {
        gtk_container_remove(gtkContainerPtr, widget.gtkWidgetPtr)
    }

    open fun resetFocus() {}

    open fun createMainLayout(): Container? = null

    open fun createUi(init: Window.() -> Unit) {
        _gtkWidgetPtr = gtk_window_new(winType)
        this.init()
    }
}
