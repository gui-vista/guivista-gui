package org.guiVista.gui.window

import gtk3.GtkScrolledWindow
import gtk3.GtkWidget
import gtk3.gtk_scrolled_window_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.Adjustment

public actual class ScrolledWindow(
    scrolledWindowPtr: CPointer<GtkScrolledWindow>? = null,
    newHAdjustment: Adjustment? = null,
    newVAdjustment: Adjustment? = null
) : ScrolledWindowBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = scrolledWindowPtr?.reinterpret()
        ?: gtk_scrolled_window_new(newHAdjustment?.gtkAdjustmentPtr, newVAdjustment?.gtkAdjustmentPtr)
}
