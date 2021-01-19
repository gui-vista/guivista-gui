package org.guiVista.gui.widget.menu.item

import org.guiVista.core.dataType.SinglyLinkedList

/** A choice from multiple check menu items. */
public expect class RadioMenuItem : CheckMenuItemBase {
    /**
     * Joins a [RadioMenuItem] object to the group of another [RadioMenuItem] object.
     * @param groupSource A [RadioMenuItem] whose group we are joining.
     * @see leaveGroup
     */
    public fun joinGroup(groupSource: RadioMenuItem)

    /**
     * The [RadioMenuItem] leaves the group it was a member of.
     * @see joinGroup
     */
    public fun leaveGroup()

    public companion object {
        /**
         * Creates a new [RadioMenuItem].
         * @param group The group that the radio menu item will be attached to, or *null*.
         * @return The new [RadioMenuItem].
         */
        public fun create(group: SinglyLinkedList? = null): RadioMenuItem

        /**
         * Creates a new [RadioMenuItem] containing a label. An underscore in [label] indicates the mnemonic for the
         * menu item.
         * @param group The group the radio menu item is inside, or *null*.
         * @param label The text of the button, with an underscore in front of the mnemonic character.
         * @return The new [RadioMenuItem].
         */
        public fun fromMnemonic(group: SinglyLinkedList? = null, label: String): RadioMenuItem

        /**
         * Creates a new [RadioMenuItem] whose child is a simple label.
         * @param group The group the radio menu item is inside, or *null*.
         * @param label The text for the label.
         * @return The new [RadioMenuItem].
         */
        public fun fromLabel(group: SinglyLinkedList? = null, label: String): RadioMenuItem

        /**
         * Creates a new [RadioMenuItem], and adds it to the same group as [group].
         * @param group The radio menu item that contains the group.
         * @return The new [RadioMenuItem].
         */
        public fun fromGroup(group: RadioMenuItem): RadioMenuItem
    }
}
