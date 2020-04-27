package org.guiVista.gui.widget.display

import org.guiVista.gui.widget.WidgetBase

/** Show a spinner animation. */
expect class Spinner : WidgetBase {
    /** Starts the animation of the spinner. */
    fun start()

    /** Stops the animation of the spinner. */
    fun stop()
}