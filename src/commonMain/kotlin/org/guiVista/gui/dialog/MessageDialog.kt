package org.guiVista.gui.dialog

import org.guiVista.gui.widget.WidgetBase

/** A convenient message window. */
public expect class MessageDialog : DialogBase {
    /**
     * Returns the message area of the dialog. This is the box where the dialog’s primary, and secondary labels are
     * packed. You can add your own extra content to that box, and it will appear below those labels. See
     * `gtk_dialog_get_content_area()` for the corresponding function in the parent [DialogBase].
     */
    public val messageArea: WidgetBase?

    /**
     * Sets the text of the [MessageDialog] to be [str], which is marked up with the Pango text markup language.
     */
    public infix fun changeMarkup(str: String)
}
