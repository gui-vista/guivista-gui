package org.guiVista.gui.widget.button

import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.dataType.SinglyLinkedList
import org.guiVista.core.disconnectGSignal

private const val GROUP_CHANGED_SIGNAL = "group-changed"

public actual class RadioButton(
    radioButtonPtr: CPointer<GtkRadioButton>? = null,
    label: String = "",
    mnemonic: Boolean = false
) : CheckButtonBase {
    @Suppress("IfThenToElvis")
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        if (radioButtonPtr != null) radioButtonPtr.reinterpret()
        else if (label.isNotEmpty() && mnemonic) gtk_check_button_new_with_mnemonic(label)
        else if (label.isNotEmpty() && !mnemonic) gtk_check_button_new_with_label(label)
        else gtk_check_button_new()
    public val gtkRadioButtonPtr: CPointer<GtkRadioButton>?
        get() = gtkWidgetPtr?.reinterpret()

    public actual fun changeGroup(newGroup: SinglyLinkedList?) {
        gtk_radio_button_set_group(gtkRadioButtonPtr, newGroup?.gSListPtr)
    }

    public actual fun fetchGroup(): SinglyLinkedList? {
        val groupPtr = gtk_radio_button_get_group(gtkRadioButtonPtr)
        return if (groupPtr != null) SinglyLinkedList(groupPtr)
        else null
    }

    public actual fun joinGroup(groupSource: RadioButton) {
        gtk_radio_button_join_group(gtkRadioButtonPtr, groupSource.gtkRadioButtonPtr)
    }

    /**
     * Connects the *group-changed* signal to a [slot] on a [RadioButton]. This signal is used when the group of radio
     * buttons that a [radio button][RadioButton] belongs to changes. Signal is emitted when a
     * [radio button][RadioButton] switches from being alone to being part of a group of two or more buttons, or
     * vice-versa, and when a button is moved from one group of two or more buttons to a different one, but not when
     * the composition of the group that a button belongs to changes.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectGroupChangedSignal(slot: CPointer<GroupChangedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkRadioButtonPtr, signal = GROUP_CHANGED_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkRadioButtonPtr, handlerId)
    }
}

public fun radioButtonWidget(
    radioButtonPtr: CPointer<GtkRadioButton>? = null,
    label: String = "",
    mnemonic: Boolean = false,
    init: RadioButton.() -> Unit = {}
): RadioButton {
    val radioButton = RadioButton(radioButtonPtr = radioButtonPtr, label = label, mnemonic = mnemonic)
    radioButton.init()
    return radioButton
}

/**
 * The event handler for the *group-changed* signal. Arguments:
 * 1. radioButton: CPointer<GtkRadioButton>
 * 2. userData: gpointer
 */
public typealias GroupChangedSlot = CFunction<(radioButton: CPointer<GtkRadioButton>, userData: gpointer) -> Unit>
