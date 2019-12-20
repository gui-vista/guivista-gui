package org.guivista.core.layout

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guivista.core.widget.Widget

/** A single line text entry field. */
class Entry : Widget {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = gtk_entry_new()
    val gtkEntryPtr: CPointer<GtkEntry>?
        get() = gtkWidgetPtr?.reinterpret()
    /** String contents in the entry. */
    var text: String
        set(value) = gtk_entry_set_text(gtkEntryPtr, value)
        get() = gtk_entry_get_text(gtkEntryPtr)?.toKString() ?: ""
    /** Placeholder text to use when [text] is empty. */
    var placeholderText: String
        set(value) = gtk_entry_set_placeholder_text(gtkEntryPtr, value)
        get() = gtk_entry_get_placeholder_text(gtkEntryPtr)?.toKString() ?: ""
    /** Maximum number of characters for this entry. Zero if no maximum. */
    var maxLength: Int
        set(value) = gtk_entry_set_max_length(gtkEntryPtr, value)
        get() = gtk_entry_get_max_length(gtkEntryPtr)
    /**
     * The desired maximum width of the entry in characters. If this property is set to *-1* the width will be
     * calculated automatically.
     */
    var maxWidthChars: Int
        set(value) = gtk_entry_set_max_width_chars(gtkEntryPtr, value)
        get() = gtk_entry_get_max_width_chars(gtkEntryPtr)
    /** Number of characters to leave space for in the entry. */
    var widthChars: Int
        set(value) = gtk_entry_set_width_chars(gtkEntryPtr, value)
        get() = gtk_entry_get_width_chars(gtkEntryPtr)
}

fun entryWidget(init: Entry.() -> Unit): Entry {
    val entry = Entry()
    entry.init()
    return entry
}
