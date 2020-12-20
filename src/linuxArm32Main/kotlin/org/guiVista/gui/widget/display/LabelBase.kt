package org.guiVista.gui.widget.display

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase

public actual interface LabelBase : WidgetBase {
    public val gtkLabelPtr: CPointer<GtkLabel>?
        get() = gtkWidgetPtr?.reinterpret()

    /** The label's string contents. Default value is *""* (an empty String). */
    public var text: String
        set(value) = gtk_label_set_text(gtkLabelPtr, value)
        get() = gtk_label_get_text(gtkLabelPtr)?.toKString() ?: ""

    /**
     * The alignment of the lines in the text of the label relative to each other. This does NOT affect the alignment
     * of the label within its allocation. See `GtkLabel.xalign` for that. Default value is
     * *GtkJustification.GTK_JUSTIFY_LEFT*.
     */
    public var justify: GtkJustification
        set(value) = gtk_label_set_justify(gtkLabelPtr, value)
        get() = gtk_label_get_justify(gtkLabelPtr)

    /**
     * The desired maximum width of the label in characters. If this property is set to *-1* the width will be
     * calculated automatically. See the section on text layout for details of how “width-chars” and “max-width-chars”
     * determine the width of ellipsized and wrapped labels. Default value is *-1*.
     */
    public var maxWidthChars: Int
        set(value) {
            if (value >= -1) gtk_label_set_max_width_chars(gtkLabelPtr, value)
        }
        get() = gtk_label_get_max_width_chars(gtkLabelPtr)

    /**
     * The desired width of the label in characters. If this property is set to *-1* the width will be calculated
     * automatically. See the section on text layout for details of how widthChars and [maxWidthChars] determine
     * the width of ellipsized and wrapped labels. Default value is *-1*.
     */
    public var widthChars: Int
        set(value) {
            if (value >= -1) gtk_label_set_width_chars(gtkLabelPtr, value)
        }
        get() = gtk_label_get_width_chars(gtkLabelPtr)

    /**
     * The angle that the baseline of the label makes with the horizontal, in degrees, measured counterclockwise. An
     * angle of 90 reads from from bottom to top, an angle of 270, from top to bottom. Ignored if the label is
     * selectable. Default value is *0.0*.
     */
    public var angle: Double
        get() = gtk_label_get_angle(gtkLabelPtr)
        set(value) {
            if (value in (0.0..360.0)) gtk_label_set_angle(gtkLabelPtr, value)
        }

    /**
     * The preferred place to "ellipsize" the string, if the label does not have enough room to display the entire
     * string, specified as a PangoEllipsizeMode. Note that setting this property to a value other than
     * PANGO_ELLIPSIZE_NONE has the side-effect that the label requests only enough space to display the ellipsis
     * "...". In particular this means that "ellipsizing" labels do not work well in notebook tabs, unless the
     * GtkNotebook tab-expand child property is set to *true*. Other ways to set a label's width are
     * `gtk_widget_set_size_request()`, and [widthChars].
     *
     * Default value is *PangoEllipsizeMode.PANGO_ELLIPSIZE_NONE*.
     */
    public var ellipsize: PangoEllipsizeMode
        get() = gtk_label_get_ellipsize(gtkLabelPtr)
        set(value) = gtk_label_set_ellipsize(gtkLabelPtr, value)

    /**
     * The contents of the label. If the string contains Pango XML markup you will have to set the “use-markup”
     * property to *true* in order for the label to display the markup attributes. See also `gtk_label_set_markup()`
     * for a convenience function that sets both this property, and the “use-markup” property at the same time.
     *
     * If the string contains underlines acting as mnemonics you will have to set the “use-underline” property to
     * *true* in order for the label to display them. Default value is *""* (an empty String).
     */
    public var label: String
        get() = gtk_label_get_label(gtkLabelPtr)?.toKString() ?: ""
        set(value) = gtk_label_set_label(gtkLabelPtr, value)

    /**
     * The number of lines to which an "ellipsized", wrapping label should be limited. This property has no effect if
     * the label is not wrapping or "ellipsized". Set this property to *-1* if you don't want to limit the number of
     * lines. Default value is *-1*.
     */
    public var lines: Int
        get() = gtk_label_get_lines(gtkLabelPtr)
        set(value) {
            if (value >= -1) gtk_label_set_lines(gtkLabelPtr, value)
        }

    /** The mnemonic accelerator key for this label. Default value is *16777215*. */
    public val mnemonicKeyval: UInt
        get() = gtk_label_get_mnemonic_keyval(gtkLabelPtr)

    /** The widget to be activated when the label's mnemonic key is pressed. */
    public var mnemonicWidget: WidgetBase?
        get() {
            val tmp = gtk_label_get_mnemonic_widget(gtkLabelPtr)
            return if (tmp != null) Widget(tmp) else null
        }
        set(value) = gtk_label_set_mnemonic_widget(gtkLabelPtr, value?.gtkWidgetPtr)

    /** Whether the label text can be selected with the mouse. Default value is *false*. */
    public var selectable: Boolean
        get() = gtk_label_get_selectable(gtkLabelPtr) == TRUE
        set(value) = gtk_label_set_selectable(gtkLabelPtr, if (value) TRUE else FALSE)

    /**
     * Whether the label is in single line mode. In single line mode the height of the label does not depend on the
     * actual text, it is always set to ascent + descent of the font. This can be an advantage in situations where
     * resizing the label because of text changes would be distracting, e.g. in a statusbar.
     *
     * Default value is *false*.
     */
    public var singleLineMode: Boolean
        get() = gtk_label_get_single_line_mode(gtkLabelPtr) == TRUE
        set(value) = gtk_label_set_single_line_mode(gtkLabelPtr, if (value) TRUE else FALSE)

    /**
     * Set this property to *true* to make the label track which links have been visited. It will then apply the
     * GTK_STATE_FLAG_VISITED when rendering this link in addition to GTK_STATE_FLAG_LINK. Default value is *true*.
     */
    public var trackVisitedLinks: Boolean
        get() = gtk_label_get_track_visited_links(gtkLabelPtr) == TRUE
        set(value) = gtk_label_set_track_visited_links(gtkLabelPtr, if (value) TRUE else FALSE)

    /** The text of the label includes XML markup. See pango_parse_markup(). Default value is *false*. */
    public var useMarkup: Boolean
        get() = gtk_label_get_use_markup(gtkLabelPtr) == TRUE
        set(value) = gtk_label_set_use_markup(gtkLabelPtr, if (value) TRUE else FALSE)

    /**
     * If set an underline in the text indicates the next character should be used for the mnemonic accelerator key.
     * Default value is *false*.
     */
    public var useUnderline: Boolean
        get() = gtk_label_get_use_underline(gtkLabelPtr) == TRUE
        set(value) = gtk_label_set_use_underline(gtkLabelPtr, if (value) TRUE else FALSE)

    /** If set wrap lines if the text becomes too wide. Default value is *false*. */
    public var lineWrap: Boolean
        get() = gtk_label_get_line_wrap(gtkLabelPtr) == TRUE
        set(value) = gtk_label_set_line_wrap(gtkLabelPtr, if (value) TRUE else FALSE)

    /**
     * If line wrapping is on (see the [lineWrap] property) this controls how the line wrapping is done. Default value
     * is *PangoWrapMode.PANGO_WRAP_WORD* (wrap on word boundaries).
     */
    public var lineWrapMode: PangoWrapMode
        get() = gtk_label_get_line_wrap_mode(gtkLabelPtr)
        set(value) = gtk_label_set_line_wrap_mode(gtkLabelPtr, value)

    /**
     * This property determines the horizontal alignment of the label text inside the labels size allocation. Compare
     * this to “halign”, which determines how the labels size allocation is positioned in the space available for the
     * label. Default value is *0.5*.
     */
    public var xAlign: Float
        get() = gtk_label_get_xalign(gtkLabelPtr)
        set(value) {
            if (value in (0.0f..1.0f)) gtk_label_set_xalign(gtkLabelPtr, value)
        }

    /**
     * This property determines the horizontal alignment of the label text inside the labels size allocation. Compare
     * this to “valign”, which determines how the labels size allocation is positioned in the space available for the
     * label. Default value is *0.5*.
     */
    public var yAlign: Float
        get() = gtk_label_get_yalign(gtkLabelPtr)
        set(value) {
            if (value in (0.0f..1.0f)) gtk_label_set_yalign(gtkLabelPtr, value)
        }
}
