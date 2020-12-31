package org.guiVista.gui.dialog

import gtk3.GtkDialog
import gtk3.GtkWidget
import gtk3.gtk_dialog_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class Dialog(dialogPtr: CPointer<GtkDialog>? = null) : DialogBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = dialogPtr?.reinterpret() ?: gtk_dialog_new()
}
