package org.guivista.core.widget.data_entry

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guivista.core.widget.Widget

/** A single line text entry field. */
class Entry : EntryBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = gtk_entry_new()
}

fun entryWidget(init: Entry.() -> Unit): Entry {
    val entry = Entry()
    entry.init()
    return entry
}
