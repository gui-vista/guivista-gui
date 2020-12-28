package org.guiVista.gui.text

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.*
import org.guiVista.core.ObjectBase
import org.guiVista.core.dataType.SinglyLinkedList

@Suppress("EqualsOrHashCode")
public actual class TextBufferIterator : ObjectBase {
    private val arena = Arena()
    private val gtkTextIter = arena.alloc<GtkTextIter>()
    public val gtkTextIterPtr: CPointer<GtkTextIter>
        get() = gtkTextIter.ptr

    public actual val buffer: TextBuffer
        get() = TextBuffer(gtk_text_iter_get_buffer(gtkTextIterPtr))

    public actual var offest: Int
        get() = gtk_text_iter_get_offset(gtkTextIterPtr)
        set(value) = gtk_text_iter_set_offset(gtkTextIterPtr, value)

    public actual var line: Int
        get() = gtk_text_iter_get_line(gtkTextIterPtr)
        set(value) = gtk_text_iter_set_line(gtkTextIterPtr, value)

    public actual var lineOffset: Int
        get() = gtk_text_iter_get_line_offset(gtkTextIterPtr)
        set(value) = gtk_text_iter_set_line_offset(gtkTextIterPtr, value)

    public actual var lineIndex: Int
        get() = gtk_text_iter_get_line_index(gtkTextIterPtr)
        set(value) = gtk_text_iter_set_line_index(gtkTextIterPtr, value)

    public actual var visibleLineIndex: Int
        get() = gtk_text_iter_get_visible_line_index(gtkTextIterPtr)
        set(value) = gtk_text_iter_set_visible_line_index(gtkTextIterPtr, value)

    public actual var visibleLineOffset: Int
        get() = gtk_text_iter_get_visible_line_offset(gtkTextIterPtr)
        set(value) = gtk_text_iter_set_visible_line_offset(gtkTextIterPtr, value)

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

        public actual fun order(first: TextBufferIterator, second: TextBufferIterator) {
            gtk_text_iter_order(first.gtkTextIterPtr, second.gtkTextIterPtr)
        }
    }

    public actual fun editable(defaultSetting: Boolean): Boolean =
        gtk_text_iter_editable(gtkTextIterPtr, default_setting = if (defaultSetting) TRUE else FALSE) == TRUE

    public actual fun canInsert(defaultEditability: Boolean): Boolean =
        gtk_text_iter_can_insert(gtkTextIterPtr, if (defaultEditability) TRUE else FALSE) == TRUE

    override fun close() {
        gtk_text_iter_free(gtkTextIter.ptr)
        arena.clear()
    }

    public actual fun forwardCharacters(count: Int): Boolean =
        if (count == 1) gtk_text_iter_forward_char(gtkTextIterPtr) == TRUE
        else gtk_text_iter_forward_chars(gtkTextIterPtr, count) == TRUE

    public actual fun backwardCharacters(count: Int): Boolean =
        if (count == 1) gtk_text_iter_backward_char(gtkTextIterPtr) == TRUE
        else gtk_text_iter_backward_chars(gtkTextIterPtr, count) == TRUE

    public actual fun forwardCursorPositions(count: Int): Boolean =
        if (count == 1) gtk_text_iter_forward_cursor_position(gtkTextIterPtr) == TRUE
        else gtk_text_iter_forward_cursor_positions(gtkTextIterPtr, count) == TRUE

    public actual fun backwardCursorPositions(count: Int): Boolean =
        if (count == 1) gtk_text_iter_backward_cursor_position(gtkTextIterPtr) == TRUE
        else gtk_text_iter_backward_cursor_positions(gtkTextIterPtr, count) == TRUE

    public actual fun forwardLines(count: Int): Boolean =
        if (count == 1) gtk_text_iter_forward_line(gtkTextIterPtr) == TRUE
        else gtk_text_iter_forward_lines(gtkTextIterPtr, count) == TRUE

    public actual fun backwardLines(count: Int): Boolean =
        if (count == 1) gtk_text_iter_backward_line(gtkTextIterPtr) == TRUE
        else gtk_text_iter_backward_lines(gtkTextIterPtr, count) == TRUE

    public actual fun backwardSentenceStarts(count: Int): Boolean =
        if (count == 1) gtk_text_iter_backward_sentence_start(gtkTextIterPtr) == TRUE
        else gtk_text_iter_backward_sentence_starts(gtkTextIterPtr, count) == TRUE

    public actual fun forwardSentenceEnds(count: Int): Boolean =
        if (count == 1) gtk_text_iter_forward_sentence_end(gtkTextIterPtr) == TRUE
        else gtk_text_iter_forward_sentence_ends(gtkTextIterPtr, count) == TRUE

    public actual fun forwardVisibleWordEnds(count: Int): Boolean =
        if (count == 1) gtk_text_iter_forward_visible_word_end(gtkTextIterPtr) == TRUE
        else gtk_text_iter_forward_visible_word_ends(gtkTextIterPtr, count) == TRUE

    public actual fun backwardVisibleWordStarts(count: Int): Boolean =
        if (count == 1) gtk_text_iter_backward_visible_word_start(gtkTextIterPtr) == TRUE
        else gtk_text_iter_backward_visible_word_starts(gtkTextIterPtr, count) == TRUE

    public actual fun forwardVisibleCursorPositions(count: Int): Boolean =
        if (count == 1) gtk_text_iter_forward_visible_cursor_position(gtkTextIterPtr) == TRUE
        else gtk_text_iter_forward_visible_cursor_positions(gtkTextIterPtr, count) == TRUE

    public actual fun backwardVisibleCursorPositions(count: Int): Boolean =
        if (count == 1) gtk_text_iter_backward_visible_cursor_position(gtkTextIterPtr) == TRUE
        else gtk_text_iter_backward_visible_cursor_positions(gtkTextIterPtr, count) == TRUE

    public actual fun forwardVisibleLines(count: Int): Boolean =
        if (count == 1) gtk_text_iter_forward_visible_line(gtkTextIterPtr) == TRUE
        else gtk_text_iter_forward_visible_lines(gtkTextIterPtr, count) == TRUE

    public actual fun backwardVisibleLines(count: Int): Boolean =
        if (count == 1) gtk_text_iter_backward_visible_line(gtkTextIterPtr) == TRUE
        else gtk_text_iter_backward_visible_lines(gtkTextIterPtr, count) == TRUE

    public actual fun forwardToEnd() {
        gtk_text_iter_forward_to_end(gtkTextIterPtr)
    }

    public actual fun forwardToLineEnd(): Boolean = gtk_text_iter_forward_to_line_end(gtkTextIterPtr) == TRUE

    public actual fun compare(otherIterator: TextBufferIterator): Int =
        gtk_text_iter_compare(gtkTextIterPtr, otherIterator.gtkTextIterPtr)

    public actual fun inRange(start: TextBufferIterator, end: TextBufferIterator): Boolean =
        gtk_text_iter_in_range(iter = gtkTextIterPtr, start = start.gtkTextIterPtr, end = end.gtkTextIterPtr) == TRUE

    external override fun equals(other: Any?): Boolean {
        return if (other !is TextBufferIterator) {
            throw IllegalArgumentException("The other parameter must be an instance of TextBufferIterator")
        } else {
            gtk_text_iter_equal(gtkTextIterPtr, other.gtkTextIterPtr) == TRUE
        }
    }
}
