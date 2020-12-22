package org.guiVista.gui.widget.display

import org.guiVista.gui.layout.Container

/** Report important messages to the user. */
public expect class InfoBar : Container {
    /** Whether to include a standard close button. Default value is *false*. */
    public var showCloseButton: Boolean

    /**
     * Adds more buttons which is the same as calling `addButton` repeatedly. Does the same thing as
     * `gtk_info_bar_add_buttons`.
     * @param buttons Pairs where each one consists of a Response ID, and button text.
     */
    public fun addMultipleButtons(vararg buttons: Pair<Int, String>)

    /** Emits the *response* signal with the given [Response ID][responseId] .*/
    public fun response(responseId: Int)
}
