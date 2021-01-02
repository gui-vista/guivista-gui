package org.guiVista.gui.dialog

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.disconnectGSignal
import org.guiVista.gui.window.WindowBase

public actual class FileChooserDialog(
    fileChooserDialogPtr: CPointer<GtkFileChooserDialog>? = null,
    title: String = "",
    parent: WindowBase? = null,
    action: GtkFileChooserAction = GtkFileChooserAction.GTK_FILE_CHOOSER_ACTION_SELECT_FOLDER,
    firstButtonText: String = "Ok",
    firstButtonResponseId: Int = GTK_RESPONSE_OK
) : FileChooser, DialogBase {
    private var _gtkFileChooserDialogPtr: CPointer<GtkFileChooserDialog>? = null
    public val gtkFileChooserDialogPtr: CPointer<GtkFileChooserDialog>?
        get() = _gtkFileChooserDialogPtr
    override val gtkWidgetPtr: CPointer<GtkWidget>?
        get() = gtkFileChooserDialogPtr?.reinterpret()
    override val gtkFileChooserPtr: CPointer<GtkFileChooser>?
        get() = gtkFileChooserDialogPtr?.reinterpret()

    init {
        _gtkFileChooserDialogPtr = fileChooserDialogPtr
            ?: if (parent != null && title.isNotEmpty()) {
                create(
                    title = title,
                    parent = parent,
                    action = action,
                    firstButtonText = firstButtonText,
                    firstButtonResponseId = firstButtonResponseId
                )
            } else {
                throw IllegalArgumentException("The parent and title parameters must be specified")
            }
    }

    private fun create(
        title: String,
        parent: WindowBase,
        action: GtkFileChooserAction,
        firstButtonText: String,
        firstButtonResponseId: Int
    ): CPointer<GtkFileChooserDialog>? = gtk_file_chooser_dialog_new(
        title = title,
        parent = parent.gtkWindowPtr,
        action = action,
        first_button_text = firstButtonText,
        firstButtonResponseId,
        null
    )?.reinterpret()

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkFileChooserPtr, handlerId)
    }
}

public fun fileChooserDialog(
    title: String,
    parent: WindowBase,
    action: GtkFileChooserAction = GtkFileChooserAction.GTK_FILE_CHOOSER_ACTION_SELECT_FOLDER,
    firstButtonText: String = "Ok",
    firstButtonResponseId: Int = GTK_RESPONSE_OK,
    init: FileChooserDialog.() -> Unit
): FileChooserDialog {
    val dialog = FileChooserDialog(parent = parent, title = title, action = action, firstButtonText = firstButtonText,
        firstButtonResponseId = firstButtonResponseId)
    dialog.init()
    return dialog
}
