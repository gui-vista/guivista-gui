package org.guiVista.core.widget

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.Adjustment
import org.guiVista.core.connectGSignal

/** A base for widgets which visualize an adjustment. */
interface Range : WidgetBase {
    val gtkRangePtr: CPointer<GtkRange>?
        get() = gtkWidgetPtr?.reinterpret()
    /** Contains the current value of this range object. */
    var adjustment: Adjustment?
        get() {
            val tmp = gtk_range_get_adjustment(gtkRangePtr)
            return if (tmp != null) Adjustment(tmp) else null
        }
        set(value) = gtk_range_set_adjustment(gtkRangePtr, value?.gtkAdjustmentPtr)
    /**
     * The fill level (e.g. prebuffering of a network stream), which is best described by its most prominent use case.
     * It is an indicator for the amount of pre-buffering in a streaming media player. In that use case the value of
     * the range would indicate the current play position, and the fill level would be the position up to which the
     * file/stream has been downloaded.
     *
     * This amount of pre buffering can be displayed on the range’s trough and is themeable separately from the trough.
     * To enable fill level display use `gtk_range_set_show_fill_level()`. The range defaults to not showing the fill
     * level. Additionally it’s possible to restrict the range’s slider position to values which are smaller than the
     * fill level. This is controlled by `gtk_range_set_restrict_to_fill_level()`, and is by default enabled.
     *
     * Default value is *1.79769e+308*.
     */
    var fillLevel: Double
        get() = gtk_range_get_fill_level(gtkRangePtr)
        set(value) = gtk_range_set_fill_level(gtkRangePtr, value)
    /** Invert direction slider moves to increase range value. Default value is *false*. */
    var inverted: Boolean
        get() = gtk_range_get_inverted(gtkRangePtr) == TRUE
        set(value) = gtk_range_set_inverted(gtkRangePtr, if (value) TRUE else FALSE)
    /**
     * The sensitivity policy for the stepper that points to the adjustment's lower side. Default value is
     * *GtkSensitivityType.GTK_SENSITIVITY_AUTO*.
     */
    var lowerStepperSensitivity: GtkSensitivityType
        get() = gtk_range_get_lower_stepper_sensitivity(gtkRangePtr)
        set(value) = gtk_range_set_lower_stepper_sensitivity(gtkRangePtr, value)
    /**
     * Controls whether slider movement is restricted to an upper boundary set by the fill level. Default value is
     * *true*.
     */
    var restrictToFillLevel: Boolean
        get() = gtk_range_get_restrict_to_fill_level(gtkRangePtr) == TRUE
        set(value) = gtk_range_set_restrict_to_fill_level(gtkRangePtr, if (value) TRUE else FALSE)
    /** The number of digits to round the value to when it changes, or -1. Default value is *-1*. */
    var roundDigits: Int
        get() = gtk_range_get_round_digits(gtkRangePtr)
        set(value) {
            if (value >= -1) gtk_range_set_round_digits(gtkRangePtr, value)
        }
    /** Controls whether fill level indicator graphics are displayed on the trough. Default value is *false*. */
    var showFillLevel: Boolean
        get() = gtk_range_get_show_fill_level(gtkRangePtr) == TRUE
        set(value) = gtk_range_set_show_fill_level(gtkRangePtr, if (value) TRUE else FALSE)
    /**
     * The sensitivity policy for the stepper that points to the adjustment's upper side. Default value is
     * *GtkSensitivityType.GTK_SENSITIVITY_AUTO*.
     */
    var upperStepperSensitivity: GtkSensitivityType
        get() = gtk_range_get_upper_stepper_sensitivity(gtkRangePtr)
        set(value) = gtk_range_set_upper_stepper_sensitivity(gtkRangePtr, value)

    /**
     * Connects the *adjust-bounds* signal to a [slot] on a [Range]. The *adjust-bounds* signal is used to give the
     * application a chance to adjust the bounds.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectAdjustBoundsSignal(slot: CPointer<AdjustBoundsSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkRangePtr, signal = "adjust-bounds", slot = slot, data = userData)

    /**
     * Connects the *change-value* signal to a [slot] on a [Range]. The *change-value* signal is used when a scroll
     * action is performed on a [Range]. It allows an application to determine the type of scroll event that occurred
     * and the new value.
     *
     * The application can handle the event itself and return *true* to prevent further processing,
     * or by returning *false* it can pass the event to other handlers until the default GTK handler is reached. The
     * value parameter isn't rounded. An application that overrides the GtkRange::change-value signal is responsible
     * for clamping the value to the desired number of decimal digits; the default GTK handler clamps the value based
     * on “round-digits”.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectChangeValueSignal(slot: CPointer<ChangeValueSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkRangePtr, signal = "change-value", slot = slot, data = userData)

    /**
     * Connects the *move-slider* signal to a [slot] on a [Range]. The *move-slider* signal is used for key bindings
     * when a slider moves.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectMoveSliderSignal(slot: CPointer<MoveSliderSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkRangePtr, signal = "move-slider", slot = slot, data = userData)

    /**
     * Connects the *value-changed* signal to a [slot] on a [Range]. The *value-changed* signal is used when the
     * ranged value changes.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectValueChangedSignal(slot: CPointer<ValueChangedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkRangePtr, signal = "value-changed", slot = slot, data = userData)
}

/**
 * The event handler for the *adjust-bounds* signal. Arguments:
 * 1. range: CPointer<GtkRange>
 * 2. value: Double
 * 3. userData: gpointer
 */
typealias AdjustBoundsSlot = CFunction<(range: CPointer<GtkRange>, value: Double, userData: gpointer) -> Unit>

/**
 * The event handler for the *change-value* signal. Arguments:
 * 1. range: CPointer<GtkRange>
 * 2. scroll: GtkScrollType
 * 3. value: double
 * 4. userData: gpointer
 */
typealias ChangeValueSlot = CFunction<(range: CPointer<GtkRange>, scroll: GtkScrollType, value: Double,
                                       userData: gpointer) -> Unit>

/**
 * The event handler for the *move-slider* signal. Arguments:
 * 1. range: CPointer<GtkRange>
 * 2. step: GtkScrollType
 * 3. userData: gpointer
 */
typealias MoveSliderSlot = CFunction<(range: CPointer<GtkRange>, step: GtkScrollType, userData: gpointer) -> Unit>

/**
 * The event handler for the *value-changed* signal. Arguments:
 * 1. range: CPointer<GtkRange>
 * 2. userData: gpointer
 */
typealias ValueChangedSlot = CFunction<(range: CPointer<GtkRange>, userData: gpointer) -> Unit>
