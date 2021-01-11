package org.guiVista.gui.text

import glib2.g_object_unref
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import org.guiVista.core.ObjectBase
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

private const val DELETED_TEXT_SIGNAL = "deleted-text"
private const val INSERTED_TEXT_SIGNAL = "inserted-text"

public actual class EntryBuffer private constructor(ptr: CPointer<GtkEntryBuffer>?) : ObjectBase {
    public val gtkEntryBufferPtr: CPointer<GtkEntryBuffer>? = ptr

    public actual companion object {
        public fun fromPointer(ptr: CPointer<GtkEntryBuffer>?): EntryBuffer = EntryBuffer(ptr)

        public actual fun create(initialText: String): EntryBuffer {
            val totalChars = if (initialText.isNotEmpty()) initialText.length else -1
            val txt = if (initialText.isNotEmpty()) initialText else null
            return EntryBuffer(gtk_entry_buffer_new(txt, totalChars))
        }
    }

    public actual val length: UInt
        get() = gtk_entry_buffer_get_length(gtkEntryBufferPtr)

    public actual var maxLength: Int
        get() = gtk_entry_buffer_get_max_length(gtkEntryBufferPtr)
        set(value) = gtk_entry_buffer_set_max_length(gtkEntryBufferPtr, value)

    public actual var text: String
        get() = gtk_entry_buffer_get_text(gtkEntryBufferPtr)?.toKString() ?: ""
        set(value) = gtk_entry_buffer_set_text(buffer = gtkEntryBufferPtr, chars = value, n_chars = -1)

    public actual val bytes: ULong
        get() = gtk_entry_buffer_get_bytes(gtkEntryBufferPtr)

    public actual fun insertText(pos: UInt, text: String): UInt =
        gtk_entry_buffer_insert_text(buffer = gtkEntryBufferPtr, chars = text, position = pos, n_chars = -1)

    public actual fun deleteText(pos: UInt, totalChars: Int): UInt =
        gtk_entry_buffer_delete_text(buffer = gtkEntryBufferPtr, position = pos, n_chars = totalChars)

    override fun close() {
        g_object_unref(gtkEntryBufferPtr)
    }

    /**
     * Connects the *deleted-text* signal to a [slot] on a [EntryBuffer]. This signal occurs after text is deleted from
     * the buffer.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectDeletedTextSignal(slot: CPointer<DeletedTextSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkEntryBufferPtr, signal = DELETED_TEXT_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *inserted-text* signal to a [slot] on a [EntryBuffer]. This signal occurs after text is inserted
     * into the buffer.
     * the buffer.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectInsertedTextSignal(slot: CPointer<InsertedTextSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkEntryBufferPtr, signal = INSERTED_TEXT_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkEntryBufferPtr, handlerId)
    }
}

/**
 * The event handler for the *deleted-text* signal. Arguments:
 * 1. buffer: CPointer<GtkEntryBuffer>,
 * 2. pos: UInt,
 * 3. totalChars: Int,
 * 4. userData: gpointer
 */
public typealias DeletedTextSlot = CFunction<(
    buffer: CPointer<GtkEntryBuffer>,
    pos: UInt,
    totalChars: Int,
    userData: gpointer
) -> Unit>

/**
 * The event handler for the *inserted-text* signal. Arguments:
 * 1. buffer: CPointer<GtkEntryBuffer>
 * 2. pos: UInt
 * 3. text: CPointer<ByteVar>
 * 4. totalChars: Int
 * 5. userData: gpointer
 */
public typealias InsertedTextSlot = CFunction<(
    buffer: CPointer<GtkEntryBuffer>,
    pos: UInt,
    text: CPointer<ByteVar>,
    totalChars: Int,
    userData: gpointer
) -> Unit>

public fun entryBuffer(
    ptr: CPointer<GtkEntryBuffer>? = null,
    initialText: String = "",
    init: EntryBuffer.() -> Unit = {}
): EntryBuffer {
    val result = if (ptr != null) EntryBuffer.fromPointer(ptr) else EntryBuffer.create(initialText)
    result.init()
    return result
}
