package org.guiVista.gui.widget.display

import org.guiVista.gui.layout.Container

/** Report important messages to the user. */
expect class InfoBar : Container {
    /** Controls whether the action bar shows its contents or not. Default value is *true*. */
    var revealed: Boolean

    /** Whether to include a standard close button. Default value is *false*. */
    var showCloseButton: Boolean

    /**
     * Adds more buttons which is the same as calling `addButton` repeatedly. Does the same thing as
     * `gtk_info_bar_add_buttons`.
     * @param buttons Pairs where each one consists of a Response ID, and button text.
     */
    fun addMultipleButtons(vararg buttons: Pair<Int, String>)

    /** Emits the *response* signal with the given [Response ID][responseId] .*/
    fun response(responseId: Int)
}
