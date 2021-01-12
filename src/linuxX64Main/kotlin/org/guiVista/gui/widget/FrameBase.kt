package org.guiVista.gui.widget

import gtk3.*
import kotlinx.cinterop.*
import org.guiVista.gui.layout.Container
import platform.posix.float_tVar

public actual interface FrameBase : Container {
    public val gtkFramePtr: CPointer<GtkFrame>?

    /** Text of the frame's label. Default value is *""*. */
    public var label: String
        get() = gtk_frame_get_label(gtkFramePtr)?.toKString() ?: ""
        set(value) = gtk_frame_set_label(gtkFramePtr, value)

    /** A widget to display in place of the usual frame label. */
    public var labelWidget: WidgetBase?
        get() {
            val ptr = gtk_frame_get_label_widget(gtkFramePtr)
            return if (ptr != null) Widget(ptr) else null
        }
        set(value) = gtk_frame_set_label_widget(gtkFramePtr, value?.gtkWidgetPtr)

    /** Appearance of the frame border. Default value is `GTK_SHADOW_ETCHED_IN`. */
    public var shadowType: GtkShadowType
        get() = gtk_frame_get_shadow_type(gtkFramePtr)
        set(value) = gtk_frame_set_shadow_type(gtkFramePtr, value)

    /**
     * Sets the alignment of the frame widget’s label. The default values for a newly created frame are *0.0*, and
     * *0.5*.
     * @param xAlign The position of the label along the top edge of the widget. A value of *0.0* represents left
     * alignment; *1.0* represents right alignment.
     * @param yAlign The y alignment of the label. A value of *0.0* aligns under the frame; *1.0* aligns above the
     * frame. If the values are exactly *0.0*, or *1.0* the gap in the frame won’t be painted because the label will be
     * completely above or below the frame.
     */
    public fun changeLabelAlign(xAlign: Float, yAlign: Float) {
        gtk_frame_set_label_align(frame = gtkFramePtr, xalign = xAlign, yalign = yAlign)
    }

    /**
     * Retrieves the X and Y alignment of the frame’s label.
     * @return A Pair that contains the following:
     * 1. xAlign
     * 2. yAlign
     * @see changeLabelAlign
     */
    public fun fetchLabelAlign(): Pair<Float, Float> = memScoped {
        val xAlign = alloc<float_tVar>()
        val yAlign = alloc<float_tVar>()
        gtk_frame_get_label_align(frame = gtkFramePtr, xalign = xAlign.ptr, yalign = yAlign.ptr)
        xAlign.value to yAlign.value
    }
}
