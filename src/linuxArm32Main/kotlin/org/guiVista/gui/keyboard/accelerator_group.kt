package org.guiVista.gui.keyboard

import glib2.*
import gtk3.*
import kotlinx.cinterop.*
import org.guiVista.core.ObjectBase
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

private const val ACCEL_ACTIVATE_SIGNAL = "accel-activate"
private const val ACCEL_CHANGED_SIGNAL = "accel-changed"

public actual class AcceleratorGroup(accelGroupPtr: CPointer<GtkAccelGroup>? = null) : ObjectBase {
    public val gtkAccelGroupPtr: CPointer<GtkAccelGroup>? = accelGroupPtr ?: gtk_accel_group_new()

    public actual val isLocked: Boolean
        get() = gtk_accel_group_get_is_locked(gtkAccelGroupPtr) == TRUE

    /** The modifier mask for this accel group. */
    public val modifierMask: GdkModifierType
        get() = gtk_accel_group_get_modifier_mask(gtkAccelGroupPtr)

    override fun close() {
        g_object_unref(gtkAccelGroupPtr)
    }

    public actual fun lock() {
        gtk_accel_group_lock(gtkAccelGroupPtr)
    }

    public actual fun unlock() {
        gtk_accel_group_unlock(gtkAccelGroupPtr)
    }

    /**
     * Adds a keyboard shortcut (accelerator).
     * @param accelMod The accelerator modifier in UInt form (eg Ctrl).
     * @param accelKey The accelerator key (eg *s*).
     * @param accelFlags The accelerator flags.
     * @param eventHandler Event handler to use when the accelerator is invoked by the user.
     */
    public fun addShortcut(
        accelMod: UInt,
        accelKey: Char,
        accelFlags: UInt = 0u,
        eventHandler: CPointer<CFunction<(COpaquePointer) -> Unit>>
    ) {
        gtk_accel_group_connect(
            accel_group = gtkAccelGroupPtr,
            accel_mods = accelMod,
            accel_key = accelKey.toInt().toUInt(),
            accel_flags = accelFlags,
            closure = g_cclosure_new(callback_func = eventHandler.reinterpret(), user_data = null, destroy_data = null)
        )
    }

    /**
     * Removes a keyboard shortcut (accelerator).
     * @param accelMod The accelerator modifier in UInt form (eg Ctrl)
     * @param accelKey The accelerator key (eg *s*).
     */
    public fun removeShortcut(accelMod: UInt, accelKey: Char) {
        gtk_accel_group_disconnect_key(accel_group = gtkAccelGroupPtr, accel_mods = accelMod,
            accel_key = accelKey.toInt().toUInt())
    }

    /**
     * Connects the *accel-activate* signal to a [slot] on an [AcceleratorGroup]. The *accel-activate* signal an
     * implementation detail, and isn't meant to be used in applications.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectAccelActivateSignal(slot: CPointer<AccelActivateSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkAccelGroupPtr, signal = ACCEL_ACTIVATE_SIGNAL, slot = slot, data = userData)
            .toULong()

    /**
     * Connects the *accel-changed* signal to a [slot] on an [AcceleratorGroup]. The *accel-changed* signal is used
     * when an entry is added to or removed from the accel group. Widgets like `GtkAccelLabel` which display an
     * associated accelerator should connect to this signal, and rebuild their visual representation if the accelerator
     * closure is theirs.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectAccelChangedSignal(slot: CPointer<AccelChangedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkAccelGroupPtr, signal = ACCEL_CHANGED_SIGNAL, slot = slot, data = userData)
            .toULong()

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkAccelGroupPtr, handlerId.toUInt())
    }
}

/**
 * The event handler for the *accel-activate* signal. Arguments:
 * 1. accelGroup: CPointer<GtkAccelGroup>
 * 2. acceleratable: CPointer<GObject>
 * 3. keyVal: UInt
 * 4. modifier: GdkModifierType
 * 5. userData: gpointer
 */
public typealias AccelActivateSlot = CFunction<(
    accelGroup: CPointer<GtkAccelGroup>,
    acceleratable: CPointer<GObject>,
    keyVal: UInt,
    modifier: GdkModifierType,
    userData: gpointer
) -> Unit>

/**
 * The event handler for the *accel-changed* signal. Arguments:
 * 1. accelGroup: CPointer<GtkAccelGroup>
 * 2. keyVal: UInt
 * 3. modifier: GdkModifierType
 * 4. accelClosure: CPointer<GClosure>
 * 5. userData: gpointer
 */
public typealias AccelChangedSlot = CFunction<(
    accelGroup: CPointer<GtkAccelGroup>,
    keyVal: UInt,
    modifier: GdkModifierType,
    accelClosure: CPointer<GClosure>,
    userData: gpointer
) -> Unit>
