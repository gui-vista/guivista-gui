package org.guiVista.gui.text

import org.guiVista.core.ObjectBase

/** Stores attributed text for display in a [TextView][org.guiVista.gui.widget.textEditor.TextView]. */
public expect class TextBuffer : ObjectBase {
    /** Indicates whether the buffer has been modified. */
    public var modified: Boolean

    /** Whether the buffer has some text currently selected. Default value is *false*. */
    public val hasSelection: Boolean

    /** Obtains the number of lines in the buffer. This value is cached so this property is very fast. */
    public val lineCount: Int

    /**
     * Gets the number of characters in the buffer. Note that characters and bytes are **NOT** the same, you can’t e.g.
     * expect the contents of the buffer in string form to be this many bytes long. The character count is cached so
     * this function is very fast.
     */
    public val charCount: Int

    /**
     * Simply calls `gtk_text_buffer_insert` using the current cursor position as the insertion point.
     * @param text Text in UTF-8 format.
     */
    public infix fun insertAtCursor(text: String)

    /**
     * Calls `gtk_text_buffer_insert_interactive` at the cursor position. The [defaultEditable] parameter indicates
     * the editability of text that doesn't have a tag affecting editability applied to it. Typically the result of
     * [TextView.editable][org.guiVista.gui.widget.textEditor.TextView.editable] is appropriate here.
     * @param text Text in UTF-8 format.
     * @param defaultEditable Default editability of the buffer.
     * @return A value of *true* if the [text] was actually inserted.
     */
    public fun insertInteractiveAtCursor(text: String, defaultEditable: Boolean): Boolean

    /**
     * Deletes current contents of buffer, and inserts text instead.
     * @param text The UTF-8 text to insert.
     */
    public fun changeText(text: String)

    /**
     * Returns the text in the range [start, end. Excludes undisplayed text (text marked with tags that set the
     * invisibility attribute) if [includeHiddenChars] is *false*. Does not include characters representing embedded
     * images, so byte and character indexes into the returned string do not correspond to byte, and character indexes
     * into the buffer. Contrast with [fetchSlice].
     * @param start Start of a range.
     * @param end End of a range.
     * @param includeHiddenChars Whether to include invisible text.
     * @return An allocated UTF-8 string.
     */
    public fun fetchText(start: TextBufferIterator, end: TextBufferIterator, includeHiddenChars: Boolean): String

    /**
     * Deletes the range between the **insert**, and **selection_bound** marks. That is the currently selected text.
     * If interactive is *true* then the editability of the selection will be considered (users can’t delete
     * uneditable text).
     * @param interactive Whether the deletion is caused by user interaction.
     * @param defaultEditable Whether the buffer is editable by default.
     * @return Whether there was a non-empty selection to delete.
     */
    public fun deleteSelection(interactive: Boolean, defaultEditable: Boolean): Boolean

    /**
     * Called to indicate that the buffer operations between here, and a call to [endUserAction] are part of a single
     * user visible operation. The operations between [beginUserAction] and [endUserAction] can then be grouped when
     * creating an undo stack. The text buffer maintains a count of calls to [beginUserAction] that have not been
     * closed with a call to [endUserAction], and emits the **begin-user-action**, and **end-user-action** signals
     * only for the outermost pair of calls. This allows you to build user actions from other user actions.
     *
     * The **interactive** buffer mutation functions, such as `gtk_text_buffer_insert_interactive()`, automatically
     * call begin/end user action around the buffer operations they perform, so there's no need to add extra calls if
     * you user action consists solely of a single call to one of those functions.
     */
    public fun beginUserAction()

    /**
     * Should be paired with a call to [beginUserAction].
     * @see beginUserAction
     */
    public fun endUserAction()

    /**
     * Inserts [text] into the [iterator]. This function emits the **insert-text** signal; insertion actually occurs in
     * the default handler for the signal. The [iterator] is invalidated when insertion occurs (because the buffer
     * contents change), but the default signal handler revalidates it to point to the end of the inserted text.
     * @param iterator A position in the buffer.
     * @param text Text in UTF-8 format.
     */
    public fun insert(iterator: TextBufferIterator, text: String)

    /**
     * Like [insert] but the insertion will not occur if the [iterator] is at a non editable location in the buffer.
     * Usually you want to prevent insertions at ineditable locations if the insertion results from a user action (is
     * interactive). Note that [defaultEditable] indicates the editability of text that doesn't have a tag affecting
     * editability applied to it. Typically the result of `gtk_text_view_get_editable()` is appropriate here.
     * @param iterator A position in the buffer.
     * @param text Text in UTF-8 format.
     * @param defaultEditable Default editability of the buffer.
     * @return A value of *true* if the text was actually inserted.
     */
    public fun insertInteractive(iterator: TextBufferIterator, text: String, defaultEditable: Boolean): Boolean

    /**
     * Copies text, tags, and pixbufs between [start], and [end] (the order of [start] and [end] doesn’t matter), and
     * inserts the copy at the [iterator]. Used instead of simply getting/inserting text because it preserves images,
     * and tags. If [start] and [end] are in a different buffer from buffer then the two buffers **MUST** share the
     * same tag table.
     *
     * Implemented via emissions of the **insert_text**, and **apply_tag** signals, so expect those.
     * @param iterator A position in the buffer.
     * @param start Starting position in the buffer.
     * @param end Ending position in the buffer.
     */
    public fun insertRange(iterator: TextBufferIterator, start: TextBufferIterator, end: TextBufferIterator)

    /**
     * Same as [insertRange] but does nothing if the insertion point isn’t editable. The [defaultEditable] parameter
     * indicates whether the text is editable at the [iterator] if no tags enclosing [iterator] affect editability.
     * Typically the result of `gtk_text_view_get_editable()` is appropriate here.
     * @param iterator A position in the buffer.
     * @param start Starting position in the buffer.
     * @param end Ending position in the buffer.
     * @param defaultEditable Default editability of the buffer.
     * @return A value of *true* if an insertion occurred with the [iterator].
     */
    public fun insertRangeInteractive(
        iterator: TextBufferIterator,
        start: TextBufferIterator,
        end: TextBufferIterator,
        defaultEditable: Boolean
    ): Boolean

    /**
     * Inserts the text in [markup] at the position in the [iterator] . The [markup] will be inserted in its entirety,
     * and **MUST** be valid UTF-8. Emits the **insert-text** signal, possibly multiple times; insertion actually
     * occurs in the default handler for the signal. The [iterator] will point to the end of the inserted text on
     * return.
     * @param iterator A location to insert the markup.
     * @param markup A UTF-8 string containing Pango markup.
     */
    public fun insertMarkup(iterator: TextBufferIterator, markup: String)

    /**
     * Deletes text between [start], and [end]. The order of [start], and [end] is not actually relevant; this
     * function will reorder them. The **delete-range** signal is emitted, and the default handler of that signal
     * deletes the text. Because the buffer is modified, all outstanding iterators become invalid after calling this
     * function. However the [start] and [end] will be re-initialized to point to the location where text was deleted.
     * @param start Starting position in the buffer.
     * @param end Ending position in the buffer.
     */
    public fun delete(start: TextBufferIterator, end: TextBufferIterator)

    /**
     * Deletes all editable text in the given range. Calls [delete] for each editable sub-range of [start, end]. The
     * [start], and [end] parameters are revalidated to point to the location of the last deleted range, or left
     * untouched if no text was deleted.
     * @param start Start of range to delete.
     * @param end End of range to delete.
     * @param defaultEditable Whether the buffer is editable by default.
     * @return A value of *true* if some text was actually deleted.
     */
    public fun deleteInteractive(start: TextBufferIterator, end: TextBufferIterator, defaultEditable: Boolean): Boolean

    /**
     * Performs the appropriate action as if the user hit the **delete** key with the cursor at the position specified
     * by the [iterator]. In the normal case a single character will be deleted, but when combining accents are
     * involved, more than one character can be deleted, and when pre-composed character and accent combinations are
     * involved, less than one character will be deleted.
     *
     * Because the buffer is modified, all outstanding iterators become invalid after calling this function. However
     * the [iterator] will be re-initialized to point to the location where text was deleted.
     * @param iterator A position in the buffer.
     * @param interactive Whether the deletion is caused by user interaction.
     * @param defaultEditable Whether the buffer is editable by default.
     * @return A value of *true* if the buffer was modified.
     */
    public fun backspace(iterator: TextBufferIterator, interactive: Boolean, defaultEditable: Boolean): Boolean

    /**
     * Returns the text in the range [start, end]. Excludes undisplayed text (text marked with tags that set the
     * invisibility attribute) if [includeHiddenChars] is *false*. The returned string includes a **0xFFFC** character
     * whenever the buffer contains embedded images, so byte and character indexes into the returned string do
     * correspond to byte and character indexes into the buffer. Contrast with `gtk_text_buffer_get_text()`. Note that
     * **0xFFFC** can occur in normal text as well, so it is not a reliable indicator that a pixbuf, or widget is in
     * the buffer.
     * @param start Start of a range.
     * @param end End of a range.
     * @param includeHiddenChars Whether to include invisible text.
     * @return A UTF-8 string.
     */
    public fun fetchSlice(start: TextBufferIterator, end: TextBufferIterator, includeHiddenChars: Boolean): String

    /**
     * This function moves the **insert** and **selection_bound** marks simultaneously. If you move them to the same
     * place in two steps with `gtk_text_buffer_move_mark()`, you will temporarily select a region in between their
     * old and new locations, which can be pretty inefficient since the temporarily-selected region will force stuff
     * to be recalculated. This function moves them as a unit, which can be optimized.
     * @param where Where to put the cursor.
     */
    public fun placeCursor(where: TextBufferIterator)

    /**
     * This function moves the **insert** and “selection_bound” marks simultaneously. If you move them in two steps
     * with `gtk_text_buffer_move_mark()`, you will temporarily select a region in between their old and new locations,
     * which can be pretty inefficient since the temporarily-selected region will force stuff to be recalculated. This
     * function moves them as a unit, which can be optimized.
     * @param insert Where to put the **insert** mark.
     * @param bound Where to put the **selection_bound** mark.
     */
    public fun selectRange(insert: TextBufferIterator, bound: TextBufferIterator)

    /**
     * Obtains an [iterator] pointing to [charOffset] within the given line. Note characters, not bytes; UTF-8 may
     * encode one character as multiple bytes. Since GTK 3.20, if [lineNum] is greater than the number of lines in the
     * buffer then the end iterator is returned. Also if [charOffset] is off the end of the line, the iterator at the
     * end of the line is returned.
     * @param iterator The iterator to initialize.
     * @param lineNum The line number counting from 0.
     * @param charOffset Character offset from the start of line.
     */
    public fun fetchIteratorAtLineOffset(iterator: TextBufferIterator, lineNum: Int, charOffset: Int)

    /**
     * Initializes the [iterator] to a position [charOffset] chars from the start of the entire buffer. If [charOffset]
     * is *-1* or greater than the number of characters in the buffer then the [iterator] is initialized to the end
     * iterator, the iterator one past the last valid character in the buffer.
     * @param iterator The iterator to initialize.
     * @param charOffset The character offset from start of the buffer, counting from *0*, or *-1*.
     */
    public fun fetchIteratorAtOffset(iterator: TextBufferIterator, charOffset: Int)

    /**
     * Initializes the [iterator] to the start of the given line. If [lineNum] is greater than the number of lines in
     * the buffer then the end iterator is returned.
     * @param iterator The iterator to initialize.
     * @param lineNum The line number counting from *0*.
     */
    public fun fetchIteratorAtLine(iterator: TextBufferIterator, lineNum: Int)

    /**
     * Obtains an iterator pointing to [byteIndex] within the given line. Note that [byteIndex] **MUST** be the start
     * of a UTF-8 character; are bytes **NOT** characters; UTF-8 may encode one character as multiple bytes. Since GTK
     * 3.20, if [lineNum] is greater than the number of lines in the buffer then the end iterator is returned. Also if
     * [byteIndex] is off the end of the line then the iterator at the end of the line is returned.
     */
    public fun fetchIteratorAtLineIndex(iterator: TextBufferIterator, lineNum: Int, byteIndex: Int)

    /**
     * Initialized [iterator] with the first position in the text buffer. This is the same as using
     * [fetchIteratorAtOffset] to get the [iterator] at character offset *0*.
     * @param iterator The iterator to initialize.
     */
    public fun fetchStartIterator(iterator: TextBufferIterator)

    /**
     * Initializes the [iterator] with the end iterator. One past the last valid character in the text buffer. If
     * dereferenced with gtk_text_iter_get_char() then the end iterator has a character value of 0. The entire buffer
     * lies in the range from the first position in the buffer (call [fetchStartIterator] to get character position
     * *0*) to the end iterator.
     * @param iterator The iterator to initialize.
     */
    public fun fetchEndIterator(iterator: TextBufferIterator)

    /**
     * Retrieves the first and last iterators in the buffer, i.e. the entire buffer lies within the range [start, end].
     * @param start The iterator to initialize with first position in the buffer.
     * @param end The iterator to initialize with the end iterator.
     */
    public fun fetchBounds(start: TextBufferIterator, end: TextBufferIterator)

    /**
     * Returns *true* if some text is selected; places the bounds of the selection in [start], and [end] (if the
     * selection has length *0*, then start and end are filled in with the same value). The [start] and [end]
     * parameters will be in ascending order. If [start] and [end] are *null* then they are not filled in, but the
     * return value still indicates whether text is selected.
     * @param start The iterator to initialize with selection start.
     * @param end The iterator to initialize with selection end.
     * @return A value of *true* if the selection has a non zero length.
     */
    public fun fetchSelectionBounds(start: TextBufferIterator?, end: TextBufferIterator?): Boolean
}
