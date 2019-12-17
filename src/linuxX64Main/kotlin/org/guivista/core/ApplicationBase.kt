package org.guivista.core

import gtk3.GApplication
import gtk3.g_application_run
import gtk3.gpointer
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer

interface ApplicationBase {
    val gAppPtr: CPointer<GApplication>

    /**
     * Connects the *activate* signal to a [slot] on a application. The *activate* signal is used for initialising the
     * application window, and is emitted on the primary instance when an activation occurs.
     * @param slot The event handler for the signal. Arguments:
     * 1. app: CPointer<GApplication>
     * 2. userData: gpointer
     * @param userData User data to pass through to the [slot].
     */
    fun connectActivateSignal(slot: CPointer<CFunction<(app: CPointer<GApplication>, userData: gpointer) -> Unit>>,
                              userData: gpointer): ULong =
        connectGtkSignal(obj = gAppPtr, signal = "activate", slot = slot, data = userData)

    /**
     * Connects the *startup* signal to a [slot] on a application. The *startup* signal is emitted on the primary
     * instance immediately after registration.
     * @param slot The event handler for the signal. Arguments:
     * 1. app: CPointer<GApplication>
     * 2. userData: gpointer
     * @param userData User data to pass through to the [slot].
     */
    fun connectStartupSignal(slot: CPointer<CFunction<(app: CPointer<GApplication>, userData: gpointer) -> Unit>>,
                             userData: gpointer): ULong =
        connectGtkSignal(obj = gAppPtr, signal = "startup", slot = slot, data = userData)

    /**
     * Connects the *shutdown* signal to a [slot] on a application. The *shutdown* signal is emitted only on the
     * registered primary instance immediately after the main loop terminates.
     * @param slot The event handler for the signal. Arguments:
     * 1. app: CPointer<GApplication>
     * 2. userData: gpointer
     * @param userData User data to pass through to the [slot].
     */
    fun connectShutdownSignal(slot: CPointer<CFunction<(app: CPointer<GApplication>, userData: gpointer) -> Unit>>,
                              userData: gpointer): ULong =
        connectGtkSignal(obj = gAppPtr, signal = "shutdown", slot = slot, data = userData)

    /**
     * Runs the application.
     * @return A status code is returned when the application exits. Any code not equal to **0** means a error has
     * occurred.
     */
    fun run(): Int = g_application_run(gAppPtr, 0, null)
}
