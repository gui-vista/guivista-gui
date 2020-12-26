package org.guiVista.gui.text

import glib2.FALSE
import glib2.TRUE
import glib2.g_object_unref
import gtk3.*
import kotlinx.cinterop.CPointer
import org.guiVista.core.ObjectBase

public actual class TextBuffer(textBufferPtr: CPointer<GtkTextBuffer>? = null) : ObjectBase {
    public val gtkTextBufferPtr: CPointer<GtkTextBuffer>? = textBufferPtr ?: gtk_text_buffer_new(null)

    public actual var modified: Boolean
        get() = gtk_text_buffer_get_modified(gtkTextBufferPtr) == TRUE
        set(value) = gtk_text_buffer_set_modified(gtkTextBufferPtr, if (value) TRUE else FALSE)

    public actual val hasSelection: Boolean
        get() = gtk_text_buffer_get_has_selection(gtkTextBufferPtr) == TRUE

    public actual val lineCount: Int
        get() = gtk_text_buffer_get_line_count(gtkTextBufferPtr)

    public actual val charCount: Int
        get() = gtk_text_buffer_get_char_count(gtkTextBufferPtr)

    public actual infix fun insertAtCursor(text: String) {
        gtk_text_buffer_insert_at_cursor(buffer = gtkTextBufferPtr, text = text, len = -1)
    }

    public actual fun insertInteractiveAtCursor(text: String, defaultEditable: Boolean): Boolean =
        gtk_text_buffer_insert_interactive_at_cursor(
            buffer = gtkTextBufferPtr,
            text = text,
            len = -1,
            default_editable = if (defaultEditable) TRUE else FALSE
        ) == TRUE

    public actual fun changeText(text: String) {
        gtk_text_buffer_set_text(buffer = gtkTextBufferPtr, text = text, len = -1)
    }

    public actual fun deleteSelection(interactive: Boolean, defaultEditable: Boolean): Boolean =
        gtk_text_buffer_delete_selection(
            buffer = gtkTextBufferPtr,
            interactive = if (interactive) TRUE else FALSE,
            default_editable = if (defaultEditable) TRUE else FALSE
        ) == TRUE

    public actual fun beginUserAction() {
        gtk_text_buffer_begin_user_action(gtkTextBufferPtr)
    }

    public actual fun endUserAction() {
        gtk_text_buffer_end_user_action(gtkTextBufferPtr)
    }

    override fun close() {
        g_object_unref(gtkTextBufferPtr)
    }
}

public fun textBuffer(textBufferPtr: CPointer<GtkTextBuffer>? = null, init: TextBuffer.() -> Unit): TextBuffer {
    val textBuffer = TextBuffer(textBufferPtr)
    textBuffer.init()
    return textBuffer
}
