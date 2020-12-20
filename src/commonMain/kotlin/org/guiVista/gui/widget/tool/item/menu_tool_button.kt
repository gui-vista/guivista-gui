package org.guiVista.gui.widget.tool.item

/** A tool item containing a button with an additional drop down menu. */
public expect class MenuToolButton : ToolButtonBase {
    /**
     * Changes the tooltip text to be used as tooltip for the arrow button which pops up the menu. See
     * `gtk_tool_item_set_tooltip_text()` for setting a tooltip on the whole [MenuToolButton].
     * @param text Text to be used as tooltip text for button’s arrow button.
     */
    public infix fun changeArrowTooltipText(text: String)
}
