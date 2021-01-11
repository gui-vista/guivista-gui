package org.guiVista.gui.keyboard

import org.guiVista.core.ObjectBase

/** Loadable keyboard accelerator specifications. */
public expect object AcceleratorMap : ObjectBase {
    /**
     * Parses a file previously saved with [save] for accelerator specifications, and propagates them accordingly.
     * @param fileName A file containing accelerator specifications in the GLib file name encoding.
     */
    public fun load(fileName: String)

    /**
     * Saves current accelerator specifications (accelerator path, key and modifiers) to [fileName]. The file is
     * written in a format suitable to be read back in by [load].
     * @param fileName The name of the file to contain accelerator specifications in the GLib file name encoding.
     */
    public fun save(fileName: String)

    /**
     * Locks the given accelerator path. If the accelerator map doesn’t yet contain an entry for [accelPath] then a new
     * one is created. Locking an accelerator path prevents its accelerator from being changed during runtime. A locked
     * accelerator path can be unlocked by [unlockPath]. Refer to `gtk_accel_map_change_entry()` for information about
     * runtime accelerator changes.
     *
     * If called more than once then [accelPath] remains locked until `gtk_accel_map_unlock_path()` has been called an
     * equivalent number of times. Note that locking of individual accelerator paths is independent from locking the
     * [AcceleratorGroup] containing them. For runtime accelerator changes to be possible, both the accelerator path
     * and its [AcceleratorGroup] have to be unlocked.
     * @param accelPath A valid accelerator path.
     */
    public fun lockPath(accelPath: String)

    /**
     * Undoes the last call to [lockPath] on this [accelPath]. Refer to [lockPath] for information about accelerator
     * path locking.
     * @param accelPath A valid accelerator path.
     * @see lockPath
     */
    public fun unlockPath(accelPath: String)

    /**
     * Looks up the accelerator entry for [accelPath].
     * @param accelPath A valid accelerator path.
     * @return A value of *true* if the [accelPath] is known.
     */
    public fun lookupEntry(accelPath: String): Boolean

    /**
     * File descriptor variant of [load]. Note that the file descriptor will **NOT** be closed by this function.
     * @param fd A valid readable file descriptor.
     */
    public fun loadFileDescriptor(fd: Int)

    /**
     * File descriptor variant of [save]. Note that the file descriptor will **NOT** be closed by this function.
     * @param fd A valid readable file descriptor.
     */
    public fun saveFileDescriptor(fd: Int)
}
