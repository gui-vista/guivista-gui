package org.guiVista.gui.widget.menu.item

/** A menu item with a check box. */
public expect class CheckMenuItem : CheckMenuItemBase {
    public companion object {
        /**
         * Creates a new [CheckMenuItem].
         * @return The new [CheckMenuItem].
         */
        public fun create(): CheckMenuItem

        /**
         * Creates a new [CheckMenuItem] with a label.
         * @param label The string to use for the label.
         * @return The new [CheckMenuItem].
         */
        public fun fromLabel(label: String): CheckMenuItem

        /**
         * Creates a new [CheckMenuItem] containing a label. An underscore in [label] indicates the mnemonic for the
         * menu item.
         * @param label The text of the button, with an underscore in front of the character.
         * @return The new [CheckMenuItem].
         */
        public fun fromMnemonic(label: String): CheckMenuItem
    }
}
