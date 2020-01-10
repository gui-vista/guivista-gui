package org.guiVista.core.widget.dataEntry

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal
import org.guiVista.core.widget.Range

private const val FORMAT_VALUE_SIGNAL = "format-value"

/** A slider widget for selecting a value from a range. */
class Scale(scalePtr: CPointer<GtkScale>? = null,
            orientation: GtkOrientation = GtkOrientation.GTK_ORIENTATION_HORIZONTAL) : Range {
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        scalePtr?.reinterpret() ?: gtk_scale_new(orientation, null)
    val gtkScalePtr: CPointer<GtkScale>?
        get() = gtkWidgetPtr?.reinterpret()
    /** The number of decimal places that are displayed in the value. Default value is *1*. */
    var digits: Int
        get() = gtk_scale_get_digits(gtkScalePtr)
        set(value) {
            if (value in (-1..64)) gtk_scale_set_digits(gtkScalePtr, value)
        }
    /** Whether the current value is displayed as a string next to the slider. Default value is *false*. */
    var drawValue: Boolean
        get() = gtk_scale_get_draw_value(gtkScalePtr) == TRUE
        set(value) = gtk_scale_set_draw_value(gtkScalePtr, if (value) TRUE else FALSE)
    /** Whether the scale has an origin. Default value is *true*. */
    var hasOrigin: Boolean
        get() = gtk_scale_get_has_origin(gtkScalePtr) == TRUE
        set(value) = gtk_scale_set_has_origin(gtkScalePtr, if (value) TRUE else FALSE)
    /** The position in which the current value is displayed. Default value is *GtkPositionType.GTK_POS_TOP*. */
    var valuePos: GtkPositionType
        get() = gtk_scale_get_value_pos(gtkScalePtr)
        set(value) = gtk_scale_set_value_pos(gtkScalePtr, value)

    /**
     * Connects the *format-value* signal to a [slot] on a [Scale]. The signal allows you to change how the scale value
     * is displayed. Connect a signal handler which returns an allocated string representing value. That string will
     * then be used to display the scale's value.
     *
     * If no user-provided handlers are installed the value will be displayed on its own, rounded according to the
     * value of the [digits property][digits].
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectFormatValueSignal(slot: CPointer<FormatValueSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkScalePtr, signal = FORMAT_VALUE_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkScalePtr, handlerId)
    }
}

fun scaleWidget(scalePtr: CPointer<GtkScale>? = null, init: Scale.() -> Unit): Scale {
    val scale = Scale(scalePtr)
    scale.init()
    return scale
}

/**
 * The event handler for the *format-value* signal. Arguments:
 * 1. scale: CPointer<GtkScale>
 * 2. value: Double
 * 3. userData: gpointer
 */
typealias FormatValueSlot = CFunction<(scale: CPointer<GtkScale>, value: Double, userData: gpointer) -> Unit>
