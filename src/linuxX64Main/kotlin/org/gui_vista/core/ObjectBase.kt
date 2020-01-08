package org.gui_vista.core

/** Root foundation for interfaces, and classes based on GObject. */
interface ObjectBase : Closable {
    /**
     * Disconnects a signal (event) from a slot (event handler) on a object.
     * @param handlerId The handler ID to use.
     */
    fun disconnectSignal(handlerId: ULong) {}
}
