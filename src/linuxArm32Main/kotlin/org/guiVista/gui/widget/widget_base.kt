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

/** Base interface for all widget's (controls). */
actual interface WidgetBase : ObjectBase {
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

    /** Whether the application will paint directly on the widget. Default value is *false*. */
    var appPaintable: Boolean
        get() = gtk_widget_get_app_paintable(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_app_paintable(gtkWidgetPtr, if (value) TRUE else FALSE)

    /** Whether the widget can be the default widget. Default value is *false*. */
    var canDefault: Boolean
        get() = gtk_widget_get_can_default(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_can_default(gtkWidgetPtr, if (value) TRUE else FALSE)

    /**
     * Whether the widget should grab focus when it is clicked with the mouse. This property is only relevant for
     * widgets that can take focus. Before 3.20 several widgets (GtkButton, GtkFileChooserButton, GtkComboBox)
     * implemented this property individually.
     *
     * Default value is *true*.
     */
    var focusOnClick: Boolean
        get() = gtk_widget_get_focus_on_click(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_focus_on_click(gtkWidgetPtr, if (value) TRUE else FALSE)

    /** How to distribute horizontal space if widget gets extra space. */
    var hAlign: GtkAlign
        get() = gtk_widget_get_halign(gtkWidgetPtr)
        set(value) = gtk_widget_set_halign(gtkWidgetPtr, value)

    /** Whether the widget is the default widget. Default value is *false*. */
    val hasDefault: Boolean
        get() = gtk_widget_has_default(gtkWidgetPtr) == TRUE

    /** Whether the widget has the input focus. Default value is *false*. */
    val hasFocus: Boolean
        get() = gtk_widget_has_focus(gtkWidgetPtr) == TRUE

    /** Whether to expand horizontally. Default value is *false*. */
    var hExpand: Boolean
        get() = gtk_widget_get_hexpand(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_hexpand(gtkWidgetPtr, if (value) TRUE else FALSE)

    /** Whether to use the [hExpand] property. Default value is *false*. */
    var hExpandSet: Boolean
        get() = gtk_widget_get_hexpand_set(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_hexpand_set(gtkWidgetPtr, if (value) TRUE else FALSE)

    /** Whether the widget is the focus widget within the top level. Default value is *false*. */
    val isFocus: Boolean
        get() = gtk_widget_is_focus(gtkWidgetPtr) == TRUE

    /** The name of the widget. Default value is *""* (an empty String). */
    var name: String
        get() = gtk_widget_get_name(gtkWidgetPtr)?.toKString() ?: ""
        set(value) = gtk_widget_set_name(gtkWidgetPtr, value)

    /** Whether the [showAll] function should not affect this widget. Default value is *false*. */
    var noShowAll: Boolean
        get() = gtk_widget_get_no_show_all(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_no_show_all(gtkWidgetPtr, if (value) TRUE else FALSE)

    /**
     * The requested opacity of the widget. Before 3.8 this was only available in GtkWindow. Default value is *1.0*.
     */
    var opacity: Double
        get() = gtk_widget_get_opacity(gtkWidgetPtr)
        set(value) {
            if (value in (0.0..1.0)) gtk_widget_set_opacity(gtkWidgetPtr, value)
        }

    /** If *true* then the widget will receive the default action when it is focused. Default value is *false*. */
    var receivesDefault: Boolean
        get() = gtk_widget_get_receives_default(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_receives_default(gtkWidgetPtr, if (value) TRUE else FALSE)

    /** The scale factor of the widget. */
    val scaleFactor: Int
        get() = gtk_widget_get_scale_factor(gtkWidgetPtr)

    /** Whether the widget responds to input. */
    var sensitive: Boolean
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
    var tooltipMarkup: String
        get() = gtk_widget_get_tooltip_markup(gtkWidgetPtr)?.toKString() ?: ""
        set(value) = gtk_widget_set_tooltip_markup(gtkWidgetPtr, value)

    /** How to distribute vertical space if widget gets extra space. Default value is *GtkAlign.GTK_ALIGN_FILL*. */
    var vAlign: GtkAlign
        get() = gtk_widget_get_valign(gtkWidgetPtr)
        set(value) = gtk_widget_set_valign(gtkWidgetPtr, value)

    /** Whether to expand vertically. Default value is *false*. */
    var vExpand: Boolean
        get() = gtk_widget_get_vexpand(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_vexpand(gtkWidgetPtr, if (value) TRUE else FALSE)

    /** Whether to use the [vExpand] property. Default value is *false*. */
    var vExpandSet: Boolean
        get() = gtk_widget_get_vexpand_set(gtkWidgetPtr) == TRUE
        set(value) = gtk_widget_set_vexpand_set(gtkWidgetPtr, if (value) TRUE else FALSE)

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
    fun connectGrabFocusSignal(slot: CPointer<GrabFocusSlot>, userData: gpointer): UInt =
        connectGSignal(obj = gtkWidgetPtr, signal = GRAB_FOCUS_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *show* signal to a [slot] on a [WidgetBase]. This signal is used when a widget is shown.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectShowSignal(slot: CPointer<ShowSlot>, userData: gpointer): UInt =
        connectGSignal(obj = gtkWidgetPtr, signal = SHOW_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *hide* signal to a [slot] on a [WidgetBase]. This signal is used when a widget is hidden.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectHideSignal(slot: CPointer<HideSlot>, userData: gpointer): UInt =
        connectGSignal(obj = gtkWidgetPtr, signal = HIDE_SIGNAL, slot = slot, data = userData)

    /**
     * Recursively shows a widget, and any child widgets (if the [widget][WidgetBase] is a
     * [container][org.guiVista.gui.layout.Container]).
     */
    fun showAll() {
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
