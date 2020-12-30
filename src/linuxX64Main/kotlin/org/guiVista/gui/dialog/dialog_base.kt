package org.guiVista.gui.dialog

import gtk3.GtkDialog
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.widget.WidgetBase
import org.guiVista.gui.window.WindowBase

public actual interface DialogBase : WindowBase {
    public actual val headerBar: WidgetBase?
    public actual val contentArea: WidgetBase?
    public val gtkDialogPtr: CPointer<GtkDialog>?
        get() = gtkWidgetPtr?.reinterpret()

    public actual fun run(): Int

    public actual fun response(responseId: Int)

    public actual fun addButton(buttonText: String, responseId: Int): WidgetBase?

    public actual fun addActionWidget(child: WidgetBase, responseId: Int)

    public actual fun changeDefaultResponse(responseId: Int)

    public actual fun changeResponseSensitive(responseId: Int, setting: Boolean)

    public actual fun fetchResponseForWidget(widget: WidgetBase): Int

    public actual fun fetchWidgetForResponse(responseId: Int): WidgetBase?
}
