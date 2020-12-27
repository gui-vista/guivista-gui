package org.guiVista.gui.text

import org.guiVista.core.ObjectBase
import org.guiVista.core.dataType.SinglyLinkedList

/** Iterates over a [TextBuffer]. Used for manipulating text. */
public expect class TextBufferIterator : ObjectBase {
    /** The buffer this iterator is associated with. */
    public val buffer: TextBuffer

    /**
     * The character offset of an iterator. Each character in the [buffer] has an offset, starting with 0 for the first
     * character in the buffer. Use `gtk_text_buffer_get_iter_at_offset()` to convert an offset back into an iterator.
     */
    public val offest: Int

    /**
     * The line number containing the iterator. Lines in the [buffer] are numbered beginning with 0 for the first line
     * in the buffer.
     */
    public val line: Int

    /**
     * The character offset of the iterator, counting from the start of a newline terminated line. The first character
     * on the line has offset 0.
     */
    public val lineOffset: Int

    /**
     * The byte index of the iterator, counting from the start of a newline terminated line. Remember that [TextBuffer]
     * encodes text in UTF-8, and that characters can require a variable number of bytes to represent.
     */
    public val lineIndex: Int

    /**
     * The number of bytes from the start of the line to the given iterator, not counting bytes that are invisible due
     * to tags with the **invisible** flag toggled on.
     */
    public val visibleLineIndex: Int

    /**
     * The offset in characters from the start of the line to the given iterator, not counting characters that are
     * invisible due to tags with the **invisible** flag toggled on.
     */
    public val visibleLineOffset: Int

    /**
     * The Unicode character at this iterator is returned. If the element at this iterator is a non-character element,
     * such as an image embedded in the [buffer], the Unicode **unknown** character **0xFFFC** is returned. If invoked
     * on the end iterator, *0* is returned; Note that *0* is not a valid Unicode character. So you can write a loop which
     * ends when [char] returns *0*.
     */
    public val char: UInt

    /**
     * A list of all `GtkTextMark` at this location. Because marks are not iterable (they don’t take up any *space* in
     * the buffer, they are just marks in between iterable locations), multiple marks can exist in the same place. The
     * returned list is not in any meaningful order.
     */
    public val marks: SinglyLinkedList

    /**
     * Determines whether this iterator begins a natural language word. Word breaks are determined by Pango, and should
     * be correct for nearly any language (if not then the correct fix would be with the Pango word break algorithms).
     */
    public val startsWord: Boolean

    /**
     * Determines whether this iterator ends a natural language word. Word breaks are determined by Pango, and should
     * be correct for nearly any language (if not then the correct fix would be with the Pango word break algorithms).
     */
    public val endsWord: Boolean

    /**
     * Determines whether the character pointed by this iterator is part of a natural language word (as opposed to say
     * inside some whitespace). Word breaks are determined by Pango, and should be correct for nearly any language
     * (if not then the correct fix would be with the Pango word break algorithms).
     *
     * Note that if [startsWord] returns *true*, then this function returns *true* too since this iterator points to
     * the first character of the word.
     */
    public val insideWord: Boolean

    /**
     * Returns *true* if this iterator begins a paragraph, i.e. if [lineOffset] would return *0*. However this
     * property is potentially more efficient than [lineOffset] because it doesn’t have to compute the offset, it just
     * has to see whether it’s *0*.
     */
    public val startsLine: Boolean

    /**
     * Returns *true* if this iterator points to the start of the paragraph delimiter characters for a line
     * (delimiters will be either a newline, a carriage return, a carriage return followed by a newline, or a Unicode
     * paragraph separator character). Note that an iterator pointing to the \n of a \r\n pair will not be counted as
     * the end of a line, the line ends before the \r. The end iterator is considered to be at the end of a line, even
     * though there are no paragraph delimiter chars there.
     */
    public val endsLine: Boolean

    /**
     * Determines whether this iterator begins a sentence. Sentence boundaries are determined by Pango, and should be
     * correct for nearly any language (if not then the correct fix would be with the Pango text boundary algorithms).
     */
    public val startsSentence: Boolean

    /**
     * Determines whether this iterator ends a sentence. Sentence boundaries are determined by Pango, and should be
     * correct for nearly any language (if not then the correct fix would be with the Pango text boundary algorithms).
     */
    public val endsSentence: Boolean

    /**
     * Determines whether this iterator is inside a sentence (as opposed to in between two sentences, e.g. after a
     * period and before the first letter of the next sentence). Sentence boundaries are determined by Pango, and
     * should be correct for nearly any language (if not then the correct fix would be with the Pango text boundary
     * algorithms).
     */
    public val insideSentence: Boolean

    /**
     * When *true* the cursor can be placed at this [iterator][TextBufferIterator]. See
     * `gtk_text_iter_forward_cursor_position()`, or PangoLogAttr, or `pango_break()` for details on what a cursor
     * position is.
     */
    public val isCursorPosition: Boolean

    /** The number of characters in the line, including the paragraph delimiters. */
    public val charsInLine: Int

    /** The number of bytes in the line, including the paragraph delimiters. */
    public val bytesInLine: Int

    /**
     * Is *true* if this iterator is the end iterator, i.e. one past the last dereferenceable iterator in the buffer.
     * This is the most efficient way to check whether an iterator is the end iterator.
     */
    public val isEnd: Boolean

    /**
     * Is *true* if this iterator is the first iterator in the buffer, that is if this iterator has a character offset
     * of 0.
     */
    public val isStart: Boolean

    public companion object {
        /**
         * Obtains the text in the given range. A “slice” is an array of characters encoded in UTF-8 format, including the
         * Unicode **unknown** character **0xFFFC** for iterable non-character elements in the [buffer], such as images.
         * Because images are encoded in the slice, byte and character offsets in the returned array will correspond to
         * byte offsets in the text buffer. Note that **0xFFFC** can occur in normal text as well, so it is not a reliable
         * indicator that a pixbuf or widget is in the buffer.
         * @param start Iterator at the start of a range.
         * @param end Iterator at the end of a range.
         * @return Slice of text from the [buffer].
         */
        public fun fetchSlice(start: TextBufferIterator, end: TextBufferIterator): String

        /**
         * Obtains text in the given range. If the range contains non text elements such as images, the character, and byte
         * offsets in the returned string will not correspond to character, and byte offsets in the [buffer].
         * @param start Iterator at the start of a range.
         * @param end Iterator at the start of a range.
         * @return An array of characters from the [buffer].
         * @see fetchSlice
         */
        public fun fetchText(start: TextBufferIterator, end: TextBufferIterator): String

        /**
         * Like [fetchSlice], but invisible text is not included. Invisible text is usually invisible because a
         * `GtkTextTag` with the **invisible** attribute turned on has been applied to it.
         * @param start Iterator at the start of a range.
         * @param end Iterator at the end of a range.
         * @return Slice of text from the [buffer].
         */
        public fun fetchVisibleSlice(start: TextBufferIterator, end: TextBufferIterator): String

        /**
         * Like [fetchText], but invisible text is not included. Invisible text is usually invisible because a
         * `GtkTextTag` with the **invisible** attribute turned on has been applied to it.
         */
        public fun fetchVisibleText(start: TextBufferIterator, end: TextBufferIterator): String
    }

    /**
     * Whether the character at this [iterator][TextBufferIterator] is within an editable region of text. Non-editable
     * text is **locke** and can’t be changed by the user via [org.guiVista.gui.widget.textEditor.TextView]. This
     * function is simply a convenience wrapper around `gtk_text_iter_get_attributes()`. If no tags applied to this text
     * affect editability, then [defaultSetting] will be returned.
     *
     * You don’t want to use this property to decide whether text can be inserted at this iterator, because for
     * insertion you don’t want to know whether the character at the iterator is inside an editable range. You want to
     * know whether a new character inserted at this iterator would be inside an editable range. Use
     * [canInsert] to handle this case.
     * @param defaultSetting If *true* then text is editable by default.
     * @return A value of *true* if this [iterator][TextBufferIterator] is an editable range.
     */
    public fun editable(defaultSetting: Boolean): Boolean

    /**
     * Considering the default editability of the buffer, and tags that affect editability, determines whether text
     * inserted at this iterator would be editable. If text inserted at this iterator would be editable then the user
     * should be allowed to insert text at this iterator. The gtk_text_buffer_insert_interactive function uses this
     * function to decide whether insertions are allowed at a given position.
     * @param defaultEditability If *true* then text is editable by default.
     * @return Whether text inserted at this [iterator][TextBufferIterator] would be editable.
     */
    public fun canInsert(defaultEditability: Boolean): Boolean

    /**
     * Moves iter forward by one character offset. Note that images embedded in the [buffer] occupy 1 character slot,
     * so this function may actually move onto an image instead of a character, if you have images in your [buffer]. If
     * this iterator is the end iterator or one character before it, then this iterator will now point at the end
     * iterator, and this function returns *false* for convenience when writing loops.
     * @return A value of *true* if this iterator moved, and is dereferenceable.
     */
    public fun forwardChar(): Boolean

    /**
     * Moves backward by one character offset. Returns *true* if movement was possible; if this iterator was the first
     * in the [buffer] (character offset 0), then this function returns *false* for convenience when writing loops.
     * @return A value of *true* if movement was possible.
     */
    public fun backwardChar(): Boolean

    /**
     * Moves [count] characters if possible (if [count] would move past the start or end of the [buffer], moves to the
     * start or end of the buffer). The return value indicates whether the new position of this iterator is different
     * from its original position, and dereferenceable (the last iterator in the [buffer] is not dereferenceable). If
     * [count] is *0* then the function does nothing and returns *false*.
     *
     * Maps to [gtk_text_iter_forward_chars][https://developer.gnome.org/gtk3/stable/GtkTextIter.html#gtk-text-iter-forward-chars] function.
     * @param count Number of characters to move, which may be negative.
     * @return A value of *true* if this iterator moved, and is dereferenceable.
     */
    public fun forwardMultipleChars(count: Int): Boolean

    /**
     * Moves [count] characters backward, if possible (if [count] would move past the start or end of the [buffer],
     * moves to the start or end of the [buffer]). The return value indicates whether this iterator moved onto a
     * dereferenceable position; if this iterator didn’t move, or moved onto the end iterator, then *false* is
     * returned. If count is *0* then the function does nothing and returns *false*.
     *
     * Maps to [gtk_text_iter_backward_chars][https://developer.gnome.org/gtk3/stable/GtkTextIter.html#gtk-text-iter-backward-chars] function.
     * @param count Number of characters to move.
     * @return A value of *true* if this iterator moved, and is dereferenceable.
     */
    public fun backwardMultipleChars(count: Int): Boolean
}
