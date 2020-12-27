package org.guiVista.gui.text

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.*
import org.guiVista.core.ObjectBase
import org.guiVista.core.dataType.SinglyLinkedList

public actual class TextBufferIterator : ObjectBase {
    private val arena = Arena()
    private val gtkTextIter = arena.alloc<GtkTextIter>()
    public val gtkTextIterPtr: CPointer<GtkTextIter>
        get() = gtkTextIter.ptr

    public actual val buffer: TextBuffer
        get() = TextBuffer(gtk_text_iter_get_buffer(gtkTextIterPtr))

    public actual val offest: Int
        get() = gtk_text_iter_get_offset(gtkTextIterPtr)

    public actual val line: Int
        get() = gtk_text_iter_get_line(gtkTextIterPtr)

    public actual val lineOffset: Int
        get() = gtk_text_iter_get_line_offset(gtkTextIterPtr)

    public actual val lineIndex: Int
        get() = gtk_text_iter_get_line_index(gtkTextIterPtr)

    public actual val visibleLineIndex: Int
        get() = gtk_text_iter_get_visible_line_index(gtkTextIterPtr)

    public actual val visibleLineOffset: Int
        get() = gtk_text_iter_get_visible_line_offset(gtkTextIterPtr)

    public actual val char: UInt
        get() = gtk_text_iter_get_char(gtkTextIterPtr)

    public actual val marks: SinglyLinkedList
        get() = SinglyLinkedList(gtk_text_iter_get_marks(gtkTextIterPtr))

    public actual val startsWord: Boolean
        get() = gtk_text_iter_starts_word(gtkTextIterPtr) == TRUE

    public actual val endsWord: Boolean
        get() = gtk_text_iter_ends_word(gtkTextIterPtr) == TRUE

    public actual val insideWord: Boolean
        get() = gtk_text_iter_inside_word(gtkTextIterPtr) == TRUE

    public actual val startsLine: Boolean
        get() = gtk_text_iter_starts_line(gtkTextIterPtr) == TRUE

    public actual val endsLine: Boolean
        get() = gtk_text_iter_ends_line(gtkTextIterPtr) == TRUE

    public actual val startsSentence: Boolean
        get() = gtk_text_iter_starts_sentence(gtkTextIterPtr) == TRUE

    public actual val endsSentence: Boolean
        get() = gtk_text_iter_ends_sentence(gtkTextIterPtr) == TRUE

    public actual val insideSentence: Boolean
        get() = gtk_text_iter_inside_sentence(gtkTextIterPtr) == TRUE

    public actual val isCursorPosition: Boolean
        get() = gtk_text_iter_is_cursor_position(gtkTextIterPtr) == TRUE

    public actual val charsInLine: Int
        get() = gtk_text_iter_get_chars_in_line(gtkTextIterPtr)

    public actual val bytesInLine: Int
        get() = gtk_text_iter_get_bytes_in_line(gtkTextIterPtr)

    public actual val isEnd: Boolean
        get() = gtk_text_iter_is_end(gtkTextIterPtr) == TRUE

    public actual val isStart: Boolean
        get() = gtk_text_iter_is_start(gtkTextIterPtr) == TRUE

    public actual companion object {
        public actual fun fetchSlice(start: TextBufferIterator, end: TextBufferIterator): String =
            gtk_text_iter_get_slice(start.gtkTextIterPtr, end.gtkTextIterPtr)?.toKString() ?: ""

        public actual fun fetchText(start: TextBufferIterator, end: TextBufferIterator): String =
            gtk_text_iter_get_text(start.gtkTextIterPtr, end.gtkTextIterPtr)?.toKString() ?: ""

        public actual fun fetchVisibleSlice(start: TextBufferIterator, end: TextBufferIterator): String =
            gtk_text_iter_get_visible_slice(start.gtkTextIterPtr, end.gtkTextIterPtr)?.toKString() ?: ""

        public actual fun fetchVisibleText(start: TextBufferIterator, end: TextBufferIterator): String =
            gtk_text_iter_get_visible_text(start.gtkTextIterPtr, end.gtkTextIterPtr)?.toKString() ?: ""
    }

    public actual fun editable(defaultSetting: Boolean): Boolean =
        gtk_text_iter_editable(gtkTextIterPtr, default_setting = if (defaultSetting) TRUE else FALSE) == TRUE

    public actual fun canInsert(defaultEditability: Boolean): Boolean =
        gtk_text_iter_can_insert(gtkTextIterPtr, if (defaultEditability) TRUE else FALSE) == TRUE

    override fun close() {
        gtk_text_iter_free(gtkTextIter.ptr)
        arena.clear()
    }

    public actual fun forwardChar(): Boolean = gtk_text_iter_forward_char(gtkTextIterPtr) == TRUE

    public actual fun backwardChar(): Boolean = gtk_text_iter_backward_char(gtkTextIterPtr) == TRUE

    public actual fun forwardMultipleChars(count: Int): Boolean =
        gtk_text_iter_forward_chars(gtkTextIterPtr, count) == TRUE

    public actual fun backwardMultipleChars(count: Int): Boolean =
        gtk_text_iter_backward_chars(gtkTextIterPtr, count) == TRUE
}
