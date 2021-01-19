package org.guiVista.gui.widget.button

import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.dataType.SinglyLinkedList
import org.guiVista.core.disconnectGSignal

public actual class RadioButton private constructor(radioButtonPtr: CPointer<GtkRadioButton>?) : CheckButtonBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = radioButtonPtr?.reinterpret()
    public val gtkRadioButtonPtr: CPointer<GtkRadioButton>?
        get() = gtkWidgetPtr?.reinterpret()

    public actual companion object {
        public fun fromPointer(radioButtonPtr: CPointer<GtkRadioButton>?): RadioButton = RadioButton(radioButtonPtr)

        public actual fun create(group: SinglyLinkedList?): RadioButton =
            RadioButton(gtk_radio_button_new(group?.gSListPtr)?.reinterpret())

        public actual fun fromLabel(group: SinglyLinkedList?, label: String): RadioButton =
            RadioButton(gtk_radio_button_new_with_label(group?.gSListPtr, label)?.reinterpret())

        public actual fun fromMnemonic(group: SinglyLinkedList?, label: String): RadioButton =
            RadioButton(gtk_radio_button_new_with_mnemonic(group?.gSListPtr, label)?.reinterpret())

        public actual fun fromGroup(group: RadioButton): RadioButton =
            RadioButton(gtk_radio_button_new_from_widget(group.gtkRadioButtonPtr)?.reinterpret())
    }

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

    public actual fun leaveGroup() {
        gtk_radio_button_join_group(gtkRadioButtonPtr, null)
    }

    /**
     * Connects the *group-changed* event to a [handler] on a [RadioButton]. This event is used when the group of radio
     * buttons that a [radio button][RadioButton] belongs to changes. Event is emitted when a
     * [radio button][RadioButton] switches from being alone to being part of a group of two or more buttons, or
     * vice-versa, and when a button is moved from one group of two or more buttons to a different one, but not when
     * the composition of the group that a button belongs to changes.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectGroupChangedEvent(handler: CPointer<GroupChangedHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkRadioButtonPtr, signal = RadioButtonEvent.groupChanged, slot = handler, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkRadioButtonPtr, handlerId)
    }
}

public fun radioButtonWidget(
    radioButtonPtr: CPointer<GtkRadioButton>? = null,
    groupList: SinglyLinkedList? = null,
    group: RadioButton? = null,
    label: String = "",
    mnemonic: String = "",
    init: RadioButton.() -> Unit = {}
): RadioButton {
    val radioButton = when {
        radioButtonPtr != null -> RadioButton.fromPointer(radioButtonPtr)
        group != null -> RadioButton.fromGroup(group)
        label.isNotEmpty() -> RadioButton.fromLabel(groupList, label)
        mnemonic.isNotEmpty() -> RadioButton.fromMnemonic(groupList, mnemonic)
        else -> RadioButton.create(groupList)
    }
    radioButton.init()
    return radioButton
}

/**
 * The event handler for the *group-changed* event. Arguments:
 * 1. radioButton: CPointer<GtkRadioButton>
 * 2. userData: gpointer
 */
public typealias GroupChangedHandler = CFunction<(radioButton: CPointer<GtkRadioButton>, userData: gpointer) -> Unit>
