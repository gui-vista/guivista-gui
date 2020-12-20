package org.guiVista.gui

import glib2.g_object_unref
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import org.guiVista.core.ObjectBase
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

private const val CHANGED_SIGNAL = "changed"
private const val VALUE_CHANGED_SIGNAL = "value-changed"

public actual class Adjustment(adjustmentPtr: CPointer<GtkAdjustment>? = null) : ObjectBase {
    public val gtkAdjustmentPtr: CPointer<GtkAdjustment>? = adjustmentPtr ?: createAdjustment()
    public actual var lower: Double
        get() = gtk_adjustment_get_lower(gtkAdjustmentPtr)
        set(value) = gtk_adjustment_set_lower(gtkAdjustmentPtr, value)
    public actual var pageIncrement: Double
        get() = gtk_adjustment_get_page_increment(gtkAdjustmentPtr)
        set(value) = gtk_adjustment_set_page_increment(gtkAdjustmentPtr, value)
    public actual var pageSize: Double
        get() = gtk_adjustment_get_page_size(gtkAdjustmentPtr)
        set(value) = gtk_adjustment_set_page_size(gtkAdjustmentPtr, value)
    public actual var stepIncrement: Double
        get() = gtk_adjustment_get_step_increment(gtkAdjustmentPtr)
        set(value) = gtk_adjustment_set_step_increment(gtkAdjustmentPtr, value)
    public actual var upper: Double
        get() = gtk_adjustment_get_upper(gtkAdjustmentPtr)
        set(value) = gtk_adjustment_set_upper(gtkAdjustmentPtr, value)
    public actual var value: Double
        get() = gtk_adjustment_get_value(gtkAdjustmentPtr)
        set(newValue) = gtk_adjustment_set_value(gtkAdjustmentPtr, newValue)
    public actual val minimumIncrement: Double
        get() = gtk_adjustment_get_minimum_increment(gtkAdjustmentPtr)

    private fun createAdjustment() = gtk_adjustment_new(
        value = 0.0,
        lower = 0.0,
        upper = 0.0,
        step_increment = 0.0,
        page_increment = 0.0,
        page_size = 0.0
    )

    public actual fun clampPage(adjustment: Adjustment, lower: Double, upper: Double) {
        gtk_adjustment_clamp_page(adjustment = adjustment.gtkAdjustmentPtr, lower = lower, upper = upper)
    }

    public actual fun configure(
        adjustment: Adjustment,
        value: Double,
        lower: Double,
        upper: Double,
        stepIncrement: Double,
        pageIncrement: Double,
        pageSize: Double
    ) {
        gtk_adjustment_configure(
            adjustment = adjustment.gtkAdjustmentPtr,
            value = value,
            lower = lower,
            upper = upper,
            step_increment = stepIncrement,
            page_size = pageSize,
            page_increment = pageIncrement
        )
    }

    /**
     * Connects the *changed* signal to a [slot] on a [Adjustment]. This signal is used when one or more of the
     * [Adjustment] properties have been changed, other than the “value” property.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectChangedSignal(slot: CPointer<ChangedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkAdjustmentPtr, signal = CHANGED_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *value-changed* signal to a [slot] on a [Adjustment]. This signal is used when the [value] property
     * has been changed.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectValueChangedSignal(slot: CPointer<ValueChangedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkAdjustmentPtr, signal = VALUE_CHANGED_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkAdjustmentPtr, handlerId)
    }

    override fun close() {
        g_object_unref(gtkAdjustmentPtr)
    }
}

public fun adjustment(adjustmentPtr: CPointer<GtkAdjustment>? = null, init: Adjustment.() -> Unit): Adjustment {
    val adjustment = Adjustment(adjustmentPtr)
    adjustment.init()
    return adjustment
}

/**
 * The event handler for the *changed* signal. Arguments:
 * 1. adjustment: CPointer<GtkAdjustment>
 * 2. userData: gpointer
 */
public typealias ChangedSlot = CFunction<(button: CPointer<GtkAdjustment>?, userData: gpointer) -> Unit>

/**
 * The event handler for the *value-changed* signal. Arguments:
 * 1. adjustment: CPointer<GtkAdjustment>
 * 2. userData: gpointer
 */
public typealias ValueChangedSlot = CFunction<(button: CPointer<GtkButton>?, userData: gpointer) -> Unit>
