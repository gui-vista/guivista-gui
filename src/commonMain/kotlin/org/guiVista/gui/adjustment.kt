package org.guiVista.gui

import org.guiVista.core.ObjectBase

/** A representation of an adjustable bounded value. */
expect class Adjustment : ObjectBase {
    /** The minimum value of the adjustment. Default value is *0*. */
    var lower: Double

    /** The page increment of the adjustment. Default value is *0*. */
    var pageIncrement: Double

    /**
     * The page size of the adjustment. Note that the page size is irrelevant, and should be set to zero *if* the
     * adjustment is used for a simple scalar value, eg in a GtkSpinButton. Default value is *0*.
     */
    var pageSize: Double

    /** The step increment of the adjustment. Default value is *0*. */
    var stepIncrement: Double

    /**
     * The maximum value of the adjustment. Note that values will be restricted by upper - [pageSize] if the [pageSize]
     * property isn't *0*. Default value is *0*.
     */
    var upper: Double

    /** The value of the adjustment. Default value is *0*. */
    var value: Double

    /** The smaller value of [step increment][stepIncrement] and [page increment][pageIncrement]. */
    val minimumIncrement: Double

    /**
     * Updates the [value] property to ensure that the range between [lower] and [upper] is in the current page
     * (i.e. between [value] and [value] + [page size][pageSize]). If the range is larger than the page size then only
     * the start of it will be in the current page. A “value-changed” signal will be emitted if the value is changed.
     */
    fun clampPage(adjustment: Adjustment, lower: Double, upper: Double)

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
    )
}