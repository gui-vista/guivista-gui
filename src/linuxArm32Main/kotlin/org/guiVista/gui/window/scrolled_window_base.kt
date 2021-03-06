package org.guiVista.gui.window

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.*
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal
import org.guiVista.gui.Adjustment
import org.guiVista.gui.layout.Container
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase

public actual interface ScrolledWindowBase : Container {
    public val gtkScrolledWindowPtr: CPointer<GtkScrolledWindow>?
        get() = gtkWidgetPtr?.reinterpret()

    /** Whether button presses are captured during kinetic scrolling. */
    public var captureButtonPress: Boolean
        get() = gtk_scrolled_window_get_capture_button_press(gtkScrolledWindowPtr) == TRUE
        set(value) = gtk_scrolled_window_set_capture_button_press(gtkScrolledWindowPtr, if (value) TRUE else FALSE)

    /** The placement of the contents with respect to the scrollbars for the scrolled window. */
    public var placement: GtkCornerType
        set(value) = gtk_scrolled_window_set_placement(gtkScrolledWindowPtr, value)
        get() = gtk_scrolled_window_get_placement(gtkScrolledWindowPtr)

    /** The adjustment for the horizontal position. */
    public var hAdjustment: Adjustment
        get() = Adjustment(gtk_scrolled_window_get_hadjustment(gtkScrolledWindowPtr))
        set(value) = gtk_scrolled_window_set_hadjustment(gtkScrolledWindowPtr, value.gtkAdjustmentPtr)

    /**
     * Whether kinetic scrolling is enabled or not. Kinetic scrolling only applies to devices with source
     * `GDK_SOURCE_TOUCHSCREEN`. Default value is *true*.
     */
    public var kineticScrolling: Boolean
        get() = gtk_scrolled_window_get_kinetic_scrolling(gtkScrolledWindowPtr) == TRUE
        set(value) = gtk_scrolled_window_set_kinetic_scrolling(gtkScrolledWindowPtr, if (value) TRUE else FALSE)

    /**
     * The minimum content height of scrolled_window, or *-1* if not set. Default value is *-1*.
     */
    public var minContentHeight: Int
        get() = gtk_scrolled_window_get_min_content_height(gtkScrolledWindowPtr)
        set(value) {
            if (value < -1) throw IllegalArgumentException("The minContentHeight property must be set to >= -1")
            else gtk_scrolled_window_set_min_content_height(gtkScrolledWindowPtr, value)
        }

    /**
     * The minimum content width of scrolled_window, or *-1* if not set. Default value is *-1*.
     */
    public var minContentWidth: Int
        get() = gtk_scrolled_window_get_min_content_width(gtkScrolledWindowPtr)
        set(value) {
            if (value < -1) throw IllegalArgumentException("The minContentWidth property must be set to >= -1")
            else gtk_scrolled_window_set_min_content_width(gtkScrolledWindowPtr, value)
        }

    /**
     * Whether overlay scrolling is enabled or not. If it is then the scrollbars are only added as traditional widgets
     * when a mouse is present. Otherwise they are overlayed on top of the content, as narrow indicators. Default value
     * is *true*.
     */
    public var overlayScrolling: Boolean
        get() = gtk_scrolled_window_get_overlay_scrolling(gtkScrolledWindowPtr) == TRUE
        set(value) = gtk_scrolled_window_set_overlay_scrolling(gtkScrolledWindowPtr, if (value) TRUE else FALSE)

    /** Style of bevel around the contents. Default value is *GTK_SHADOW_NONE*. */
    public var shadowType: GtkShadowType
        get() = gtk_scrolled_window_get_shadow_type(gtkScrolledWindowPtr)
        set(value) = gtk_scrolled_window_set_shadow_type(gtkScrolledWindowPtr, value)

    /** The GtkAdjustment for the vertical position. */
    public var vAdjustment: Adjustment
        get() = Adjustment(gtk_scrolled_window_get_vadjustment(gtkScrolledWindowPtr))
        set(value) = gtk_scrolled_window_set_vadjustment(gtkScrolledWindowPtr, value.gtkAdjustmentPtr)

    /** The horizontal scrollbar of the scrolled window. */
    public val hScrollBar: WidgetBase?
        get() {
            val ptr = gtk_scrolled_window_get_hscrollbar(gtkScrolledWindowPtr)
            return if (ptr != null) Widget(ptr) else null
        }

    /** The vertical scrollbar of the scrolled window. */
    public val vScrollBar: WidgetBase?
        get() {
            val ptr = gtk_scrolled_window_get_vscrollbar(gtkScrolledWindowPtr)
            return if (ptr != null) Widget(ptr) else null
        }

    /**
     * Sets the scrollbar policy for the horizontal and vertical scrollbars. The policy determines when the scrollbar
     * should appear; it is a value from the `GtkPolicyType` enumeration. If *GTK_POLICY_ALWAYS* then the scrollbar is
     * always present; if *GTK_POLICY_NEVER* then the scrollbar is never present; if *GTK_POLICY_AUTOMATIC* then the
     * scrollbar is present only if needed (that is, if the slider part of the bar would be smaller than the trough —
     * the display is larger than the page size).
     * @param hScrollBarPolicy Policy for horizontal bar.
     * @param vScrollBarPolicy Policy for vertical bar.
     */
    public fun changePolicy(hScrollBarPolicy: GtkPolicyType, vScrollBarPolicy: GtkPolicyType) {
        gtk_scrolled_window_set_policy(scrolled_window = gtkScrolledWindowPtr, hscrollbar_policy = hScrollBarPolicy,
            vscrollbar_policy = vScrollBarPolicy)
    }

    /**
     * Unsets the placement of the contents with respect to the scrollbars for the scrolled window. If no window
     * placement is set for a scrolled window, it defaults to GTK_CORNER_TOP_LEFT.
     * @see placement
     */
    public fun unsetPlacement() {
        gtk_scrolled_window_unset_placement(gtkScrolledWindowPtr)
    }

    /**
     * Retrieves the current policy values for the horizontal and vertical scrollbars.
     * @return A Pair with first element being hScrollBarPolicy, and last element being vScrollBarPolicy.
     * @see changePolicy
     */
    public fun fetchPolicy(): Pair<GtkPolicyType, GtkPolicyType> = memScoped {
        val hScrollBarPolicy = alloc<GtkPolicyType.Var>()
        val vScrollBarPolicy = alloc<GtkPolicyType.Var>()
        gtk_scrolled_window_get_policy(
            scrolled_window = gtkScrolledWindowPtr,
            hscrollbar_policy = hScrollBarPolicy.ptr,
            vscrollbar_policy = vScrollBarPolicy.ptr
        )
        hScrollBarPolicy.value to vScrollBarPolicy.value
    }

    /**
     * Connects the *edge-overshot* event to a [handler] on a scrolled window. The event occurs whenever user initiated
     * scrolling makes the scrolled window firmly surpass (ie. with some edge resistance) the lower or upper limits
     * defined by the adjustment in that orientation. A similar behavior without edge resistance is provided by the
     * **edge-reached** event.
     *
     * Note the pos argument is LTR/RTL aware, so callers should be aware too if intending to provide behavior on
     * horizontal edges.
     * @param handler The event handler for the evnet.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectEdgeOvershotEvent(handler: CPointer<EdgeOvershotHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkScrolledWindowPtr, signal = ScrolledWindowBaseEvent.edgeOvershot, slot = handler,
            data = userData).toULong()

    /**
     * Connects the *edge-reached* event to a [handler] on a scrolled window. The event occurs whenever user-initiated
     * scrolling makes the scrolled window exactly reaches the lower or upper limits defined by the adjustment in that
     * orientation. A similar behavior with edge resistance is provided by the **edge-overshot** event.
     *
     * Note the pos argument is LTR/RTL aware, so callers should be aware too if intending to provide behavior on
     * horizontal edges.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectEdgeReachedEvent(handler: CPointer<EdgeReachedHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkScrolledWindowPtr, signal = ScrolledWindowBaseEvent.edgeReached, slot = handler,
            data = userData).toULong()


    /**
     * Connects the *move-focus-out* event to a [handler] on a scrolled window. This event is a keybinding event which
     * occurs when focus is moved away from the scrolled window by a keybinding. The **move-focus** event is emitted
     * with direction_type on this scrolled windows top level parent in the container hierarchy. The default bindings
     * for this event are `Tab + Ctrl` and `Tab + Ctrl + Shift`.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectMoveFocusOutEvent(handler: CPointer<MoveFocusOutHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkScrolledWindowPtr, signal = ScrolledWindowBaseEvent.moveFocusOut, slot = handler,
            data = userData).toULong()


    /**
     * Connects the *scroll-child* event to a [handler] on a scrolled window. This event is a keybinding event which
     * occurs when a keybinding that scrolls is pressed. The horizontal or vertical adjustment is updated, which
     * triggers a event that the scrolled windows child may listen to and scroll itself.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectScrollChildEvent(handler: CPointer<ScrollChildHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkScrolledWindowPtr, signal = ScrolledWindowBaseEvent.scrollChild, slot = handler,
            data = userData).toULong()

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkScrolledWindowPtr, handlerId.toUInt())
    }
}

/**
 * The event handler for the *edge-overshot* event. Arguments:
 * 1. scrolledWin: CPointer<GtkScrolledWin>
 * 2. pos: GtkPositionType
 * 3. userData: gpointer
 */
public typealias EdgeOvershotHandler = CFunction<(
    scrolledWin: CPointer<GtkScrolledWindow>,
    pos: GtkPositionType,
    userData: gpointer
) -> Unit>

/**
 * The event handler for the *edge-reached* event. Arguments:
 * 1. scrolledWin: CPointer<GtkScrolledWin>
 * 2. pos: GtkPositionType
 * 3. userData: gpointer
 */
public typealias EdgeReachedHandler = CFunction<(
    scrolledWin: CPointer<GtkScrolledWindow>,
    pos: GtkPositionType,
    userData: gpointer
) -> Unit>

/**
 * The event handler for the *move-focus-out* event. Arguments:
 * 1. scrolledWin: CPointer<GtkScrolledWin>
 * 2. directionType: GtkDirectionType
 * 3. userData: gpointer
 */
public typealias MoveFocusOutHandler = CFunction<(
    scrolledWin: CPointer<GtkScrolledWindow>,
    directionType: GtkDirectionType,
    userData: gpointer
) -> Unit>

/**
 * The event handler for the *scroll-child* event. Arguments:
 * 1. scrolledWin: CPointer<GtkScrolledWin>
 * 2. scroll: GtkScrollType
 * 3. horizontal: Int (a "fake" Boolean)
 * 4. userData: gpointer
 */
public typealias ScrollChildHandler = CFunction<(
    scrolledWin: CPointer<GtkScrolledWindow>,
    scroll: GtkScrollType,
    horizontal: Int,
    userData: gpointer
) -> Unit>
