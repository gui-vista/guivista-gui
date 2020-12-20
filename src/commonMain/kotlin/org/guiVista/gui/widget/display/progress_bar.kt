package org.guiVista.gui.widget.display

import org.guiVista.gui.widget.WidgetBase

/** A widget which indicates progress visually. */
public expect class ProgressBar : WidgetBase {
    /** The fraction of total work that has been completed. Default value is *0.0*. */
    public var fraction: Double

    /** Invert the direction in which the progress bar grows. Default value is *false*. */
    public var inverted: Boolean

    /** The fraction of total progress to move the bouncing block when pulsed. Default value is *0.1*. */
    public var pulseStep: Double

    /**
     * Sets whether the progress bar will show a text in addition to the bar itself. The shown text is either the
     * value of the [text] property, or if that is *null* the “fraction” value as a percentage. To make a progress bar
     * that is styled and sized suitably for showing text (even if the actual text is blank), set “show-text” to *true*
     * and “text” to the empty string (not *null*).
     *
     * Default value is *false*.
     */
    public var showText: Boolean

    /** Text to be displayed in the progress bar. Default value is *""* (an empty String). */
    public var text: String

    /**
     * Indicates that some progress has been made, but you don’t know how much. Causes the progress bar to enter
     * “activity mode” where a block bounces back and forth. Each call to [pulse] causes the block to move by a little
     * bit (the amount of movement per pulse is determined by [pulseStep]).
     */
    public fun pulse()
}
