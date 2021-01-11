package org.guiVista.gui.keyboard

import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.toKString

public actual object KeyboardAccelerator {
    /** The default accelerator modifier mask. */
    public var defaultModMask: GdkModifierType
        get() = gtk_accelerator_get_default_mod_mask()
        set(value) = gtk_accelerator_set_default_mod_mask(value)

    /**
     * Determines whether a given [keyVal] and modifier mask constitute a valid keyboard accelerator. For example
     * the `GDK_KEY_a` [keyVal] plus `GDK_CONTROL_MASK` is valid - this is a **Ctrl+a** accelerator. But you can't
     * for instance use the `GDK_KEY_Control_L` [keyVal] as an accelerator.
     * @param keyVal A GDK key val.
     * @param modifiers Modifier mask.
     * @return A value of *true* if the accelerator is valid.
     */
    public fun valid(keyVal: UInt, modifiers: GdkModifierType): Boolean =
        gtk_accelerator_valid(keyVal, modifiers) == TRUE

    /**
     * Converts an accelerator key val, and modifier mask into a string that can be parsed. For example if you
     * pass in `GDK_KEY_q`, and `GDK_CONTROL_MASK`, this function returns **<Control>q**.
     * @param acceleratorKey Accelerator key val.
     * @param acceleratorMods Accelerator modifier mask.
     * @return The accelerator name.
     * @see fetchLabel
     */
    public fun name(acceleratorKey: UInt, acceleratorMods: GdkModifierType): String =
        gtk_accelerator_name(acceleratorKey, acceleratorMods)?.toKString() ?: ""

    /**
     * Converts an accelerator key val and modifier mask into a string, which can be used to represent the
     * accelerator to the user.
     * @param acceleratorKey Accelerator key val.
     * @param acceleratorMods Accelerator modifier mask.
     * @return The String representing the accelerator.
     */
    public fun fetchLabel(acceleratorKey: UInt, acceleratorMods: GdkModifierType): String =
        gtk_accelerator_get_label(acceleratorKey, acceleratorMods)?.toKString() ?: ""
}
