package org.guiVista.gui.widget.button

import org.guiVista.core.dataType.SinglyLinkedList

/** A choice from multiple check buttons. */
public expect class RadioButton : CheckButtonBase {
    /**
     * Changes the [radio button’s][RadioButton] group. It should be noted that this does not change the layout of your
     * interface in any way. If you are changing the group it is likely you will need to re-arrange the user interface
     * to reflect these changes.
     * @param newGroup An existing radio button group such as one returned from [fetchGroup], or *null*.
     */
    public fun changeGroup(newGroup: SinglyLinkedList?)

    /**
     * Retrieves the group assigned to a radio button.
     * @return A [list][SinglyLinkedList] containing all the radio buttons in the same group as this radio button. The
     * returned list is owned by the radio button, and **MUST** not be modified or freed!
     */
    public fun fetchGroup(): SinglyLinkedList?

    /**
     * Joins this [RadioButton] object to the group of another [RadioButton].
     * @param groupSource A [RadioButton] object who's group we are joining.
     * @see leaveGroup
     */
    public fun joinGroup(groupSource: RadioButton)

    /**
     * The [RadioButton] leaves the group it was a member of.
     * @see joinGroup
     */
    public fun leaveGroup()

    public companion object {
        /**
         * Creates a new [RadioButton]. To be of any practical value, a widget should then be packed into the radio
         * button.
         * @param group An existing radio button group, or *null* if you are creating a new group.
         * @return The new [RadioButton].
         */
        public fun create(group: SinglyLinkedList? = null): RadioButton

        /**
         * Creates a new [RadioButton] with a text label.
         * @param group An existing radio button group, or *null* if you are creating a new group.
         * @param label The text label to display next to the radio button.
         * @return The new [RadioButton].
         */
        public fun fromLabel(group: SinglyLinkedList? = null, label: String): RadioButton

        /**
         * Creates a new [RadioButton] containing a label, and adds it to the same group as [group]. An underscore in
         * label indicates the mnemonic for the button.
         * @param group The radio button group, or *null*.
         * @param label The text of the button, with an underscore in front of the mnemonic character.
         */
        public fun fromMnemonic(group: SinglyLinkedList? = null, label: String): RadioButton

        /**
         * Creates a new [RadioButton], and adds it to the same group as [group]. A widget should be packed into the
         * radio button.
         * @param group An existing [RadioButton].
         * @return The new [RadioButton].
         */
        public fun fromGroup(group: RadioButton): RadioButton
    }
}
