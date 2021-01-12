package org.guiVista.gui

import glib2.g_object_unref
import gtk3.*
import kotlinx.cinterop.CPointer
import org.guiVista.core.ObjectBase
import org.guiVista.gui.widget.WidgetBase
import org.guiVista.io.icon.IconBase

/** Add tips to your widgets. */
public actual class Tooltip(ptr: CPointer<GtkTooltip>?) : ObjectBase {
    public val gtkTooltipPtr: CPointer<GtkTooltip>? = ptr

    public actual infix fun changeMarkup(markup: String) {
        gtk_tooltip_set_markup(gtkTooltipPtr, markup)
    }

    public actual infix fun changeText(text: String) {
        gtk_tooltip_set_text(gtkTooltipPtr, text)
    }

    public actual infix fun changeCustom(customWidget: WidgetBase?) {
        gtk_tooltip_set_custom(gtkTooltipPtr, customWidget?.gtkWidgetPtr)
    }

    /**
     * Sets the icon of the tooltip (which is in front of the text) to be the icon indicated by [iconName] with the
     * size indicated by [size]. If [iconName] is *null* then the image will be hidden.
     * @param iconName An icon name, or *null*.
     * @param size A stock icon size.
     */
    public fun changeIconFromIconName(iconName: String, size: GtkIconSize) {
        gtk_tooltip_set_icon_from_icon_name(tooltip = gtkTooltipPtr, icon_name = iconName, size = size)
    }

    /**
     * Sets the icon of the tooltip (which is in front of the text) to be the icon indicated by [icon] with the size
     * indicated by [size]. If [icon] is *null* then the image will be hidden.
     * @param icon The icon, or *null*.
     * @param size A stock icon size.
     */
    public fun changeIconFromIcon(icon: IconBase?, size: GtkIconSize) {
        gtk_tooltip_set_icon_from_gicon(tooltip = gtkTooltipPtr, gicon = icon?.gIconPtr, size = size)
    }

    override fun close() {
        g_object_unref(gtkTooltipPtr)
    }

}