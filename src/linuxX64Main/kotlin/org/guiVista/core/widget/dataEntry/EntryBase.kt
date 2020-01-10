package org.guiVista.core.widget.dataEntry

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guiVista.core.widget.WidgetBase

/** Base interface for entry objects. */
interface EntryBase : WidgetBase {
    val gtkEntryPtr: CPointer<GtkEntry>?
        get() = gtkWidgetPtr?.reinterpret()
    /** String contents in the entry. Default value is *""* (an empty String). */
    var text: String
        set(value) = gtk_entry_set_text(gtkEntryPtr, value)
        get() = gtk_entry_get_text(gtkEntryPtr)?.toKString() ?: ""
    /** Placeholder text to use when [text] is empty. Default value is *""* (an empty String). */
    var placeholderText: String
        set(value) = gtk_entry_set_placeholder_text(gtkEntryPtr, value)
        get() = gtk_entry_get_placeholder_text(gtkEntryPtr)?.toKString() ?: ""
    /** Maximum number of characters for this entry. Zero if no maximum. Default value is *0*. */
    var maxLength: Int
        set(value) = gtk_entry_set_max_length(gtkEntryPtr, value)
        get() = gtk_entry_get_max_length(gtkEntryPtr)
    /**
     * The desired maximum width of the [Entry] in characters. If this property is set to *-1* the width will be
     * calculated automatically. Default value is *-1*.
     */
    var maxWidthChars: Int
        set(value) {
            if (value >= -1) gtk_entry_set_max_width_chars(gtkEntryPtr, value)
        }
        get() = gtk_entry_get_max_width_chars(gtkEntryPtr)
    /** Number of characters to leave space for in the [Entry]. Default value is *-1*. */
    var widthChars: Int
        set(value) {
            if (value >= -1) gtk_entry_set_width_chars(gtkEntryPtr, value)
        }
        get() = gtk_entry_get_width_chars(gtkEntryPtr)
}
