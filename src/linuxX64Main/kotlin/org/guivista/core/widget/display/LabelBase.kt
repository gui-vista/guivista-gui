package org.guivista.core.widget.display

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guivista.core.widget.WidgetBase

/** Base interface for label objects. */
interface LabelBase : WidgetBase {
    val gtkLabelPtr: CPointer<GtkLabel>?
        get() = gtkWidgetPtr?.reinterpret()
    /** The label's string contents. Default value is *""* (an empty String). */
    var text: String
        set(value) = gtk_label_set_text(gtkLabelPtr, value)
        get() = gtk_label_get_text(gtkLabelPtr)?.toKString() ?: ""
    /**
     * The alignment of the lines in the text of the label relative to each other. This does NOT affect the alignment
     * of the label within its allocation. See `GtkLabel.xalign` for that. Default value is
     * *GtkJustification.GTK_JUSTIFY_LEFT*.
     */
    var justify: GtkJustification
        set(value) = gtk_label_set_justify(gtkLabelPtr, value)
        get() = gtk_label_get_justify(gtkLabelPtr)
    /**
     * The desired maximum width of the label in characters. If this property is set to *-1* the width will be
     * calculated automatically. See the section on text layout for details of how “width-chars” and “max-width-chars”
     * determine the width of ellipsized and wrapped labels. Default value is *-1*.
     */
    var maxWidthChars: Int
        set(value) {
            if (value >= -1) gtk_label_set_max_width_chars(gtkLabelPtr, value)
        }
        get() = gtk_label_get_max_width_chars(gtkLabelPtr)
    /**
     * The desired width of the label in characters. If this property is set to *-1* the width will be calculated
     * automatically. See the section on text layout for details of how widthChars and [maxWidthChars] determine
     * the width of ellipsized and wrapped labels. Default value is *-1*.
     */
    var widthChars: Int
        set(value) {
            if (value >= -1) gtk_label_set_width_chars(gtkLabelPtr, value)
        }
        get() = gtk_label_get_width_chars(gtkLabelPtr)
}
