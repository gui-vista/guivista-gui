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
     * @param groupSource A [RadioButton] object who's group we are joining, or *null* to remove this [RadioButton]
     * from its group.
     */
    public fun joinGroup(groupSource: RadioButton)
}
