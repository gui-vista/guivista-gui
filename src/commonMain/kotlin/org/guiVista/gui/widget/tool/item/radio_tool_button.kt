package org.guiVista.gui.widget.tool.item

import org.guiVista.core.dataType.SinglyLinkedList

/** A toolbar item that contains a radio button. */
public expect class RadioToolButton : ToggleToolButtonBase {
    /** The radio button group button belongs to. */
    public var group: SinglyLinkedList?
}
