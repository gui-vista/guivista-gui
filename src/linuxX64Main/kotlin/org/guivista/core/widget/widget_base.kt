package org.guivista.core.widget

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import org.guivista.core.ObjectBase
import org.guivista.core.connectGSignal

private const val GRAB_FOCUS_SIGNAL = "grab-focus"
private const val SHOW_SIGNAL = "show"
private const val HIDE_SIGNAL = "hide"

/** Base interface for all widget's (controls). */
interface WidgetBase : ObjectBase {
    val gtkWidgetPtr: CPointer<GtkWidget>?
    /** If set to *true* then the widget can accept the input focus. Default value is *false*. */
    var canFocus: Boolean
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
    var hasTooltip: Boolean
        set(value) = gtk_widget_set_has_tooltip(gtkWidgetPtr, if (value) TRUE else FALSE)
        get() = gtk_widget_get_has_tooltip(gtkWidgetPtr) == TRUE
    /**
     * Sets the text of tooltip to be the given string. Property “has-tooltip” will automatically be set to *true* and
     * there will be taken care of “query-tooltip” in the default signal handler. Note that if both “tooltip-text” and
     * “tooltip-markup” are set, the last one wins. Default value is *""* (an empty String).
     */
    var tooltipText: String
        set(value) = gtk_widget_set_tooltip_text(gtkWidgetPtr, value)
        get() = gtk_widget_get_tooltip_text(gtkWidgetPtr)?.toKString() ?: ""
    /** If set to *true* then the widget is visible. Default value is *false*. **/
    var visible: Boolean
        set(value) = gtk_widget_set_visible(gtkWidgetPtr, if (value) TRUE else FALSE)
        get() = gtk_widget_get_visible(gtkWidgetPtr) == TRUE
    /**
     * Margin on bottom side of widget. This property adds margin outside of the widget's normal size request, the
     * margin will be added in addition to the size from `gtk_widget_set_size_request()` for example. Default value is
     * *0*.
     */
    var marginBottom: Int
        set(value) {
            if (value in marginRange) gtk_widget_set_margin_bottom(gtkWidgetPtr, value)
        }
        get() = gtk_widget_get_margin_bottom(gtkWidgetPtr)
    /**
     * Margin on end of widget, horizontally. This property supports left-to-right and right-to-left text directions.
     * This property adds margin outside of the widget's normal size request, the margin will be added in addition to
     * the size from `gtk_widget_set_size_request()` for example. Default value is *0*.
     */
    var marginEnd: Int
        set(value) {
            if (value in marginRange) gtk_widget_set_margin_end(gtkWidgetPtr, value)
        }
        get() = gtk_widget_get_margin_end(gtkWidgetPtr)
    /**
     * Margin on start of widget, horizontally. This property supports left-to-right and right-to-left text directions.
     * This property adds margin outside of the widget's normal size request, the margin will be added in addition to
     * the size from `gtk_widget_set_size_request()` for example. Default value is *0*.
     */
    var marginStart: Int
        set(value) {
            if (value in marginRange) gtk_widget_set_margin_start(gtkWidgetPtr, value)
        }
        get() = gtk_widget_get_margin_start(gtkWidgetPtr)
    /**
     * Margin on top side of widget. This property adds margin outside of the widget's normal size request, the margin
     * will be added in addition to the size from `gtk_widget_set_size_request()` for example. Default value is *0*.
     */
    var marginTop: Int
        set(value) {
            if (value in marginRange) gtk_widget_set_margin_top(gtkWidgetPtr, value)
        }
        get() = gtk_widget_get_margin_top(gtkWidgetPtr)

    companion object {
        private val marginRange = 0..32767
    }

    /** Changes all margins for the widget. */
    fun changeMargins(bottom: Int, end: Int, start: Int, top: Int) {
        marginBottom = bottom
        marginEnd = end
        marginStart = start
        marginTop = top
    }

    /** Changes all margins for the widget using a single value. */
    fun changeAllMargins(value: Int) {
        marginBottom = value
        marginEnd = value
        marginStart = value
        marginTop = value
    }

    /** Clears all the widget's margins (bottom, end, start, and top). */
    fun clearMargins() {
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
    fun grabFocus() {
        gtk_widget_grab_focus(gtkWidgetPtr)
    }

    /**
     * Connects the *grab-focus* signal to a [slot] on a [WidgetBase]. This signal is used when a widget is
     * "grabbing" focus.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectGrabFocusSignal(slot: CPointer<GrabFocusSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkWidgetPtr, signal = GRAB_FOCUS_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *show* signal to a [slot] on a [WidgetBase]. This signal is used when a widget is shown.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectShowSignal(slot: CPointer<ShowSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkWidgetPtr, signal = SHOW_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *hide* signal to a [slot] on a [WidgetBase]. This signal is used when a widget is hidden.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectHideSignal(slot: CPointer<HideSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkWidgetPtr, signal = HIDE_SIGNAL, slot = slot, data = userData)

    /**
     * Recursively shows a widget, and any child widgets (if the [widget][WidgetBase] is a
     * [container][org.guivista.core.layout.Container]).
     */
    fun showAll() {
        gtk_widget_show_all(gtkWidgetPtr)
    }
}

/**
 * The event handler for the *grab-focus* signal. Arguments:
 * 1. widget: CPointer<GtkWidget>
 * 2. userData: gpointer
 */
typealias GrabFocusSlot = CFunction<(widget: CPointer<GtkWidget>, userData: gpointer) -> Unit>

/**
 * The event handler for the *show* signal. Arguments:
 * 1. widget: CPointer<GtkWidget>
 * 2. userData: gpointer
 */
typealias ShowSlot = CFunction<(widget: CPointer<GtkWidget>, userData: gpointer) -> Unit>

/**
 * The event handler for the *hide* signal. Arguments:
 * 1. widget: CPointer<GtkWidget>
 * 2. userData: gpointer
 */
typealias HideSlot = CFunction<(widget: CPointer<GtkWidget>, userData: gpointer) -> Unit>
