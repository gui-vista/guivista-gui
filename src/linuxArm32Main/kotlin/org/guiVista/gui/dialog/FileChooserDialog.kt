package org.guiVista.gui.dialog

import gtk3.GtkFileChooser
import gtk3.GtkFileChooserAction
import gtk3.GtkFileChooserDialog
import gtk3.gtk_file_chooser_dialog_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.window.WindowBase

public actual class FileChooserDialog(
    fileChooserDialogPtr: CPointer<GtkFileChooserDialog>? = null,
    title: String = "",
    parent: WindowBase? = null,
    action: GtkFileChooserAction = GtkFileChooserAction.GTK_FILE_CHOOSER_ACTION_SELECT_FOLDER,
    firstButtonText: String = ""
) : FileChooser {
    private var _gtkFileChooserDialogPtr: CPointer<GtkFileChooserDialog>? = null
    public val gtkFileChooserDialogPtr: CPointer<GtkFileChooserDialog>?
        get() = _gtkFileChooserDialogPtr
    override val gtkFileChooserPtr: CPointer<GtkFileChooser>?
        get() = gtkFileChooserDialogPtr?.reinterpret()

    init {
        _gtkFileChooserDialogPtr = fileChooserDialogPtr
            ?: if (parent != null && title.isNotEmpty() && firstButtonText.isNotEmpty()) {
                create(title = title, parent = parent, action = action,
                    firstButtonText = firstButtonText)
            } else {
                throw IllegalArgumentException("The parent, title, and firstButtonText parameters must be specified")
            }
    }

    private fun create(
        title: String,
        parent: WindowBase,
        action: GtkFileChooserAction,
        firstButtonText: String
    ): CPointer<GtkFileChooserDialog>? = gtk_file_chooser_dialog_new(
        title = title,
        parent = parent.gtkWindowPtr,
        action = action,
        first_button_text = firstButtonText,
        null
    )?.reinterpret()
}
