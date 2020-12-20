package org.guiVista.gui.widget.display

import org.guiVista.gui.widget.WidgetBase

/** Show a spinner animation. */
public expect class Spinner : WidgetBase {
    /** Starts the animation of the spinner. */
    public fun start()

    /** Stops the animation of the spinner. */
    public fun stop()
}