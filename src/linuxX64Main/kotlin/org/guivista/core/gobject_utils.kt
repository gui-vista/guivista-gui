@file:Suppress("EXPERIMENTAL_API_USAGE", "EXPERIMENTAL_UNSIGNED_LITERALS")

package org.guivista.core

import gtk3.GConnectFlags
import gtk3.g_signal_connect_data
import gtk3.gpointer
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret

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
internal fun <F : CFunction<*>> connectGtkSignal(
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