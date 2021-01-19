package org.guiVista.gui.widget.button

/** Create buttons which retain their state. */
public expect class ToggleButton : ToggleButtonBase {
    public companion object {
        /**
         * Creates a new [ToggleButton]. A widget should be packed into the button.
         * @return A new [ToggleButton].
         */
        public fun create(): ToggleButton

        /**
         * Creates a new [ToggleButton] with a text label.
         * @param label A String containing the message to be placed in the toggle button.
         * @return A new [ToggleButton].
         */
        public fun fromLabel(label: String): ToggleButton

        /**
         * Creates a new [ToggleButton] containing a label. An underscore in label indicates the mnemonic for the
         * button.
         * @param label The text of the button, with an underscore in front of the mnemonic character.
         * @return A new [ToggleButton].
         */
        public fun fromMnemonic(label: String): ToggleButton
    }
}
