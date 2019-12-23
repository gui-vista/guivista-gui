@file:Suppress("EXPERIMENTAL_API_USAGE", "EXPERIMENTAL_UNSIGNED_LITERALS")

package org.guivista.core

import gtk3.GConnectFlags
import gtk3.g_signal_connect_data
import gtk3.gpointer
import kotlinx.cinterop.*

private val emptyData by lazy { EmptyData() }
private val emptyDataRef = StableRef.create(emptyData)

/** Gets the C Pointer for EmptyData, which is used for supplying "empty" data when connecting a signal to a slot. **/
@Suppress("unused")
fun fetchEmptyDataPointer(): COpaquePointer = emptyDataRef.asCPointer()

internal fun disposeEmptyDataRef() {
    emptyDataRef.dispose()
}

/**
 * Connects a signal (event) to a slot (event handler). Note that all callback parameters must be primitive types or
 * nullable C pointers.
 * @param obj The object to use for connecting a [signal][signal] to a [slot][slot].
 * @param signal The name of the signal to connect to.
 * @param slot The slot to use for handling the signal.
 * @param data User data to pass through to the [slot]. By default no user data is passed through.
 * @param connectFlags The flags to use.
 * @return The handler ID for the [slot].
 */
fun <F : CFunction<*>> connectGObjectSignal(
        obj: CPointer<*>?,
        signal: String,
        slot: CPointer<F>,
        data: gpointer? = null,
        connectFlags: GConnectFlags = 0u
): ULong = g_signal_connect_data(
        instance = obj,
        detailed_signal = signal,
        c_handler = slot.reinterpret(),
        data = data,
        destroy_data = null,
        connect_flags = connectFlags
)

private class EmptyData
