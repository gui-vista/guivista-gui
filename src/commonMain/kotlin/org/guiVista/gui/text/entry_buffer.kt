package org.guiVista.gui.text

import org.guiVista.core.ObjectBase
import org.guiVista.gui.widget.dataEntry.Entry

/** Text buffer for [Entry]. */
public expect class EntryBuffer : ObjectBase {
    /** The length (in characters) of the text in buffer. Default value is *0*. */
    public val length: UInt

    /** The maximum length (in characters) of the text in the buffer. Default value is *0*. */
    public var maxLength: Int

    /** The contents of the buffer. Default value is *""*. */
    public var text: String

    /**
     * The length in bytes of the buffer
     * @see length
     */
    public val bytes: ULong

    public companion object {
        /**
         * Create a new [EntryBuffer]. Optionally, specify initial text to set in the buffer.
         * @param initialText The initial buffer text, or *null*.
         * @return A new [EntryBuffer].
         */
        public fun create(initialText: String = ""): EntryBuffer
    }

    /**
     * Inserts [text] into the contents of the buffer, at a specified [position][pos].
     * @param pos The position at which to insert text.
     * @param text The text to insert into the [buffer][EntryBuffer].
     * @return The number of characters actually inserted.
     */
    public fun insertText(pos: UInt, text: String): UInt

    /**
     * Deletes a sequence of characters from the [buffer][EntryBuffer]. A number of [total characters][totalChars] are
     * deleted starting at the specified [position][pos]. If [totalChars] is negative then all characters until the
     * end of the text are deleted.
     *
     * If [pos] or [totalChars] are out of bounds then they are coerced to sane values.
     * @param pos The position at which to delete text.
     * @param totalChars Number of characters to delete.
     * @return The number of characters deleted.
     */
    public fun deleteText(pos: UInt, totalChars: Int): UInt
}
