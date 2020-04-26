package org.guiVista.gui

/** Handles a GTK application including application lifecycle, and session management. */
expect class GuiApplication {
    // TODO: Figure out why the GUI Vista IO library can't be used in the common module.
    /**
     * Use a [GuiApplication] instance before closing it.
     * @param init Initialization block for the [GuiApplication] instance.
     */
    fun use(init: GuiApplication.() -> Unit)
}
