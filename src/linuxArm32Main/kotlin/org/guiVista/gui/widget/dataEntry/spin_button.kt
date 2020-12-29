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
import org.guiVista.gui.Adjustment

private const val VALUE_CHANGED_SIGNAL = "value-changed"
private const val CHANGE_VALUE_SIGNAL = "change-value"

public actual class SpinButton(spinButtonPtr: CPointer<GtkSpinButton>? = null, climbRate: Double = 0.0,
                               digits: UInt = 1u) : EntryBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = spinButtonPtr?.reinterpret()
        ?: gtk_spin_button_new(climb_rate = climbRate, digits = digits, adjustment = null)
    public val gtkSpinButtonPtr: CPointer<GtkSpinButton>?
        get() = gtkWidgetPtr?.reinterpret()
    public actual var adjustment: Adjustment?
        get() {
            val tmp = gtk_spin_button_get_adjustment(gtkSpinButtonPtr)
            return if (tmp != null) Adjustment(tmp)
            else null
        }
        set(value) = gtk_spin_button_set_adjustment(gtkSpinButtonPtr, value?.gtkAdjustmentPtr)
    public actual var digits: UInt
        get() = gtk_spin_button_get_digits(gtkSpinButtonPtr)
        set(value) {
            if (value <= 20u) gtk_spin_button_set_digits(gtkSpinButtonPtr, value)
        }
    public actual var numeric: Boolean
        get() = gtk_spin_button_get_numeric(gtkSpinButtonPtr) == TRUE
        set(value) = gtk_spin_button_set_numeric(gtkSpinButtonPtr, if (value) TRUE else FALSE)
    public actual var snapToTicks: Boolean
        get() = gtk_spin_button_get_snap_to_ticks(gtkSpinButtonPtr) == TRUE
        set(value) = gtk_spin_button_set_snap_to_ticks(gtkSpinButtonPtr, if (value) TRUE else FALSE)
    public actual var value: Double
        get() = gtk_spin_button_get_value(gtkSpinButtonPtr)
        set(value) = gtk_spin_button_set_value(gtkSpinButtonPtr, value)
    public actual var wrap: Boolean
        get() = gtk_spin_button_get_wrap(gtkSpinButtonPtr) == TRUE
        set(value) = gtk_spin_button_set_wrap(gtkSpinButtonPtr, if (value) TRUE else FALSE)

    /**
     * Whether the spin button should update always, or only when the value is legal. Default value is
     * *GtkSpinButtonUpdatePolicy.GTK_UPDATE_ALWAYS*.
     */
    public var updatePolicy: GtkSpinButtonUpdatePolicy
        get() = gtk_spin_button_get_update_policy(gtkSpinButtonPtr)
        set(value) = gtk_spin_button_set_update_policy(gtkSpinButtonPtr, value)

    /**
     * Increment or decrement a spin button’s value in a specified direction by a specified amount.
     * @param direction A GtkSpinType indicating the direction to spin.
     * @param increment Step increment to apply in the specified direction.
     */
    public fun spin(direction: GtkSpinType, increment: Double) {
        gtk_spin_button_spin(spin_button = gtkSpinButtonPtr, direction = direction, increment = increment)
    }

    public actual fun changeRange(range: ClosedFloatingPointRange<Double>) {
        gtk_spin_button_set_range(spin_button = gtkSpinButtonPtr, min = range.start, max = range.endInclusive)
    }

    public actual fun configure(adjustment: Adjustment?, digits: UInt, climbRate: Double) {
        gtk_spin_button_configure(
            spin_button = gtkSpinButtonPtr,
            adjustment = adjustment?.gtkAdjustmentPtr,
            climb_rate = climbRate,
            digits = digits
        )
    }

    /**
     * Connects the *value-changed* signal to a [slot] on a [SpinButton]. This signal is used when the [value] changes.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectValueChangedSignal(slot: CPointer<ValueChangedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkSpinButtonPtr, signal = VALUE_CHANGED_SIGNAL, slot = slot, data = userData).toULong()

    /**
     * Connects the *change-value* signal to a [slot] on a [SpinButton]. This signal is used when the user initiates a
     * [value] change. Applications should not connect to it, but may emit it with `g_signal_emit_by_name()` if they
     * need to control the cursor programmatically. The default bindings for this signal are *Up*, *Down*, *Page Up*,
     * and *Page Down*.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectChangeValueSignal(slot: CPointer<ChangeValueSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkSpinButtonPtr, signal = CHANGE_VALUE_SIGNAL, slot = slot, data = userData).toULong()

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkSpinButtonPtr, handlerId.toUInt())
    }
}

public fun spinButtonWidget(
    spinButtonPtr: CPointer<GtkSpinButton>? = null,
    climbRate: Double = 1.0,
    digits: UInt = 1u,
    init: SpinButton.() -> Unit
): SpinButton {
    val spinButton = SpinButton(spinButtonPtr = spinButtonPtr, climbRate = climbRate, digits = digits)
    spinButton.init()
    return spinButton
}

/**
 * The event handler for the *value-changed* signal. Arguments:
 * 1. spinButton: CPointer<GtkSpinButton>
 * 2. userData: gpointer
 */
public typealias ValueChangedSlot = CFunction<(spinButton: CPointer<GtkSpinButton>, userData: gpointer) -> Unit>

/**
 * The event handler for the *change-value* signal. Arguments:
 * 1. spinButton: CPointer<GtkSpinButton>
 * 2. scroll: GtkScrollType
 * 3. userData: gpointer
 */
public typealias ChangeValueSlot = CFunction<(spinButton: CPointer<GtkSpinButton>, scroll: GtkScrollType,
                                              userData: gpointer) -> Unit>
