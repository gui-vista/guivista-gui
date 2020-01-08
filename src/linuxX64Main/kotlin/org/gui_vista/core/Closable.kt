package org.gui_vista.core

/** Allows a resource to be closed. */
interface Closable {
    /** Closes the resource. May also close other child resources. */
    fun close()
}
