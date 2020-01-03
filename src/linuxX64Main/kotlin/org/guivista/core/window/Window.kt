package org.guivista.core.window

import gtk3.*
import kotlinx.cinterop.*
import org.guivista.core.Application
import org.guivista.core.layout.Container
import org.guivista.core.widget.Widget
import org.guivista.core.widget.WidgetBase

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
abstract class Window(
    windowPtr: CPointer<GtkWindow>? = null,
    val winType: GtkWindowType = GtkWindowType.GTK_WINDOW_TOPLEVEL
) : Container {
    private var _gtkWidgetPtr: CPointer<GtkWidget>? = windowPtr?.reinterpret()
    override val gtkWidgetPtr: CPointer<GtkWidget>?
        get() = _gtkWidgetPtr
    val gtkWindowPtr: CPointer<GtkWindow>? by lazy {
        // Have to manually specify the type for the reinterpret function for some strange reason.
        gtkWidgetPtr?.reinterpret<GtkWindow>()
    }
    /** If set to *true* then the window should receive the input focus. Default value is *true*. */
    var acceptFocus: Boolean
        set(value) = gtk_window_set_accept_focus(gtkWindowPtr, if (value) TRUE else FALSE)
        get() = gtk_window_get_accept_focus(gtkWindowPtr) == TRUE
    /** Name of the window. Default value is *""* (an empty String). */
    var title: String
        set(value) = gtk_window_set_title(gtkWindowPtr, value)
        get() = gtk_window_get_title(gtkWindowPtr)?.toKString() ?: ""
    /** If set to *true* then a user can resize the window. Default value is *true*. */
    var resizable: Boolean
        set(value) = gtk_window_set_resizable(gtkWindowPtr, if (value) TRUE else FALSE)
        get() = gtk_window_get_resizable(gtkWindowPtr) == TRUE
    /** If set to *true* then the window is maximized. Default value is *false*. */
    val isMaximized: Boolean
        get() = gtk_window_is_maximized(gtkWindowPtr) == TRUE
    /**
     * The widget to which this window is attached. Examples of places where specifying this relation is useful are
     * for instance a GtkMenu created by a GtkComboBox, a completion popup window created by
     * [org.guivista.core.widget.data_entry.Entry], or a type ahead search entry created by GtkTreeView.
     */
    var attachedTo: Widget?
        get() {
            val tmp = gtk_window_get_attached_to(gtkWindowPtr)
            return if (tmp != null) Widget(tmp) else null
        }
        set(value) = gtk_window_set_attached_to(gtkWindowPtr, value?.gtkWidgetPtr)
    /** Whether the [Window] should be decorated by the window manager. Default value is *true*. */
    var decorated: Boolean
        get() = gtk_window_get_decorated(gtkWindowPtr) == TRUE
        set(value) = gtk_window_set_decorated(gtkWindowPtr, if (value) TRUE else FALSE)
    /** Whether the window frame should have a close button. Default value is *true*. */
    var deletable: Boolean
        get() = gtk_window_get_deletable(gtkWindowPtr) == TRUE
        set(value) = gtk_window_set_deletable(gtkWindowPtr, if (value) TRUE else FALSE)
    /** If this window should be destroyed when the parent is destroyed. */
    var destroyWithParent: Boolean
        get() = gtk_window_get_destroy_with_parent(gtkWindowPtr) == TRUE
        set(value) = gtk_window_set_destroy_with_parent(gtkWindowPtr, if (value) TRUE else FALSE)
    /** Whether the window should receive the input focus when mapped. Default value is *true*. */
    var focusOnMap: Boolean
        get() = gtk_window_get_focus_on_map(gtkWindowPtr) == TRUE
        set(value) = gtk_window_set_focus_on_map(gtkWindowPtr, if (value) TRUE else FALSE)
    /**
     * Whether 'focus rectangles' are currently visible in this [Window]. This property is maintained by GTK based on
     * user input and should **NOT** be set by applications!
     */
    var focusVisible: Boolean
        get() = gtk_window_get_focus_visible(gtkWindowPtr) == TRUE
        set(value) = gtk_window_set_focus_visible(gtkWindowPtr, if (value) TRUE else FALSE)
    /**
     * The window gravity of the window. Default value is *GdkGravity.GDK_GRAVITY_NORTH_WEST*. See [move], and
     * GdkGravity for more details about window gravity.
     */
    var gravity: GdkGravity
        get() = gtk_window_get_gravity(gtkWindowPtr)
        set(value) = gtk_window_set_gravity(gtkWindowPtr, value)
    /** Whether the titlebar should be hidden during maximization. */
    var hideTitleBarWhenMaximized: Boolean
        get() = gtk_window_get_hide_titlebar_when_maximized(gtkWindowPtr) == TRUE
        set(value) = gtk_window_set_hide_titlebar_when_maximized(gtkWindowPtr, if (value) TRUE else FALSE)
    /**
     * This property specifies the name of the themed icon to use as the window icon. Default value is *""*
     * (an empty String). See GtkIconTheme for more details.
     */
    var iconName: String
        get() = gtk_window_get_icon_name(gtkWindowPtr)?.toKString() ?: ""
        set(value) = gtk_window_set_icon_name(gtkWindowPtr, value)
    /** Whether the top level is the current active window. Default value is *false*. */
    val isActive: Boolean
        get() = gtk_window_is_active(gtkWindowPtr) == TRUE
    /**
     * Whether mnemonics are currently visible in this window. Default value is *true*. This property is maintained by
     * GTK based on user input, and should **NOT** be set by applications!
     */
    var mnemonicsVisible: Boolean
        get() = gtk_window_get_mnemonics_visible(gtkWindowPtr) == TRUE
        set(value) = gtk_window_set_mnemonics_visible(gtkWindowPtr, if (value) TRUE else FALSE)
    /** If TRUE, the window is modal (other windows are not usable while this one is up). Default value is *false*. */
    var modal: Boolean
        get() = gtk_window_get_modal(gtkWindowPtr) == TRUE
        set(value) = gtk_window_set_modal(gtkWindowPtr, if (value) TRUE else FALSE)
    /**
     * Unique identifier for the [Window] to be used when restoring a session. Default value is *""* (an empty String).
     */
    var role: String
        get() = gtk_window_get_role(gtkWindowPtr)?.toKString() ?: ""
        set(value) = gtk_window_set_role(gtkWindowPtr, value)
    /** When *true* the window should not be in the pager. Default value is *false*. */
    var skipPagerHint: Boolean
        get() = gtk_window_get_skip_pager_hint(gtkWindowPtr) == TRUE
        set(value) = gtk_window_set_skip_pager_hint(gtkWindowPtr, if (value) TRUE else FALSE)
    /** When *true* the window should not be in the task bar. Default value is *false*. */
    var skipTaskBarHint: Boolean
        get() = gtk_window_get_skip_taskbar_hint(gtkWindowPtr) == TRUE
        set(value) = gtk_window_set_skip_taskbar_hint(gtkWindowPtr, if (value) TRUE else FALSE)
    /**
     * Hint to help the desktop environment understand what kind of window this is, and how to treat it. Default value
     * is *GdkWindowTypeHint.GDK_WINDOW_TYPE_HINT_NORMAL*.
     */
    var typeHint: GdkWindowTypeHint
        get() = gtk_window_get_type_hint(gtkWindowPtr)
        set(value) = gtk_window_set_type_hint(gtkWindowPtr, value)
    /** When *true* the window should be brought to the user's attention. Default value is *false*. */
    var urgencyHint: Boolean
        get() = gtk_window_get_urgency_hint(gtkWindowPtr) == TRUE
        set(value) = gtk_window_set_urgency_hint(gtkWindowPtr, if (value) TRUE else FALSE)

    /**
     * This function returns the position you need to pass to [move] to keep window in its current position. This means
     * that the meaning of the returned value varies with window gravity. The reliability of this function depends on
     * the windowing system currently in use. Some windowing systems such as Wayland do not support a global coordinate
     * system, and thus the position of the window will always be (0, 0). Others, like X11 do not have a reliable way
     * to obtain the geometry of the decorations of a window, if they are provided by the window manager. Additionally
     * on X11 the window manager has been known to mismanage window gravity, which result in windows moving even if you
     * use the coordinates of the current position as returned by this function.
     *
     * If you haven’t changed the window gravity, its gravity will be GDK_GRAVITY_NORTH_WEST. This means that this
     * function gets the position of the top left corner of the window manager frame for the window. The [move]
     * function sets the position of this same top left corner.
     *
     * If a window has gravity GDK_GRAVITY_STATIC the window manager frame is not relevant, and thus this function will
     * always produce accurate results. However you can’t use static gravity to do things like place a window in a
     * corner of the screen because static gravity ignores the window manager decorations.
     *
     * Ideally this function should return appropriate values if the window has client side decorations. Assuming that
     * the windowing system supports global coordinates. In practice saving the window position should not be left to
     * applications as they lack enough knowledge of the windowing system, and the window manager state to effectively
     * do so. The appropriate way to implement saving the window position is to use a platform specific protocol,
     * wherever that is available.
     * @return The current position as a Pair with the first element being the root x coordinate, and the last element
     * being the root y coordinate.
     * @see move
     */
    fun fetchPosition(): Pair<Int, Int> = memScoped {
        val rootX = alloc<gintVar>()
        val rootY = alloc<gintVar>()
        gtk_window_get_position(window = gtkWindowPtr, root_x = rootX.ptr, root_y = rootY.ptr)
        return rootX.value to rootY.value
    }

    /**
     * Asks the window manager to move window to the given position. Window managers are free to ignore this. Most
     * window managers ignore requests for initial window positions (instead using a user defined placement algorithm),
     * and honor requests after the window has already been shown. **Note**: the position is the position of the
     * gravity determined reference point for the window. The gravity determines two things: first, the location of the
     * reference point in root window coordinates, and second which point on the window is positioned at the reference
     * point.
     *
     * By default the gravity is GDK_GRAVITY_NORTH_WEST, so the reference point is simply the x/y supplied to this
     * function. The top left corner of the window decorations (aka window frame or border) will be placed at x/y.
     * Therefore to position a window at the top left of the screen you want to use the default gravity
     * (which is GDK_GRAVITY_NORTH_WEST), and move the window to 0,0.
     *
     * To position a window at the bottom right corner of the screen you would set GDK_GRAVITY_SOUTH_EAST, which means
     * that the reference point is at x + the window width, and y + the window height, and the bottom-right corner of
     * the window border will be placed at that reference point. So to place a window in the bottom right corner you
     * would first set gravity to south east, then write:
     * `move(window, gdk_screen_width() - window_width, gdk_screen_height() - window_height)`. Do note that this example
     * doesn't take multi-head scenarios into account.
     * @param x The X coordinate to move the [Window] to.
     * @param y The Y coordinate to move the [Window] to.
     */
    fun move(x: Int, y: Int) {
        gtk_window_move(window = gtkWindowPtr, x = x, y = y)
    }

    /**
     * Changes the [window's][Window] position.
     * @param position The window position to use.
     */
    fun changePosition(position: GtkWindowPosition) {
        gtk_window_set_position(gtkWindowPtr, position)
    }

    /**
     * Changes the [window's][Window] startup notification identifier.
     * @param startupId The startup identifier to use.
     */
    fun changeStartupId(startupId: String) {
        gtk_window_set_startup_id(gtkWindowPtr, startupId)
    }

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
        gtk_window_set_default_size(gtkWindowPtr, width, height)
    }

    /** Closes the window. */
    fun close() {
        gtk_window_close(gtkWindowPtr)
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
        gtk_window_maximize(gtkWindowPtr)
    }

    /**
     * Asks to unmaximize window . Note that you shouldn’t assume the window is definitely unmaximized afterward
     * because other entities (e.g. the user or window manager) could maximize it again, and not all window managers
     * honor requests to unmaximize. But normally the window will end up unmaximized. Just don’t write code that
     * crashes if not. You can track maximization via the “window-state-event” signal on GtkWidget.
     */
    fun unmaximize() {
        gtk_window_unmaximize(gtkWindowPtr)
    }

    /** Adds a new child to the window. */
    fun addChild(widget: WidgetBase) {
        gtk_container_add(gtkContainerPtr, widget.gtkWidgetPtr)
    }

    /** Removes a child from the window. */
    fun removeChild(widget: WidgetBase) {
        gtk_container_remove(gtkContainerPtr, widget.gtkWidgetPtr)
    }

    open fun resetFocus() {}

    open fun createMainLayout(): Container? = null

    open fun createUi(init: Window.() -> Unit) {
        _gtkWidgetPtr = gtk_window_new(winType)
        this.init()
        val mainLayout = createMainLayout()
        if (mainLayout != null) addChild(mainLayout)
        if (visible) {
            showAll()
            resetFocus()
        }
    }
}
