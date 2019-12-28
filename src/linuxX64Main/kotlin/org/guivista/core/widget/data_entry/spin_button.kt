package org.guivista.core.widget.data_entry

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.connectGObjectSignal

/** Retrieve an integer or floating-point number from the user. */
class SpinButton(climbRate: Double = 1.0, digits: UInt = 1u) : Entry() {
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
            gtk_spin_button_new(climb_rate = climbRate, digits = digits, adjustment = null)
    val gtkSpinButtonPtr: CPointer<GtkSpinButton>?
        get() = gtkWidgetPtr?.reinterpret()
    /** The adjustment that holds the value of the [SpinButton]. */
    var adjustment: CPointer<GtkAdjustment>?
        get() = gtk_spin_button_get_adjustment(gtkSpinButtonPtr)
        set(value) = gtk_spin_button_set_adjustment(gtkSpinButtonPtr, value)
    /** The number of decimal places to display. */
    var digits: UInt
        get() = gtk_spin_button_get_digits(gtkSpinButtonPtr)
        set(value) = gtk_spin_button_set_digits(gtkSpinButtonPtr, value)
    /** Whether non-numeric characters should be ignored. */
    var numeric: Boolean
        get() = gtk_spin_button_get_numeric(gtkSpinButtonPtr) == TRUE
        set(value) = gtk_spin_button_set_numeric(gtkSpinButtonPtr, if (value) TRUE else FALSE)
    /** Whether erroneous values are automatically changed to a spin button's nearest step increment. */
    var snapToTicks: Boolean
        get() = gtk_spin_button_get_snap_to_ticks(gtkSpinButtonPtr) == TRUE
        set(value) = gtk_spin_button_set_snap_to_ticks(gtkSpinButtonPtr, if (value) TRUE else FALSE)
    /** Reads the current value, or sets a new value. */
    var value: Double
        get() = gtk_spin_button_get_value(gtkSpinButtonPtr)
        set(value) = gtk_spin_button_set_value(gtkSpinButtonPtr, value)
    /** Whether a spin button should wrap upon reaching its limits. */
    var wrap: Boolean
        get() = gtk_spin_button_get_wrap(gtkSpinButtonPtr) == TRUE
        set(value) = gtk_spin_button_set_wrap(gtkSpinButtonPtr, if (value) TRUE else FALSE)
    /** Whether the spin button should update always, or only when the value is legal. */
    var updatePolicy: GtkSpinButtonUpdatePolicy
        get() = gtk_spin_button_get_update_policy(gtkSpinButtonPtr)
        set(value) = gtk_spin_button_set_update_policy(gtkSpinButtonPtr, value)

    /**
     * Increment or decrement a spin button’s value in a specified direction by a specified amount.
     * @param direction A GtkSpinType indicating the direction to spin.
     * @param increment Step increment to apply in the specified direction.
     */
    fun spin(direction: GtkSpinType, increment: Double) {
        gtk_spin_button_spin(spin_button = gtkSpinButtonPtr, direction = direction, increment = increment)
    }

    /**
     * Sets the minimum and maximum allowable values for [SpinButton]. If the current value is outside this range it
     * will be adjusted to fit within the range, otherwise it will remain unchanged.
     * @param range A range that includes the minimum (start) and maximum values (end).
     */
    fun changeRange(range: ClosedFloatingPointRange<Double> = 0.0..1.0) {
        gtk_spin_button_set_range(spin_button = gtkSpinButtonPtr, min = range.start, max = range.endInclusive)
    }

    /**
     * Changes the properties of an existing [SpinButton]. The adjustment, climb rate, and number of decimal places are
     * updated accordingly.
     * @param adjustment A GtkAdjustment to replace the [spin button's][SpinButton] existing [adjustment], or **null**
     * to leave its current adjustment unchanged.
     * @param digits The number of decimal places to display in the [SpinButton].
     * @param climbRate The number of decimal places to display in the [SpinButton].
     */
    fun configure(adjustment: CPointer<GtkAdjustment>? = null, digits: UInt = 1u, climbRate: Double = 1.0) {
        gtk_spin_button_configure(spin_button = gtkSpinButtonPtr, adjustment = adjustment, climb_rate = climbRate,
                digits = digits)
    }

    /**
     * Connects the *value-changed* signal to a [slot] on a [SpinButton]. This signal is used when the [value] changes.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectValueChangedSignal(slot: CPointer<ValueChangedSlot>, userData: gpointer): ULong =
            connectGObjectSignal(obj = gtkSpinButtonPtr, signal = "value-changed", slot = slot, data = userData)

    /**
     * Connects the *change-value* signal to a [slot] on a [SpinButton]. This signal is used when the user initiates a
     * [value] change. Applications should not connect to it, but may emit it with `g_signal_emit_by_name()` if they
     * need to control the cursor programmatically. The default bindings for this signal are *Up*, *Down*, *Page Up*, and
     * *Page Down*.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectChangeValueSignal(slot: CPointer<ChangeValueSlot>, userData: gpointer): ULong =
            connectGObjectSignal(obj = gtkSpinButtonPtr, signal = "change-value", slot = slot, data = userData)
}

fun spinButtonWidget(climbRate: Double = 1.0, digits: UInt = 1u, init: SpinButton.() -> Unit): SpinButton {
    val spinButton = SpinButton(climbRate, digits)
    spinButton.init()
    return spinButton
}

/**
 * The event handler for the *value-changed* signal. Arguments:
 * 1. spinButton: CPointer<GtkSpinButton>
 * 2. userData: gpointer
 */
typealias ValueChangedSlot = CFunction<(spinButton: CPointer<GtkSpinButton>, userData: gpointer) -> Unit>

/**
 * The event handler for the *change-value* signal. Arguments:
 * 1. spinButton: CPointer<GtkSpinButton>
 * 2. scroll: GtkScrollType
 * 3. userData: gpointer
 */
typealias ChangeValueSlot = CFunction<(spinButton: CPointer<GtkSpinButton>, scroll: GtkScrollType,
                                       userData: gpointer) -> Unit>
