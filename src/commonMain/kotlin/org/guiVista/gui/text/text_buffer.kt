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
}
