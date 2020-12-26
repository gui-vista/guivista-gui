package org.guiVista.gui.widget.textEditor

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.gui.layout.Container
import org.guiVista.gui.text.TextBuffer
import org.guiVista.gui.widget.WidgetBase

public actual class TextView(
    textViewPtr: CPointer<GtkTextView>? = null,
    textBufferPtr: CPointer<GtkTextBuffer>? = null
) : Container {
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        if (textViewPtr != null && textBufferPtr == null) textViewPtr.reinterpret()
        else if (textBufferPtr != null && textViewPtr == null) gtk_text_view_new_with_buffer(textBufferPtr)
        else gtk_text_view_new()
    public val gtkTextViewPtr: CPointer<GtkTextView>?
        get() = gtkWidgetPtr?.reinterpret()
    public actual var buffer: TextBuffer
        get() = TextBuffer(gtk_text_view_get_buffer(gtkTextViewPtr))
        set(value) = gtk_text_view_set_buffer(gtkTextViewPtr, value.gtkTextBufferPtr)
    public actual var editable: Boolean
        get() = gtk_text_view_get_editable(gtkTextViewPtr) == TRUE
        set(value) = gtk_text_view_set_editable(gtkTextViewPtr, if (value) TRUE else FALSE)
    public actual var cursorVisible: Boolean
        get() = gtk_text_view_get_cursor_visible(gtkTextViewPtr) == TRUE
        set(value) = gtk_text_view_set_cursor_visible(gtkTextViewPtr, if (value) TRUE else FALSE)
    public actual var overwrite: Boolean
        get() = gtk_text_view_get_overwrite(gtkTextViewPtr) == TRUE
        set(value) = gtk_text_view_set_overwrite(gtkTextViewPtr, if (value) TRUE else FALSE)
    public actual var pixelsAboveLines: Int
        get() = gtk_text_view_get_pixels_above_lines(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The pixelsAboveLines value must be >= 0.")
            gtk_text_view_set_pixels_above_lines(gtkTextViewPtr, value)
        }
    public actual var pixelsBelowLines: Int
        get() = gtk_text_view_get_pixels_below_lines(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The pixelsBelowLines value must be >= 0.")
            gtk_text_view_set_pixels_below_lines(gtkTextViewPtr, value)
        }
    public actual var pixelsInsideWrap: Int
        get() = gtk_text_view_get_pixels_inside_wrap(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The pixelsInsideWrap value must be >= 0.")
            gtk_text_view_set_pixels_inside_wrap(gtkTextViewPtr, value)
        }
    public actual var leftMargin: Int
        get() = gtk_text_view_get_left_margin(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The leftMargin value must be >= 0.")
            gtk_text_view_set_left_margin(gtkTextViewPtr, value)
        }
    public actual var rightMargin: Int
        get() = gtk_text_view_get_right_margin(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The rightMargin value must be >= 0.")
            gtk_text_view_set_right_margin(gtkTextViewPtr, value)
        }
    public actual var topMargin: Int
        get() = gtk_text_view_get_top_margin(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The topMargin value must be >= 0.")
            gtk_text_view_set_top_margin(gtkTextViewPtr, value)
        }
    public actual var bottomMargin: Int
        get() = gtk_text_view_get_bottom_margin(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The bottomMargin value must be >= 0.")
            gtk_text_view_set_bottom_margin(gtkTextViewPtr, value)
        }
    public actual var indent: Int
        get() = gtk_text_view_get_indent(gtkTextViewPtr)
        set(value) = gtk_text_view_set_indent(gtkTextViewPtr, value)
    public actual var acceptsTab: Boolean
        get() = gtk_text_view_get_accepts_tab(gtkTextViewPtr) == TRUE
        set(value) = gtk_text_view_set_accepts_tab(gtkTextViewPtr, if (value) TRUE else FALSE)
    public actual var monospace: Boolean
        get() = gtk_text_view_get_monospace(gtkTextViewPtr) == TRUE
        set(value) = gtk_text_view_set_monospace(gtkTextViewPtr, if (value) TRUE else FALSE)

    public actual fun placeCursorOnscreen(): Boolean = gtk_text_view_place_cursor_onscreen(gtkTextViewPtr) == TRUE

    public actual fun moveChild(child: WidgetBase, xPos: Int, yPos: Int) {
        gtk_text_view_move_child(text_view = gtkTextViewPtr, child = child.gtkWidgetPtr, xpos = xPos, ypos = yPos)
    }

    public actual fun resetCursorBlink() {
        gtk_text_view_reset_cursor_blink(gtkTextViewPtr)
    }
}

public fun textViewWidget(
    textViewPtr: CPointer<GtkTextView>? = null,
    textBufferPtr: CPointer<GtkTextBuffer>? = null,
    init: TextView.() -> Unit
): TextView {
    val textView = TextView(textViewPtr, textBufferPtr)
    textView.init()
    return textView
}
