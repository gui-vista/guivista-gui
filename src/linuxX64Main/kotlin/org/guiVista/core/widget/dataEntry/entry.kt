package org.guiVista.core.widget.dataEntry

import gtk3.GtkEntry
import gtk3.GtkWidget
import gtk3.gtk_entry_new
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

/** A single line text entry field. */
class Entry(entryPtr: CPointer<GtkEntry>? = null) : EntryBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = entryPtr?.reinterpret() ?: gtk_entry_new()
}

fun entryWidget(entryPtr: CPointer<GtkEntry>? = null, init: Entry.() -> Unit): Entry {
    val entry = Entry(entryPtr)
    entry.init()
    return entry
}
