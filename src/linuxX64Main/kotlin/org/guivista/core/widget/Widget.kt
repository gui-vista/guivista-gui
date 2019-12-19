package org.guivista.core.widget

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import org.guivista.core.connectGtkSignal

/** Base interface for all widget's (controls). */
interface Widget {
    val widgetPtr: CPointer<GtkWidget>?
    /** If set to *true* then the widget can accept the input focus. */
    var canFocus: Boolean
        set(value) = gtk_widget_set_can_focus(widgetPtr, if (value) TRUE else FALSE)
        get() = gtk_widget_get_can_focus(widgetPtr) == TRUE
    /**
     * Enables or disables the emission of “query-tooltip” on widget. A value of *true* indicates that widget can have
     * a tooltip, in this case the widget will be queried using “query-tooltip” to determine whether it will provide a
     * tooltip or not. Note that setting this property to *true* for the first time will change the event masks of the
     * GdkWindows of this widget to include leave-notify and motion-notify events. This cannot and will not be undone
     * when the property is set to *false* again.
     */
    var hasTooltip: Boolean
        set(value) = gtk_widget_set_has_tooltip(widgetPtr, if (value) TRUE else FALSE)
        get() = gtk_widget_get_has_tooltip(widgetPtr) == TRUE
    /**
     * Sets the text of tooltip to be the given string. Property “has-tooltip” will automatically be set to *true* and
     * there will be taken care of “query-tooltip” in the default signal handler. Note that if both “tooltip-text” and
     * “tooltip-markup” are set, the last one wins.
     */
    var tooltipText: String
        set(value) = gtk_widget_set_tooltip_text(widgetPtr, value)
        get() = gtk_widget_get_tooltip_text(widgetPtr)?.toKString() ?: ""
    /** If set to *true* then the widget is visible. **/
    var visible: Boolean
        set(value) = gtk_widget_set_visible(widgetPtr, if (value) TRUE else FALSE)
        get() = gtk_widget_get_visible(widgetPtr) == TRUE
    /**
     * Margin on bottom side of widget. This property adds margin outside of the widget's normal size request, the
     * margin will be added in addition to the size from `gtk_widget_set_size_request()` for example.
     */
    var marginBottom: Int
        set(value) = gtk_widget_set_margin_bottom(widgetPtr, value)
        get() = gtk_widget_get_margin_bottom(widgetPtr)
    /**
     * Margin on end of widget, horizontally. This property supports left-to-right and right-to-left text directions.
     * This property adds margin outside of the widget's normal size request, the margin will be added in addition to
     * the size from `gtk_widget_set_size_request()` for example.
     */
    var marginEnd: Int
        set(value) = gtk_widget_set_margin_end(widgetPtr, value)
        get() = gtk_widget_get_margin_end(widgetPtr)
    /**
     * Margin on start of widget, horizontally. This property supports left-to-right and right-to-left text directions.
     * This property adds margin outside of the widget's normal size request, the margin will be added in addition to
     * the size from `gtk_widget_set_size_request()` for example.
     */
    var marginStart: Int
        set(value) = gtk_widget_set_margin_start(widgetPtr, value)
        get() = gtk_widget_get_margin_start(widgetPtr)
    /**
     * Margin on top side of widget. This property adds margin outside of the widget's normal size request, the margin
     * will be added in addition to the size from `gtk_widget_set_size_request()` for example.
     */
    var marginTop: Int
        set(value) = gtk_widget_set_margin_top(widgetPtr, value)
        get() = gtk_widget_get_margin_top(widgetPtr)

    /**
     * Changes all margins for the widget.
     */
    fun changeMargins(bottom: Int, end: Int, start: Int, top: Int) {
        marginBottom = bottom
        marginEnd = end
        marginStart = start
        marginTop = top
    }

    /**
     * Clears all the widget's margins (bottom, end, start, and top).
     */
    fun clearMargins() {
        marginBottom = 0
        marginEnd = 0
        marginStart = 0
        marginTop = 0
    }

    /**
     * Connects the *grab-focus* signal to a [slot] on a widget. The *grab-focus* signal is used when a widget is
     * "grabbing" focus.
     * @param slot The event handler for the signal. Arguments:
     * 1. app: CPointer<GtkWidget>
     * 2. userData: gpointer
     * @param userData User data to pass through to the [slot].
     */
    fun connectGrabFocusSignal(slot: CPointer<CFunction<(app: CPointer<GtkWidget>, userData: gpointer) -> Unit>>,
                               userData: gpointer): ULong =
        connectGtkSignal(obj = widgetPtr, signal = "activate", slot = slot, data = userData)

    /**
     * Connects the *show* signal to a [slot] on a widget. The *show* signal is used when a widget is shown.
     * @param slot The event handler for the signal. Arguments:
     * 1. app: CPointer<GtkWidget>
     * 2. userData: gpointer
     * @param userData User data to pass through to the [slot].
     */
    fun connectShowSignal(slot: CPointer<CFunction<(app: CPointer<GtkWidget>, userData: gpointer) -> Unit>>,
                          userData: gpointer): ULong =
        connectGtkSignal(obj = widgetPtr, signal = "show", slot = slot, data = userData)

    /**
     * Connects the *hide* signal to a [slot] on a widget. The *hide* signal is used when a widget is hidden.
     * @param slot The event handler for the signal. Arguments:
     * 1. app: CPointer<GtkWidget>
     * 2. userData: gpointer
     * @param userData User data to pass through to the [slot].
     */
    fun connectHideSignal(slot: CPointer<CFunction<(app: CPointer<GtkWidget>, userData: gpointer) -> Unit>>,
                          userData: gpointer): ULong =
        connectGtkSignal(obj = widgetPtr, signal = "hide", slot = slot, data = userData)
}
