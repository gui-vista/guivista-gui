package org.guiVista.gui.keyboard

import org.guiVista.core.ObjectBase

/** Groups of global keyboard accelerators for an entire window. */
public expect class AcceleratorGroup : ObjectBase {
    /** Is the accel group locked. Default value is *false*. */
    public val isLocked: Boolean

    /**
     * Locks the given accelerator group. Locking an accelerator group prevents the accelerators contained within it to
     * be changed during runtime. Refer to `gtk_accel_map_change_entry()` about runtime accelerator changes. If called
     * more than once then [AcceleratorGroup] remains locked until [unlock] has been called an
     * equivalent number of times.
     */
    public fun lock()

    /** Undoes the last call to [lock] on this accel_group/ */
    public fun unlock()
}