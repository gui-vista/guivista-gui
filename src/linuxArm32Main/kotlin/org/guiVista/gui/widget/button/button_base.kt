package org.guiVista.gui.widget.button

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal
import org.guiVista.gui.layout.Container
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase

private const val CLICKED_SIGNAL = "clicked"
private const val ACTIVATE_SIGNAL = "activate"

actual interface ButtonBase : Container {
    val gtkButtonPtr: CPointer<GtkButton>?
        get() = gtkWidgetPtr?.reinterpret()

    /**
     * Text of the label widget inside the button, if the button contains a label widget. Default value is *""* (an
     * empty String).
     */
    var label: String
        set(value) = gtk_button_set_label(gtkButtonPtr, value)
        get() = gtk_button_get_label(gtkButtonPtr)?.toKString() ?: ""

    /** The border relief style. Default value is *GtkReliefStyle.GTK_RELIEF_NORMAL*. */
    var relief: GtkReliefStyle
        get() = gtk_button_get_relief(gtkButtonPtr)
        set(value) = gtk_button_set_relief(gtkButtonPtr, value)

    /**
     * If *true* the button will ignore the “gtk-button-images” setting, and always show the image if available. Use
     * this property if the button would be useless, or hard to use without the image. Default value is *false*.
     */
    var alwaysShowImage: Boolean
        get() = gtk_button_get_always_show_image(gtkButtonPtr) == TRUE
        set(value) = gtk_button_set_always_show_image(gtkButtonPtr, if (value) TRUE else FALSE)

    /** The child widget to appear next to the button text. */
    var image: WidgetBase?
        set(value) = gtk_button_set_image(gtkButtonPtr, value?.gtkWidgetPtr)
        get() {
            val tmp = gtk_button_get_image(gtkButtonPtr)
            return if (tmp != null) Widget(tmp) else null
        }

    /**
     * The position of the image relative to the text inside the button. Default value is
     * *GtkPositionType.GTK_POS_LEFT*.
     */
    var imagePosition: GtkPositionType
        get() = gtk_button_get_image_position(gtkButtonPtr)
        set(value) = gtk_button_set_image_position(gtkButtonPtr, value)

    /**
     * If set an underline in the text indicates the next character should be used for the mnemonic accelerator key.
     * Default value is *false*.
     */
    var useUnderline: Boolean
        get() = gtk_button_get_use_underline(gtkButtonPtr) == TRUE
        set(value) = gtk_button_set_use_underline(gtkButtonPtr, if (value) TRUE else FALSE)

    /**
     * Connects the *clicked* signal to a [slot] on a button. This signal is used when a user has clicked on the button.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectClickedSignal(slot: CPointer<ClickedSlot>, userData: gpointer): UInt =
        connectGSignal(obj = gtkButtonPtr, signal = CLICKED_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *activate* signal to a [slot] on a button. This signal is an action signal that causes the button
     * to animate press then release. Applications should **NEVER** connect to this signal, but use the “clicked”
     * signal.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectActivateSignal(slot: CPointer<ActivateSlot>, userData: gpointer): UInt =
        connectGSignal(obj = gtkButtonPtr, signal = ACTIVATE_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkButtonPtr, handlerId.toUInt())
    }
}

/**
 * The event handler for the *activate* signal. Arguments:
 * 1. widget: CPointer<GtkButton>
 * 2. userData: gpointer
 */
typealias ActivateSlot = CFunction<(widget: CPointer<GtkButton>?, userData: gpointer) -> Unit>

/**
 * The event handler for the *clicked* signal. Arguments:
 * 1. button: CPointer<GtkButton>
 * 2. userData: gpointer
 */
typealias ClickedSlot = CFunction<(button: CPointer<GtkButton>?, userData: gpointer) -> Unit>
