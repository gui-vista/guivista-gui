package org.guiVista.gui.widget.menu.item

/** A choice from multiple check menu items. */
public expect class RadioMenuItem : CheckMenuItemBase {
    /**
     * Joins a GtkRadioMenuItem object to the group of another GtkRadioMenuItem object.
     * @param groupSource A [RadioMenuItem] whose group we are joining, or *null* to remove the [RadioMenuItem] from
     * its current group.
     */
    public fun joinGroup(groupSource: RadioMenuItem?)
}
