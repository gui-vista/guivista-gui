package org.guivista.core.widget.button

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.connectGSignal
import org.guivista.core.disconnectGSignal

private const val GROUP_CHANGED_SIGNAL = "group-changed"

/** A choice from multiple check buttons. */
class RadioButton(label: String = "", mnemonic: Boolean = false) : CheckButtonBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        if (label.isNotEmpty() && mnemonic) gtk_check_button_new_with_mnemonic(label)
        else if (label.isNotEmpty() && !mnemonic) gtk_check_button_new_with_label(label)
        else gtk_check_button_new()
    val gtkRadioButtonPtr: CPointer<GtkRadioButton>?
        get() = gtkWidgetPtr?.reinterpret()

    /**
     * Joins this [RadioButton] object to the group of another [RadioButton].
     * @param groupSource A [RadioButton] object who's group we are joining, or *null* to remove this [RadioButton]
     * from its group.
     */
    fun joinGroup(groupSource: RadioButton) {
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
    fun connectGroupChangedSignal(slot: CPointer<GroupChangedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkRadioButtonPtr, signal = GROUP_CHANGED_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkRadioButtonPtr, handlerId)
    }
}

fun radioButtonWidget(label: String = "", mnemonic: Boolean = false, init: RadioButton.() -> Unit): RadioButton {
    val radioButton =
        if (label.isNotEmpty() && mnemonic) RadioButton(label = label, mnemonic = mnemonic)
        else if (label.isNotEmpty() && !mnemonic) RadioButton(label = label, mnemonic = mnemonic)
        else RadioButton()
    radioButton.init()
    return radioButton
}

/**
 * The event handler for the *group-changed* signal. Arguments:
 * 1. radioButton: CPointer<GtkRadioButton>
 * 2. userData: gpointer
 */
typealias GroupChangedSlot = CFunction<(radioButton: CPointer<GtkRadioButton>, userData: gpointer) -> Unit>
