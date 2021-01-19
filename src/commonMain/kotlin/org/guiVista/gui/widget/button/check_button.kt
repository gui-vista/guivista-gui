package org.guiVista.gui.widget.button

/** Create widgets with a discrete toggle button. */
public expect class CheckButton : CheckButtonBase {
    public companion object {
        /**
         * Creates a new [CheckButton].
         * @return The new [CheckButton].
         */
        public fun create(): CheckButton

        /**
         * Creates a new [CheckButton] with a label to the right of it.
         * @param label The text for the check button.
         * @return The new [CheckButton]
         */
        public fun fromLabel(label: String): CheckButton

        /**
         * Creates a new [CheckButton] containing a label. An underscore in label indicates the mnemonic for the check
         * button.
         * @param label The text of the button, with an underscore in front of the mnemonic character.
         * @return The new [CheckButton].
         */
        public fun fromMnemonic(label: String): CheckButton
    }
}
