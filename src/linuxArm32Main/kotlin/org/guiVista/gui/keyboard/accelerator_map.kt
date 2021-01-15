package org.guiVista.gui.keyboard

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import org.guiVista.core.ObjectBase
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

public actual object AcceleratorMap : ObjectBase {
    public actual fun load(fileName: String) {
        gtk_accel_map_load(fileName)
    }

    public actual fun save(fileName: String) {
        gtk_accel_map_save(fileName)
    }

    override fun close() {
        // Do nothing since AcceleratorMap is a Singleton.
    }

    /**
     * Registers a new accelerator with the global accelerator map. This function should only be called once per
     * [accelPath] with the canonical [accelKey] and [accelMods] for this path. To change the accelerator during
     * runtime programatically, use [changeEntry]. Set [accelKey], and [accelMods] to *0* to request a removal of the
     * accelerator.
     *
     * Note that [accelPath] will be stored in a `GQuark`. Therefore if you pass a static string, you can save some
     * memory by interning it first with `g_intern_static_string()`.
     * @param accelPath A valid accelerator path.
     * @param accelKey The accelerator key.
     * @param accelMods The accelerator modifiers.
     */
    public fun addEntry(accelPath: String, accelKey: UInt, accelMods: GdkModifierType) {
        gtk_accel_map_add_entry(accel_path = accelPath, accel_key = accelKey, accel_mods = accelMods)
    }

    public actual fun lookupEntry(accelPath: String): Boolean = gtk_accel_map_lookup_entry(accelPath, null) == TRUE

    /**
     * Changes the [accelKey] and [accelMods] currently associated with [accelPath]. Due to conflicts with other
     * accelerators a change may not always be possible. The [replace] parameter indicates whether other accelerators
     * may be deleted to resolve such conflicts. A change will only occur if all conflicts could be resolved (which
     * might not be the case if conflicting accelerators are locked). Successful changes are indicated by a *true*
     * return value.
     *
     * Note that [accelPath] will be stored in a `GQuark`. Therefore if you pass a static string you can save some
     * memory by interning it first with `g_intern_static_string()`.
     * @param accelPath A valid accelerator path.
     * @param accelKey The new accelerator key.
     * @param accelMods The new accelerator modifiers.
     * @param replace If *true* then other accelerators *may* be deleted upon conflicts.
     * @return A value of *true* if the accelerator could be changed.
     */
    public fun changeEntry(accelPath: String, accelKey: UInt, accelMods: GdkModifierType, replace: Boolean): Boolean =
        gtk_accel_map_change_entry(accel_path = accelPath, accel_key = accelKey, accel_mods = accelMods,
            replace = if (replace) TRUE else FALSE) == TRUE

    public actual fun loadFileDescriptor(fd: Int) {
        gtk_accel_map_load_fd(fd)
    }

    public actual fun saveFileDescriptor(fd: Int) {
        gtk_accel_map_save_fd(fd)
    }

    public actual fun lockPath(accelPath: String) {
        gtk_accel_map_lock_path(accelPath)
    }

    public actual fun unlockPath(accelPath: String) {
        gtk_accel_map_unlock_path(accelPath)
    }

    /**
     * Connects the *changed* event to a [handler] on an [AcceleratorMap]. The *changed* event is used to of a change in
     * the global accelerator map. The path is also used as the detail for the event, so it is possible to connect to
     * `changed::accel_path`.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectChangedEvent(handler: CPointer<ChangedHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtk_accel_map_get(), signal = AcceleratorMapEvent.changed, slot = handler, data = userData)
            .toULong()

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtk_accel_map_get(), handlerId.toUInt())
    }
}

/**
 * The event handler for the *changed* event. Arguments:
 * 1. obj: CPointer<GtkAccelMap>
 * 2. accelPath: CPointer<ByteVar>
 * 3. modifier: GdkModifierType
 * 4. accelClosure: CPointer<GClosure>
 * 5. userData: gpointer
 */
public typealias ChangedHandler = CFunction<(
    obj: CPointer<GtkAccelMap>,
    accelPath: CPointer<ByteVar>,
    accelKey: UInt,
    accelMods: GdkModifierType,
    userData: gpointer
) -> Unit>
