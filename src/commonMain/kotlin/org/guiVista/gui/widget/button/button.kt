package org.guiVista.gui.widget.button

/** A widget that emits a signal when clicked on. */
public expect class Button : ButtonBase {
    public companion object {
        /**
         * Creates a new [Button].
         * @return The new [Button].
         */
        public fun create(): Button

        /**
         * Creates a new [Button] with a label containing the given text.
         * @param label The text you want the label to hold.
         * @return The new [Button].
         */
        public fun fromLabel(label: String): Button

        /**
         * Creates a new [Button] containing a label. If characters in label are preceded by an underscore then they
         * are underlined. If you need a literal underscore character in a label, use “__” (two underscores). The first
         * underlined character represents a keyboard accelerator called a mnemonic. Pressing **Alt**, and that key
         * activates the button.
         * @param label The text of the button, with an underscore in front of the mnemonic character.
         * @return The new [Button].
         */
        public fun fromMnemonic(label: String): Button
    }
}
