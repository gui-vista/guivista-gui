package org.guiVista.gui

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString

public actual class FileFilter(fileFilterPtr: CPointer<GtkFileFilter>? = null) {
    public val gtkFileFilterPtr: CPointer<GtkFileFilter>? = fileFilterPtr ?: gtk_file_filter_new()
    public actual var name: String
        get() = gtk_file_filter_get_name(gtkFileFilterPtr)?.toKString() ?: ""
        set(value) = gtk_file_filter_set_name(gtkFileFilterPtr, value)

    public actual infix fun addMimeType(mimeType: String) {
        gtk_file_filter_add_mime_type(gtkFileFilterPtr, mimeType)
    }

    public actual infix fun addPattern(pattern: String) {
        gtk_file_filter_add_pattern(gtkFileFilterPtr, pattern)
    }

    public actual fun addPixBufFormats() {
        gtk_file_filter_add_pixbuf_formats(gtkFileFilterPtr)
    }
}

public fun fileFilter(ptr: CPointer<GtkFileFilter>? = null, init: FileFilter.() -> Unit): FileFilter {
    val filter = FileFilter(ptr)
    filter.init()
    return filter
}
