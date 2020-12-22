package org.guiVista.gui.widget

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import org.guiVista.core.ObjectBase
import org.guiVista.core.connectGSignal

private const val GRAB_FOCUS_SIGNAL = "grab-focus"
private const val SHOW_SIGNAL = "show"
private const val HIDE_SIGNAL = "hide"
private const val CONFIGURE_EVENT_SIGNAL = "configure-event"
private const val KEY_PRESS_EVENT_SIGNAL = "key-press-event"
private const val KEY_RELEASE_EVENT_SIGNAL = "key-release-event"

/** Base interface for all widget's (controls). */
public actual interface WidgetBase : ObjectBase {
    public val gtkWidgetPtr: CPointer<GtkWidget>?

    /** If set to *true* then the widget can accept the input focus. Default value is *false*. */
    public var canFocus: Boolean
        set(value) = gtk_widget_set_can_focus(gtkWidgetPtr, if (value) TRUE else FALSE)
        get() = gtk_widget_get_can_focus(gtkWidgetPtr) == TRUE

    /**
     * Enables or disables the emission of “query-tooltip” on widget. A value of *true* indicates that widget can have
     * a tooltip, in this case the widget will be queried using “query-tooltip” to determine whether it will provide a
     * tooltip or not. Note that setting this property to *true* for the first time will change the event masks of the
     * GdkWindows of this widget to include leave-notify and motion-notify events. This cannot and will not be undone
     * when the property is set to *false* again.
     *
     * Default value is *false*.
     */
    public var hasTooltip: Boolean
        set(value) = gtk_widget_set_has_tooltip(gtkWidgetPtr, if (value) TRUE else FALSE)
        get() = gtk_widget_get_has_tooltip(gtkWidgetPtr) == TRUE

    /**
     * Sets the text of tooltip to be the given string. Property “has-tooltip” will automatically be set to *true* and
     * there will be taken care of “query-tooltip” in the default signal handler. Note that if both “tooltip-text” and
     * “tooltip-markup” are set, the last one wins. Default value is *""* (an empty String).
     */
    public var tooltipText: String
        set(value) = gtk_widget_set_tooltip_text(gtkWidgetPtr, value)
        get() = gtk_widget_get_tooltip_text(gtkWidgetPtr)?.toKString() ?: ""

    /** If set to *true* then the widget is visible. Default value is *false*. **/
    public var visible: Boolean
        set(value) = gtk_widget_set_visible(gtkWidgetPtr, if (value) TRUE else FALSE)
        get() = gtk_widget_get_visible(gtkWidgetPtr) == TRUE

    /**
     * Margin on bottom side of widget. This property adds margin outside of the widget's normal size request, the
     * margin will be added in addition to the size from `gtk_widget_set_size_request()` for example. Default value is
     * *0*.
     */
    public var marginBottom: Int
        set(value) {
            if (value in marginRange) gtk_widget_set_margin_bottom(gtkWidgetPtr, value)
        }
        get() = gtk_widget_get_margin_bottom(gtkWidgetPtr)

    /**
     * Margin on end of widget, horizontally. This property supports left-to-right and right-to-left text directions.
     * This property adds margin outside of the widget's normal size request, the margin will be added in addition to
     * the size from `gtk_widget_set_size_request()` for example. Default value is *0*.
     */
    public var marginEnd: Int
        set(value) {
            if (value in marginRange) gtk_widget_set_margin_end(gtkWidgetPtr, value)
        }
        get() = gtk_widget_get_margin_end(gtkWidgetPtr)

    /**
     * Margin on start of widget, horizontally. This property supports left-to-right and right-to-left text directions.
     * This property adds margin outside of the widget's normal size request, the margin will be added in addition to
     * the size from `gtk_widget_set_size_request()` for example. Default value is *0*.
     */
    public var marginStart: Int
        set(value) {
            if (value in marginRange) gtk_widget_set_margin_start(gtkWidgetPtr, value)
        }
        get() = gtk_widget_get_margin_start(gtkWidgetPtr)

    /**
     * Margin on top side of widget. This property adds margin outside of the widget's normal size request, the margin
     * will be added in addition to the size from `gtk_widget_set_size_request()` for example. Default value is *0*.
     */
    public var marginTop: Int
        set(value) {
            if (value in marginRange) gtk_widget_set_margin_top(gtkWidgetPtr, value)
        }
        get() = gtk_widget_get_margin_top(gtkWidgetPtr)

    /** Whether the application will paint directly on the widget. Default value is *false*. */
    public var appPaintable: Boolean
        get() = gtk_widget_get_app_paintable(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_app_paintable(gtkWidgetPtr, if (value) TRUE else FALSE)

    /** Whether the widget can be the default widget. Default value is *false*. */
    public var canDefault: Boolean
        get() = gtk_widget_get_can_default(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_can_default(gtkWidgetPtr, if (value) TRUE else FALSE)

    /**
     * Whether the widget should grab focus when it is clicked with the mouse. This property is only relevant for
     * widgets that can take focus. Before 3.20 several widgets (GtkButton, GtkFileChooserButton, GtkComboBox)
     * implemented this property individually.
     *
     * Default value is *true*.
     */
    public var focusOnClick: Boolean
        get() = gtk_widget_get_focus_on_click(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_focus_on_click(gtkWidgetPtr, if (value) TRUE else FALSE)

    /** How to distribute horizontal space if widget gets extra space. */
    public var hAlign: GtkAlign
        get() = gtk_widget_get_halign(gtkWidgetPtr)
        set(value) = gtk_widget_set_halign(gtkWidgetPtr, value)

    /** Whether the widget is the default widget. Default value is *false*. */
    public val hasDefault: Boolean
        get() = gtk_widget_has_default(gtkWidgetPtr) == TRUE

    /** Whether the widget has the input focus. Default value is *false*. */
    public val hasFocus: Boolean
        get() = gtk_widget_has_focus(gtkWidgetPtr) == TRUE

    /** Whether to expand horizontally. Default value is *false*. */
    public var hExpand: Boolean
        get() = gtk_widget_get_hexpand(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_hexpand(gtkWidgetPtr, if (value) TRUE else FALSE)

    /** Whether to use the [hExpand] property. Default value is *false*. */
    public var hExpandSet: Boolean
        get() = gtk_widget_get_hexpand_set(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_hexpand_set(gtkWidgetPtr, if (value) TRUE else FALSE)

    /** Whether the widget is the focus widget within the top level. Default value is *false*. */
    public val isFocus: Boolean
        get() = gtk_widget_is_focus(gtkWidgetPtr) == TRUE

    /** The name of the widget. Default value is *""* (an empty String). */
    public var name: String
        get() = gtk_widget_get_name(gtkWidgetPtr)?.toKString() ?: ""
        set(value) = gtk_widget_set_name(gtkWidgetPtr, value)

    /** Whether the [showAll] function should not affect this widget. Default value is *false*. */
    public var noShowAll: Boolean
        get() = gtk_widget_get_no_show_all(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_no_show_all(gtkWidgetPtr, if (value) TRUE else FALSE)

    /**
     * The requested opacity of the widget. Before 3.8 this was only available in GtkWindow. Default value is *1.0*.
     */
    public var opacity: Double
        get() = gtk_widget_get_opacity(gtkWidgetPtr)
        set(value) {
            if (value in (0.0..1.0)) gtk_widget_set_opacity(gtkWidgetPtr, value)
        }

    /** If *true* then the widget will receive the default action when it is focused. Default value is *false*. */
    public var receivesDefault: Boolean
        get() = gtk_widget_get_receives_default(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_receives_default(gtkWidgetPtr, if (value) TRUE else FALSE)

    /** The scale factor of the widget. */
    public val scaleFactor: Int
        get() = gtk_widget_get_scale_factor(gtkWidgetPtr)

    /** Whether the widget responds to input. */
    public var sensitive: Boolean
        get() = gtk_widget_get_sensitive(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_sensitive(gtkWidgetPtr, if (value) TRUE else FALSE)

    /**
     * Sets the text of tooltip to be the given string, which is marked up with the Pango text markup language. Also
     * see `gtk_tooltip_set_markup()`. This is a convenience property which will take care of getting the tooltip
     * shown if the given string isn't *null*, [hasTooltip] will automatically be set to *true*, and the
     * "query-tooltip" signal will be handled in the default signal handler. Note that if both [tooltipText], and
     * [tooltipMarkup] are set the last one wins.
     *
     * Default value is *""* (an empty String).
     */
    public var tooltipMarkup: String
        get() = gtk_widget_get_tooltip_markup(gtkWidgetPtr)?.toKString() ?: ""
        set(value) = gtk_widget_set_tooltip_markup(gtkWidgetPtr, value)

    /** How to distribute vertical space if widget gets extra space. Default value is *GtkAlign.GTK_ALIGN_FILL*. */
    public var vAlign: GtkAlign
        get() = gtk_widget_get_valign(gtkWidgetPtr)
        set(value) = gtk_widget_set_valign(gtkWidgetPtr, value)

    /** Whether to expand vertically. Default value is *false*. */
    public var vExpand: Boolean
        get() = gtk_widget_get_vexpand(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_vexpand(gtkWidgetPtr, if (value) TRUE else FALSE)

    /** Whether to use the [vExpand] property. Default value is *false*. */
    public var vExpandSet: Boolean
        get() = gtk_widget_get_vexpand_set(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_vexpand_set(gtkWidgetPtr, if (value) TRUE else FALSE)

    private companion object {
        private val marginRange = 0..32767
    }

    /** Changes all margins for the widget. */
    public fun changeMargins(bottom: Int, end: Int, start: Int, top: Int) {
        marginBottom = bottom
        marginEnd = end
        marginStart = start
        marginTop = top
    }

    /** Changes all margins for the widget using a single value. */
    public fun changeAllMargins(value: Int) {
        marginBottom = value
        marginEnd = value
        marginStart = value
        marginTop = value
    }

    /** Clears all the widget's margins (bottom, end, start, and top). */
    public fun clearMargins() {
        marginBottom = 0
        marginEnd = 0
        marginStart = 0
        marginTop = 0
    }

    /**
     * Causes widget to have the keyboard focus for the GtkWindow it's inside. Widget must be a focusable widget such
     * as a GtkEntry; something like GtkFrame won’t work. More precisely it must have the `GTK_CAN_FOCUS` flag set. Use
     * `gtk_widget_set_can_focus()` to modify that flag.
     *
     * The widget also needs to be realized and mapped. This is indicated by the related signals. Grabbing the focus
     * immediately after creating the widget will likely fail and cause critical warnings.
     */
    public fun grabFocus() {
        gtk_widget_grab_focus(gtkWidgetPtr)
    }

    /**
     * Connects the *grab-focus* signal to a [slot] on a [WidgetBase]. This signal is used when a widget is
     * "grabbing" focus.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectGrabFocusSignal(slot: CPointer<GrabFocusSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkWidgetPtr, signal = GRAB_FOCUS_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *show* signal to a [slot] on a [WidgetBase]. This signal is used when a widget is shown.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectShowSignal(slot: CPointer<ShowSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkWidgetPtr, signal = SHOW_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *hide* signal to a [slot] on a [WidgetBase]. This signal is used when a widget is hidden.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectHideSignal(slot: CPointer<HideSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkWidgetPtr, signal = HIDE_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *configure-event* signal to a [slot] on a [WidgetBase]. This signal is used when the size, position
     * or stacking of the widget's window has changed. To receive this signal, the GdkWindow associated to the widget
     * needs to enable the `GDK_STRUCTURE_MASK` mask. GDK will enable this mask automatically for all new windows.
     */
    public fun connectConfigureEventSignal(slot: CPointer<ConfigureEventSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkWidgetPtr, signal = CONFIGURE_EVENT_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *key-press-event* signal to a [slot] on a [WidgetBase]. This signal is used when a key is pressed.
     * The signal emission will reoccur at the key-repeat rate when the key is kept pressed. To receive this signal the
     * GdkWindow associated to the widget needs to enable the GDK_KEY_PRESS_MASK mask.
     *
     * This signal will be sent to the grab widget if there is one.
     */
    public fun connectKeyPressEventSignal(slot: CPointer<KeyPressEventSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkWidgetPtr, signal = KEY_PRESS_EVENT_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *key-release-event* signal to a [slot] on a [WidgetBase]. This signal is used when a key is
     * released. To receive this signal the GdkWindow associated to the widget needs to enable the
     * `GDK_KEY_RELEASE_MASK` mask. This signal will be sent to the grab widget if there is one.
     */
    public fun connectKeyReleaseEventSignal(slot: CPointer<KeyReleaseEventSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkWidgetPtr, signal = KEY_RELEASE_EVENT_SIGNAL, slot = slot, data = userData)

    /**
     * Recursively shows a widget, and any child widgets (if the [widget][WidgetBase] is a
     * [container][org.guiVista.gui.layout.Container]).
     */
    public fun showAll() {
        gtk_widget_show_all(gtkWidgetPtr)
    }

    override fun close() {
        gtk_widget_destroy(gtkWidgetPtr)
    }
}

/**
 * The event handler for the *grab-focus* signal. Arguments:
 * 1. widget: CPointer<GtkWidget>
 * 2. userData: gpointer
 */
public typealias GrabFocusSlot = CFunction<(widget: CPointer<GtkWidget>, userData: gpointer) -> Unit>

/**
 * The event handler for the *show* signal. Arguments:
 * 1. widget: CPointer<GtkWidget>
 * 2. userData: gpointer
 */
public typealias ShowSlot = CFunction<(widget: CPointer<GtkWidget>, userData: gpointer) -> Unit>

/**
 * The event handler for the *hide* signal. Arguments:
 * 1. widget: CPointer<GtkWidget>
 * 2. userData: gpointer
 */
public typealias HideSlot = CFunction<(widget: CPointer<GtkWidget>, userData: gpointer) -> Unit>

/**
 * The event handler for the *configure-event* signal. Arguments:
 * 1. widget: CPointer<GtkWidget>
 * 2. event: CPointer<GdkEvent>
 * 3. userData: gpointer
 *
 * Return *true* to stop other handlers from being invoked for the event, otherwise *false* to propagate the event
 * further.
 */
public typealias ConfigureEventSlot =
    CFunction<(widget: CPointer<GtkWidget>, event: CPointer<GdkEvent>, userData: gpointer) -> Boolean>

/**
 * The event handler for the *key-press-event* signal. Arguments:
 * 1. widget: CPointer<GtkWidget>
 * 2. event: CPointer<GdkEvent>
 * 3. userData: gpointer
 *
 * Return *true* to stop other handlers from being invoked for the event, otherwise *false* to propagate the event
 * further.
 */
public typealias KeyPressEventSlot =
    CFunction<(widget: CPointer<GtkWidget>, event: CPointer<GdkEvent>, userData: gpointer) -> Boolean>

/**
 * The event handler for the *key-release-event* signal. Arguments:
 * 1. widget: CPointer<GtkWidget>
 * 2. event: CPointer<GdkEvent>
 * 3. userData: gpointer
 *
 * Return *true* to stop other handlers from being invoked for the event, otherwise *false* to propagate the event
 * further.
 */
public typealias KeyReleaseEventSlot =
    CFunction<(widget: CPointer<GtkWidget>, event: CPointer<GdkEvent>, userData: gpointer) -> Boolean>
