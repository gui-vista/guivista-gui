package org.guiVista.gui.widget.menu.item

import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.dataType.SinglyLinkedList
import org.guiVista.core.disconnectGSignal

public actual class RadioMenuItem private constructor(radioMenuItemPtr: CPointer<GtkRadioMenuItem>?) :
    CheckMenuItemBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = radioMenuItemPtr?.reinterpret()
    public val gtkRadioMenuItemPtr: CPointer<GtkRadioMenuItem>?
        get() = gtkWidgetPtr?.reinterpret()

    /**
     * Connects the *group-changed* signal to a [slot] on a [RadioMenuItem]. This signal is used when the group is
     * changed.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectGroupChangedSignal(slot: CPointer<GroupChangedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkRadioMenuItemPtr, signal = "group-changed", slot = slot, data = userData).toULong()

    public actual fun joinGroup(groupSource: RadioMenuItem) {
        gtk_radio_menu_item_join_group(gtkRadioMenuItemPtr, groupSource.gtkRadioMenuItemPtr)
    }

    public actual fun leaveGroup() {
        gtk_radio_menu_item_join_group(gtkRadioMenuItemPtr, null)
    }

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkRadioMenuItemPtr, handlerId.toUInt())
    }

    public actual companion object {
        public fun fromPointer(radioMenuItemPtr: CPointer<GtkRadioMenuItem>?): RadioMenuItem =
            RadioMenuItem(radioMenuItemPtr)

        public actual fun create(group: SinglyLinkedList?): RadioMenuItem =
            RadioMenuItem(gtk_radio_menu_item_new(group?.gSListPtr)?.reinterpret())

        public actual fun fromMnemonic(group: SinglyLinkedList?, label: String): RadioMenuItem =
            RadioMenuItem(gtk_radio_menu_item_new_with_mnemonic(group?.gSListPtr, label)?.reinterpret())

        public actual fun fromLabel(group: SinglyLinkedList?, label: String): RadioMenuItem =
            RadioMenuItem(gtk_radio_menu_item_new_with_label(group?.gSListPtr, label)?.reinterpret())

        public actual fun fromGroup(group: RadioMenuItem): RadioMenuItem =
            RadioMenuItem(gtk_radio_menu_item_new_from_widget(group.gtkRadioMenuItemPtr)?.reinterpret())
    }
}

public fun radioMenuItem(
    radioMenuItemPtr: CPointer<GtkRadioMenuItem>? = null,
    groupList: SinglyLinkedList? = null,
    group: RadioMenuItem? = null,
    label: String = "",
    mnemonic: String = "",
    init: RadioMenuItem.() -> Unit = {}
): RadioMenuItem {
    val menuItem = when {
        radioMenuItemPtr != null -> RadioMenuItem.fromPointer(radioMenuItemPtr)
        group != null -> RadioMenuItem.fromGroup(group)
        label.isNotEmpty() -> RadioMenuItem.fromLabel(groupList, label)
        mnemonic.isNotEmpty() -> RadioMenuItem.fromMnemonic(groupList, mnemonic)
        else -> RadioMenuItem.create()
    }
    menuItem.init()
    return menuItem
}

/**
 * The event handler for the *group-changed* signal. Arguments:
 * 1. radioMenuItem: CPointer<GtkRadioMenuItem>
 * 2. userData: gpointer
 */
public typealias GroupChangedSlot = CFunction<(radioMenuItem: CPointer<GtkRadioMenuItem>, userData: gpointer) -> Unit>
