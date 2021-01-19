package org.guiVista.gui.widget.menu.item

/** The widget used for item in menus. */
public expect class MenuItem : MenuItemBase {
    public companion object {
        /**
         * Creates a new [MenuItem].
         * @return The new [MenuItem].
         */
        public fun create(): MenuItem

        /**
         * Creates a new [MenuItem] whose child is a label.
         * @param label The text for the label.
         * @return The new [MenuItem].
         */
        public fun fromLabel(label: String): MenuItem

        /**
         * Creates a new [MenuItem] containing a label. An underscore in [label] indicates the mnemonic for the menu
         * item.
         * @param label The text of the button, with an underscore in front of the mnemonic character.
         */
        public fun fromMnemonic(label: String): MenuItem
    }
}
