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

/** A representation of an adjustable bounded value. */
class Adjustment(adjustmentPtr: CPointer<GtkAdjustment>? = null) : ObjectBase {
    val gtkAdjustmentPtr: CPointer<GtkAdjustment>? = adjustmentPtr ?: createAdjustment()
    /** The minimum value of the adjustment. Default value is *0*. */
    var lower: Double
        get() = gtk_adjustment_get_lower(gtkAdjustmentPtr)
        set(value) = gtk_adjustment_set_lower(gtkAdjustmentPtr, value)
    /** The page increment of the adjustment. Default value is *0*. */
    var pageIncrement: Double
        get() = gtk_adjustment_get_page_increment(gtkAdjustmentPtr)
        set(value) = gtk_adjustment_set_page_increment(gtkAdjustmentPtr, value)
    /**
     * The page size of the adjustment. Note that the page size is irrelevant, and should be set to zero *if* the
     * adjustment is used for a simple scalar value, eg in a GtkSpinButton. Default value is *0*.
     */
    var pageSize: Double
        get() = gtk_adjustment_get_page_size(gtkAdjustmentPtr)
        set(value) = gtk_adjustment_set_page_size(gtkAdjustmentPtr, value)
    /** The step increment of the adjustment. Default value is *0*. */
    var stepIncrement: Double
        get() = gtk_adjustment_get_step_increment(gtkAdjustmentPtr)
        set(value) = gtk_adjustment_set_step_increment(gtkAdjustmentPtr, value)
    /**
     * The maximum value of the adjustment. Note that values will be restricted by upper - [pageSize] if the [pageSize]
     * property isn't *0*. Default value is *0*.
     */
    var upper: Double
        get() = gtk_adjustment_get_upper(gtkAdjustmentPtr)
        set(value) = gtk_adjustment_set_upper(gtkAdjustmentPtr, value)
    /** The value of the adjustment. Default value is *0*. */
    var value: Double
        get() = gtk_adjustment_get_value(gtkAdjustmentPtr)
        set(newValue) = gtk_adjustment_set_value(gtkAdjustmentPtr, newValue)
    /** The smaller value of [step increment][stepIncrement] and [page increment][pageIncrement]. */
    val minimumIncrement: Double
        get() = gtk_adjustment_get_minimum_increment(gtkAdjustmentPtr)

    private fun createAdjustment() = gtk_adjustment_new(
        value = 0.0,
        lower = 0.0,
        upper = 0.0,
        step_increment = 0.0,
        page_increment = 0.0,
        page_size = 0.0
    )

    /**
     * Updates the [value] property to ensure that the range between [lower] and [upper] is in the current page
     * (i.e. between [value] and [value] + [page size][pageSize]). If the range is larger than the page size then only
     * the start of it will be in the current page. A “value-changed” signal will be emitted if the value is changed.
     */
    fun clampPage(adjustment: Adjustment, lower: Double, upper: Double) {
        gtk_adjustment_clamp_page(adjustment = adjustment.gtkAdjustmentPtr, lower = lower, upper = upper)
    }

    /**
     * Sets all properties of the [Adjustment] at once. Use this function to avoid multiple emissions of the “changed”
     * signal.
     * @param adjustment The adjustment.
     * @param value The new value.
     * @param lower The new minimum value.
     * @param upper The new maximum value.
     * @param stepIncrement The new step increment.
     * @param pageIncrement The new page increment.
     * @param pageSize The new page size.
     * @see lower For an alternative way of compressing multiple emissions of “changed” into one.
     */
    fun configure(
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
    fun connectChangedSignal(slot: CPointer<ChangedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkAdjustmentPtr, signal = CHANGED_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *value-changed* signal to a [slot] on a [Adjustment]. This signal is used when the [value] property
     * has been changed.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectValueChangedSignal(slot: CPointer<ValueChangedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkAdjustmentPtr, signal = VALUE_CHANGED_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkAdjustmentPtr, handlerId)
    }

    override fun close() {
        g_object_unref(gtkAdjustmentPtr)
    }
}

fun adjustment(adjustmentPtr: CPointer<GtkAdjustment>? = null, init: Adjustment.() -> Unit): Adjustment {
    val adjustment = Adjustment(adjustmentPtr)
    adjustment.init()
    return adjustment
}

/**
 * The event handler for the *changed* signal. Arguments:
 * 1. adjustment: CPointer<GtkAdjustment>
 * 2. userData: gpointer
 */
typealias ChangedSlot = CFunction<(button: CPointer<GtkAdjustment>?, userData: gpointer) -> Unit>

/**
 * The event handler for the *value-changed* signal. Arguments:
 * 1. adjustment: CPointer<GtkAdjustment>
 * 2. userData: gpointer
 */
typealias ValueChangedSlot = CFunction<(button: CPointer<GtkButton>?, userData: gpointer) -> Unit>

