package org.guiVista.gui.widget

import gtk3.GtkFrame
import gtk3.GtkWidget
import gtk3.gtk_frame_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

public actual class Frame(ptr: CPointer<GtkFrame>? = null, label: String = "") : FrameBase {
    override val gtkFramePtr: CPointer<GtkFrame>? =
        ptr ?: gtk_frame_new(if (label.isNotEmpty()) label else null)?.reinterpret()
    override val gtkWidgetPtr: CPointer<GtkWidget>?
        get() = gtkFramePtr?.reinterpret()
}

public fun frameWidget(ptr: CPointer<GtkFrame>? = null, label: String = "", init: Frame.() -> Unit = {}): Frame {
    val frame = Frame(ptr, label)
    frame.init()
    return frame
}
