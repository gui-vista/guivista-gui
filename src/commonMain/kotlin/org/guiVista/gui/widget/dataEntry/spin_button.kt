package org.guiVista.gui.widget.dataEntry

import org.guiVista.gui.Adjustment

/** Retrieve an integer or floating-point number from the user. */
expect class SpinButton : EntryBase {
    /** The adjustment that holds the value of the [SpinButton]. */
    var adjustment: Adjustment?

    /** The number of decimal places to display. Default value is *0*. */
    var digits: UInt

    /** Whether non-numeric characters should be ignored. Default value is *false*. */
    var numeric: Boolean

    /**
     * Whether erroneous values are automatically changed to a [spin button's][SpinButton] nearest step increment.
     * Default value is *false*.
     */
    var snapToTicks: Boolean

    /** Reads the current value, or sets a new value. Default value is *0*. */
    var value: Double

    /** Whether a spin button should wrap upon reaching its limits. Default value is *false*. */
    var wrap: Boolean

    /**
     * Sets the minimum and maximum allowable values for [SpinButton]. If the current value is outside this range it
     * will be adjusted to fit within the range, otherwise it will remain unchanged.
     * @param range A range that includes the minimum (start) and maximum values (end).
     */
    fun changeRange(range: ClosedFloatingPointRange<Double> = 0.0..1.0)

    /**
     * Changes the properties of an existing [SpinButton]. The adjustment, climb rate, and number of decimal places are
     * updated accordingly.
     * @param adjustment The adjustment to replace the [spin button's][SpinButton] existing adjustment, or **null**
     * to leave its current adjustment unchanged.
     * @param digits The number of decimal places to display in the [SpinButton].
     * @param climbRate The number of decimal places to display in the [SpinButton].
     */
    fun configure(adjustment: Adjustment? = null, digits: UInt = 1u, climbRate: Double = 1.0)
}