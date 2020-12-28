package org.guiVista.gui.text

import glib2.FALSE
import glib2.TRUE
import glib2.g_object_unref
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
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

    public actual fun insert(iterator: TextBufferIterator, text: String) {
        gtk_text_buffer_insert(buffer = gtkTextBufferPtr, iter = iterator.gtkTextIterPtr, text = text, len = -1)
    }

    public actual fun insertInteractive(
        iterator: TextBufferIterator,
        text: String,
        defaultEditable: Boolean
    ): Boolean = gtk_text_buffer_insert_interactive(
        buffer = gtkTextBufferPtr,
        iter = iterator.gtkTextIterPtr,
        text = text,
        default_editable = if (defaultEditable) TRUE else FALSE,
        len = -1
    ) == TRUE

    public actual fun insertRange(iterator: TextBufferIterator, start: TextBufferIterator, end: TextBufferIterator) {
        gtk_text_buffer_insert_range(
            buffer = gtkTextBufferPtr,
            iter = iterator.gtkTextIterPtr,
            start = start.gtkTextIterPtr,
            end = end.gtkTextIterPtr
        )
    }

    public actual fun insertRangeInteractive(
        iterator: TextBufferIterator,
        start: TextBufferIterator,
        end: TextBufferIterator,
        defaultEditable: Boolean
    ): Boolean = gtk_text_buffer_insert_range_interactive(
        buffer = gtkTextBufferPtr,
        iter = iterator.gtkTextIterPtr,
        start = start.gtkTextIterPtr,
        end = end.gtkTextIterPtr,
        default_editable = if (defaultEditable) TRUE else FALSE
    ) == TRUE

    public actual fun insertMarkup(iterator: TextBufferIterator, markup: String) {
        gtk_text_buffer_insert_markup(buffer = gtkTextBufferPtr, iter = iterator.gtkTextIterPtr, markup = markup,
            len = -1)
    }

    public actual fun delete(start: TextBufferIterator, end: TextBufferIterator) {
        gtk_text_buffer_delete(buffer = gtkTextBufferPtr, start = start.gtkTextIterPtr, end = end.gtkTextIterPtr)
    }

    public actual fun deleteInteractive(
        start: TextBufferIterator,
        end: TextBufferIterator,
        defaultEditable: Boolean
    ): Boolean = gtk_text_buffer_delete_interactive(
        buffer = gtkTextBufferPtr,
        start_iter = start.gtkTextIterPtr,
        end_iter = end.gtkTextIterPtr,
        default_editable = if (defaultEditable) TRUE else FALSE
    ) == TRUE

    public actual fun backspace(
        iterator: TextBufferIterator,
        interactive: Boolean,
        defaultEditable: Boolean
    ): Boolean = gtk_text_buffer_backspace(
        buffer = gtkTextBufferPtr,
        iter = iterator.gtkTextIterPtr,
        interactive = if (interactive) TRUE else FALSE,
        default_editable = if (defaultEditable) TRUE else FALSE
    ) == TRUE

    public actual fun fetchSlice(
        start: TextBufferIterator,
        end: TextBufferIterator,
        includeHiddenChars: Boolean
    ): String = gtk_text_buffer_get_slice(
        buffer = gtkTextBufferPtr,
        start = start.gtkTextIterPtr,
        end = end.gtkTextIterPtr,
        include_hidden_chars = if (includeHiddenChars) TRUE else FALSE
    )?.toKString() ?: ""

    public actual fun placeCursor(where: TextBufferIterator) {
        gtk_text_buffer_place_cursor(gtkTextBufferPtr, where.gtkTextIterPtr)
    }

    public actual fun selectRange(insert: TextBufferIterator, bound: TextBufferIterator) {
        gtk_text_buffer_select_range(buffer = gtkTextBufferPtr, ins = insert.gtkTextIterPtr,
            bound = bound.gtkTextIterPtr)
    }

    public actual fun fetchIteratorAtLineOffset(iterator: TextBufferIterator, lineNum: Int, charOffset: Int) {
        gtk_text_buffer_get_iter_at_line_offset(
            buffer = gtkTextBufferPtr,
            iter = iterator.gtkTextIterPtr,
            line_number = lineNum,
            char_offset = charOffset
        )
    }

    public actual fun fetchIteratorAtOffset(iterator: TextBufferIterator, charOffset: Int) {
        gtk_text_buffer_get_iter_at_offset(buffer = gtkTextBufferPtr, iter = iterator.gtkTextIterPtr,
            char_offset = charOffset)
    }

    public actual fun fetchIteratorAtLine(iterator: TextBufferIterator, lineNum: Int) {
        gtk_text_buffer_get_iter_at_line(buffer = gtkTextBufferPtr, iter = iterator.gtkTextIterPtr,
            line_number = lineNum)
    }

    public actual fun fetchIteratorAtLineIndex(iterator: TextBufferIterator, lineNum: Int, byteIndex: Int) {
        gtk_text_buffer_get_iter_at_line_index(
            buffer = gtkTextBufferPtr,
            iter = iterator.gtkTextIterPtr,
            line_number = lineNum,
            byte_index = byteIndex
        )
    }

    public actual fun fetchStartIterator(iterator: TextBufferIterator) {
        gtk_text_buffer_get_start_iter(gtkTextBufferPtr, iterator.gtkTextIterPtr)
    }

    public actual fun fetchEndIterator(iterator: TextBufferIterator) {
        gtk_text_buffer_get_end_iter(gtkTextBufferPtr, iterator.gtkTextIterPtr)
    }

    public actual fun fetchBounds(start: TextBufferIterator, end: TextBufferIterator) {
        gtk_text_buffer_get_bounds(buffer = gtkTextBufferPtr, start = start.gtkTextIterPtr, end = end.gtkTextIterPtr)
    }

    public actual fun fetchSelectionBounds(
        start: TextBufferIterator?,
        end: TextBufferIterator?
    ): Boolean = gtk_text_buffer_get_selection_bounds(
        buffer = gtkTextBufferPtr,
        start = start?.gtkTextIterPtr,
        end = end?.gtkTextIterPtr
    ) == TRUE
}

public fun textBuffer(textBufferPtr: CPointer<GtkTextBuffer>? = null, init: TextBuffer.() -> Unit): TextBuffer {
    val textBuffer = TextBuffer(textBufferPtr)
    textBuffer.init()
    return textBuffer
}
