package org.guiVista.gui

import org.guiVista.core.ObjectBase
import org.guiVista.gui.widget.WidgetBase
import org.guiVista.gui.widget.display.LabelBase
import org.guiVista.gui.widget.display.Image

/** Add tips to your widgets. */
public expect class Tooltip : ObjectBase {
    /**
     * Sets the text of the tooltip to be [markup], which is marked up with the Pango text markup language. If
     * [markup] is empty (**""**) then the label will be hidden.
     * @param markup A markup String (see Pango markup format) or *""*.
     */
    public infix fun changeMarkup(markup: String)

    /**
     * Sets the [text] of the tooltip to be text. If [text] is empty (**""**) then the label will be hidden.
     * @param text A text String or *""*.
     * @see changeMarkup
     */
    public infix fun changeText(text: String)

    /**
     * Replaces the widget packed into the tooltip with [customWidget]. Note that [customWidget] doesn't get destroyed
     * when the tooltip goes away. By default a box with a [Image], and [LabelBase] is embedded in the tooltip, which
     * can be configured using [changeMarkup] and `gtk_tooltip_set_icon()`.
     * @param customWidget A widget, or *null* to unset the custom widget.
     */
    public infix fun changeCustom(customWidget: WidgetBase?)
}