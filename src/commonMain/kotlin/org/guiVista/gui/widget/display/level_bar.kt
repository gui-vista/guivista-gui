package org.guiVista.gui.widget.display

import org.guiVista.gui.widget.WidgetBase

/** A bar that can used as a level indicator. */
public expect class LevelBar : WidgetBase {
    /**
     * Level bars normally grow from top to bottom or left to right. Inverted level bars grow in the opposite
     * direction. Default value is *false*.
     */
    public var inverted: Boolean

    /** Determines the maximum value of the interval that can be displayed by the bar. Default value is *1.0*. */
    public var maxValue: Double

    /** determines the minimum value of the interval that can be displayed by the bar. Default value is *0.0*. */
    public var minValue: Double

    /** Determines the currently filled value of the level bar. Default value is *0.0*. */
    public var value: Double
}