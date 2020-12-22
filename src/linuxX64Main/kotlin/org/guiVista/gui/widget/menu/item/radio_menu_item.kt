package org.guiVista.gui.widget.menu.item

import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

public actual class RadioMenuItem(radioMenuItemPtr: CPointer<GtkRadioMenuItem>? = null, label: String = "",
                                  mnemonic: Boolean = false) : CheckMenuItemBase {
    @Suppress("IfThenToElvis")
    override val gtkWidgetPtr: CPointer<GtkWidget>? = when {
        radioMenuItemPtr != null -> radioMenuItemPtr.reinterpret()
        label.isNotEmpty() && mnemonic -> gtk_radio_menu_item_new_with_mnemonic(null, label)
        label.isNotEmpty() && !mnemonic -> gtk_radio_menu_item_new_with_label(null, label)
        else -> gtk_radio_menu_item_new(null)
    }
    public val gtkRadioMenuItemPtr: CPointer<GtkRadioMenuItem>?
        get() = gtkWidgetPtr?.reinterpret()

    /**
     * Connects the *group-changed* signal to a [slot] on a [RadioMenuItem]. This signal is used when the group is
     * changed.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectGroupChangedSignal(slot: CPointer<GroupChangedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkRadioMenuItemPtr, signal = "group-changed", slot = slot, data = userData)

    public actual fun joinGroup(groupSource: RadioMenuItem?) {
        gtk_radio_menu_item_join_group(gtkRadioMenuItemPtr, groupSource?.gtkRadioMenuItemPtr)
    }

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkRadioMenuItemPtr, handlerId)
    }
}

public fun radioMenuItem(
    radioMenuItemPtr: CPointer<GtkRadioMenuItem>? = null,
    label: String = "",
    mnemonic: Boolean = false,
    init: RadioMenuItem.() -> Unit
): RadioMenuItem {
    val menuItem = RadioMenuItem(radioMenuItemPtr = radioMenuItemPtr, label = label, mnemonic = mnemonic)
    menuItem.init()
    return menuItem
}

/**
 * The event handler for the *group-changed* signal. Arguments:
 * 1. radioMenuItem: CPointer<GtkRadioMenuItem>
 * 2. userData: gpointer
 */
public typealias GroupChangedSlot = CFunction<(radioMenuItem: CPointer<GtkRadioMenuItem>, userData: gpointer) -> Unit>
