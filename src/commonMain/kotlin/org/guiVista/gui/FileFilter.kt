package org.guiVista.gui

/** A filter for selecting a file subset. */
public expect class FileFilter {
    /** Human readable name of the filter. */
    public var name: String

    /**
     * Adds a rule allowing a given mime type to filter.
     * @param mimeType Name of the MIME type.
     */
    public infix fun addMimeType(mimeType: String)

    /**
     * Adds a rule allowing a shell style glob to a filter.
     * @param pattern A shell style glob.
     */
    public infix fun addPattern(pattern: String)

    /** Adds a rule allowing image files in the formats supported by `GdkPixbuf`. */
    public fun addPixBufFormats()
}