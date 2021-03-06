package org.guiVista.gui.widget.dataEntry

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal
import org.guiVista.gui.widget.Range

public actual class Scale(scalePtr: CPointer<GtkScale>? = null,
                          orientation: GtkOrientation = GtkOrientation.GTK_ORIENTATION_HORIZONTAL) : Range {
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        scalePtr?.reinterpret() ?: gtk_scale_new(orientation, null)
    public val gtkScalePtr: CPointer<GtkScale>?
        get() = gtkWidgetPtr?.reinterpret()
    public actual var digits: Int
        get() = gtk_scale_get_digits(gtkScalePtr)
        set(value) {
            if (value in (-1..64)) gtk_scale_set_digits(gtkScalePtr, value)
        }
    public actual var drawValue: Boolean
        get() = gtk_scale_get_draw_value(gtkScalePtr) == TRUE
        set(value) = gtk_scale_set_draw_value(gtkScalePtr, if (value) TRUE else FALSE)
    public actual var hasOrigin: Boolean
        get() = gtk_scale_get_has_origin(gtkScalePtr) == TRUE
        set(value) = gtk_scale_set_has_origin(gtkScalePtr, if (value) TRUE else FALSE)

    /** The position in which the current value is displayed. Default value is *GtkPositionType.GTK_POS_TOP*. */
    public var valuePos: GtkPositionType
        get() = gtk_scale_get_value_pos(gtkScalePtr)
        set(value) = gtk_scale_set_value_pos(gtkScalePtr, value)

    /**
     * Connects the *format-value* event to a [handler] on a [Scale]. The event allows you to change how the scale value
     * is displayed. Connect a event handler which returns an allocated string representing value. That string will
     * then be used to display the scale's value.
     *
     * If no user-provided handlers are installed the value will be displayed on its own, rounded according to the
     * value of the [digits property][digits].
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectFormatValueEvent(handler: CPointer<FormatValueHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkScalePtr, signal = ScaleEvent.formatValue, slot = handler, data = userData).toULong()

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkScalePtr, handlerId.toUInt())
    }

    public fun addMark(value: Double, position: GtkPositionType, markup: String) {
        gtk_scale_add_mark(scale = gtkScalePtr, value = value, position = position, markup = markup)
    }

    public actual fun clearMarks() {
        gtk_scale_clear_marks(gtkScalePtr)
    }
}

public fun scaleWidget(scalePtr: CPointer<GtkScale>? = null, init: Scale.() -> Unit = {}): Scale {
    val scale = Scale(scalePtr)
    scale.init()
    return scale
}

/**
 * The event handler for the *format-value* event. Arguments:
 * 1. scale: CPointer<GtkScale>
 * 2. value: Double
 * 3. userData: gpointer
 */
public typealias FormatValueHandler = CFunction<(scale: CPointer<GtkScale>, value: Double, userData: gpointer) -> Unit>
