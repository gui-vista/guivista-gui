package org.guiVista.gui.text

import glib2.*
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import org.guiVista.core.ObjectBase
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

private const val BEGIN_USER_ACTION_SIGNAL = "begin-user-action"
private const val END_USER_ACTION_SIGNAL = "end-user-action"
private const val CHANGED_SIGNAL = "changed"
private const val DELETE_RANGE_SIGNAL = "delete-range"
private const val INSERT_TEXT_SIGNAL = "insert-text"
private const val MODIFIED_CHANGED_SIGNAL = "modified-changed"

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

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkTextBufferPtr, handlerId.toUInt())
    }

    /**
     * Connects the *begin-user-action* signal to a [slot] on a [TextBuffer]. This signal is triggered when the
     * beginning of a single user-visible operation has occurred on a [TextBuffer].
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     * @see beginUserAction
     * @see insertInteractive
     * @see insertRangeInteractive
     * @see deleteInteractive
     * @see backspace
     * @see deleteSelection
     */
    public fun connectBeginUserActionSignal(slot: CPointer<BEGIN_USER_ACTION_SLOT>, userData: gpointer): ULong =
        connectGSignal(obj = gtkTextBufferPtr, signal = BEGIN_USER_ACTION_SIGNAL, slot = slot, data = userData)
            .toULong()

    /**
     * Connects the *end-user-action* signal to a [slot] on a [TextBuffer]. This signal is triggered when the
     * end of a single user-visible operation has occurred on a [TextBuffer].
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     * @see endUserAction
     * @see insertInteractive
     * @see insertRangeInteractive
     * @see deleteInteractive
     * @see backspace
     * @see deleteSelection
     * @see backspace
     */
    public fun connectEndUserActionSignal(slot: CPointer<END_USER_ACTION_SLOT>, userData: gpointer): ULong =
        connectGSignal(obj = gtkTextBufferPtr, signal = END_USER_ACTION_SIGNAL, slot = slot, data = userData).toULong()

    /**
     * Connects the *changed* signal to a [slot] on a [TextBuffer]. This signal is triggered when the content of a
     * [TextBuffer] has changed.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectChangedSignal(slot: CPointer<CHANGED_SLOT>, userData: gpointer): ULong =
        connectGSignal(obj = gtkTextBufferPtr, signal = CHANGED_SIGNAL, slot = slot, data = userData).toULong()

    /**
     * Connects the *modified-changed* signal to a [slot] on a [TextBuffer]. This signal is triggered when the modified
     * bit of a [TextBuffer] flips.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     * @see modified
     */
    public fun connectModifiedChangedSignal(slot: CPointer<MODIFIED_CHANGED_SLOT>, userData: gpointer): ULong =
        connectGSignal(obj = gtkTextBufferPtr, signal = MODIFIED_CHANGED_SIGNAL, slot = slot, data = userData).toULong()

    /**
     * Connects the *delete-range* signal to a [slot] on a [TextBuffer]. This signal is triggered when a ranged is
     * deleted from a [TextBuffer]. Note that if your handler runs before the default handler it must **NOT**
     * invalidate the start and end iterators (or has to revalidate them). The default signal handler revalidates the
     * start, and end iterators to both point to the location where text was deleted. Handlers which run after the
     * default handler (see `g_signal_connect_after()`) do not have access to the deleted text.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     * @see delete
     */
    public fun connectDeleteRangeSignal(slot: CPointer<DELETE_RANGE_SLOT>, userData: gpointer): ULong =
        connectGSignal(obj = gtkTextBufferPtr, signal = DELETE_RANGE_SIGNAL, slot = slot, data = userData).toULong()

    /**
     * Connects the *insert-text* signal to a [slot] on a [TextBuffer]. This signal is triggered when inserting text in
     * a [TextBuffer]. Insertion actually occurs in the default handler. Note that if your handler runs before the
     * default handler it must **NOT** invalidate the location iterator (or has to revalidate it). The default signal
     * handler revalidates it to point to the end of the inserted text.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     * @see insert
     * @see insertRange
     */
    public fun connectInsertTextSignal(slot: CPointer<INSERT_TEXT_SLOT>, userData: gpointer): ULong =
        connectGSignal(obj = gtkTextBufferPtr, signal = INSERT_TEXT_SIGNAL, slot = slot, data = userData).toULong()
}

public fun textBuffer(textBufferPtr: CPointer<GtkTextBuffer>? = null, init: TextBuffer.() -> Unit): TextBuffer {
    val textBuffer = TextBuffer(textBufferPtr)
    textBuffer.init()
    return textBuffer
}

/**
 * The event handler for the *begin-user-action* signal. Arguments:
 * 1. textBuffer: CPointer<GtkTextBuffer>
 * 2. userData: gpointer
 */
public typealias BEGIN_USER_ACTION_SLOT = CFunction<(textBuffer: CPointer<GtkTextBuffer>, userData: gpointer) -> Unit>

/**
 * The event handler for the *end-user-action* signal. Arguments:
 * 1. textBuffer: CPointer<GtkTextBuffer>
 * 2. userData: gpointer
 */
public typealias END_USER_ACTION_SLOT = CFunction<(textBuffer: CPointer<GtkTextBuffer>, userData: gpointer) -> Unit>

/**
 * The event handler for the *changed* signal. Arguments:
 * 1. textBuffer: CPointer<GtkTextBuffer>
 * 2. userData: gpointer
 */
public typealias CHANGED_SLOT = CFunction<(textBuffer: CPointer<GtkTextBuffer>, userData: gpointer) -> Unit>

/**
 * The event handler for the *modified-changed* signal. Arguments:
 * 1. textBuffer: CPointer<GtkTextBuffer>
 * 2. userData: gpointer
 */
public typealias MODIFIED_CHANGED_SLOT = CFunction<(textBuffer: CPointer<GtkTextBuffer>, userData: gpointer) -> Unit>

/**
 * The event handler for the *delete-range* signal. Arguments:
 * 1. textBuffer: CPointer<GtkTextBuffer>
 * 2. start: CPointer<GtkTextIter>
 * 3. end: CPointer<GtkTextIter>
 * 4. userData: gpointer
 */
public typealias DELETE_RANGE_SLOT = CFunction<(
    textBuffer: CPointer<GtkTextBuffer>,
    start: CPointer<GtkTextIter>,
    end: CPointer<GtkTextIter>,
    userData: gpointer
) -> Unit>

/**
 * The event handler for the *insert-text* signal. Arguments:
 * 1. textBuffer: CPointer<GtkTextBuffer>
 * 2. location: CPointer<GtkTextIter>
 * 2. text: gchar
 * 2. len: Int
 * 2. userData: gpointer
 */
public typealias INSERT_TEXT_SLOT = CFunction<(
    textBuffer: CPointer<GtkTextBuffer>,
    location: CPointer<GtkTextIter>,
    text: gchar,
    len: Int,
    userData: gpointer
) -> Unit>
