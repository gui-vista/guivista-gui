package org.guivista.core

/** Root foundation for interfaces, and classes based on GObject. */
interface ObjectBase {
    /**
     * Disconnects a signal (event) from a slot (event handler) on a object.
     * @param handlerId The handler ID to use.
     */
    fun disconnectSignal(handlerId: ULong) {}
}
