package org.guiVista.gui.keyboard

import glib2.*
import gtk3.*
import kotlinx.cinterop.*
import org.guiVista.core.ObjectBase
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

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
     * Connects the *accel-activate* event to a [handler] on an [AcceleratorGroup]. This event is an implementation
     * detail, and isn't meant to be used in applications.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectAccelActivateEvent(handler: CPointer<AccelActivateHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkAccelGroupPtr, signal = AcceleratorGroupEvent.accelActivate, slot = handler,
            data = userData)

    /**
     * Connects the *accel-changed* event to a [handler] on an [AcceleratorGroup]. This event is used when an entry is
     * added to or removed from the accel group. Widgets like `GtkAccelLabel` which display an associated accelerator
     * should connect to this event, and rebuild their visual representation if the accelerator closure is theirs.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectAccelChangedEvent(handler: CPointer<AccelChangedHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkAccelGroupPtr, signal = AcceleratorGroupEvent.accelChanged, slot = handler,
            data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkAccelGroupPtr, handlerId)
    }
}

/**
 * The event handler for the *accel-activate* event. Arguments:
 * 1. accelGroup: CPointer<GtkAccelGroup>
 * 2. acceleratable: CPointer<GObject>
 * 3. keyVal: UInt
 * 4. modifier: GdkModifierType
 * 5. userData: gpointer
 */
public typealias AccelActivateHandler = CFunction<(
    accelGroup: CPointer<GtkAccelGroup>,
    acceleratable: CPointer<GObject>,
    keyVal: UInt,
    modifier: GdkModifierType,
    userData: gpointer
) -> Unit>

/**
 * The event handler for the *accel-changed* event. Arguments:
 * 1. accelGroup: CPointer<GtkAccelGroup>
 * 2. keyVal: UInt
 * 3. modifier: GdkModifierType
 * 4. accelClosure: CPointer<GClosure>
 * 5. userData: gpointer
 */
public typealias AccelChangedHandler = CFunction<(
    accelGroup: CPointer<GtkAccelGroup>,
    keyVal: UInt,
    modifier: GdkModifierType,
    accelClosure: CPointer<GClosure>,
    userData: gpointer
) -> Unit>
