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

public actual interface ButtonBase : Container {
    public val gtkButtonPtr: CPointer<GtkButton>?
        get() = gtkWidgetPtr?.reinterpret()

    /**
     * Text of the label widget inside the button, if the button contains a label widget. Default value is *""* (an
     * empty String).
     */
    public var label: String
        set(value) = gtk_button_set_label(gtkButtonPtr, value)
        get() = gtk_button_get_label(gtkButtonPtr)?.toKString() ?: ""

    /** The border relief style. Default value is *GtkReliefStyle.GTK_RELIEF_NORMAL*. */
    public var relief: GtkReliefStyle
        get() = gtk_button_get_relief(gtkButtonPtr)
        set(value) = gtk_button_set_relief(gtkButtonPtr, value)

    /**
     * If *true* the button will ignore the “gtk-button-images” setting, and always show the image if available. Use
     * this property if the button would be useless, or hard to use without the image. Default value is *false*.
     */
    public var alwaysShowImage: Boolean
        get() = gtk_button_get_always_show_image(gtkButtonPtr) == TRUE
        set(value) = gtk_button_set_always_show_image(gtkButtonPtr, if (value) TRUE else FALSE)

    /** The child widget to appear next to the button text. */
    public var image: WidgetBase?
        set(value) = gtk_button_set_image(gtkButtonPtr, value?.gtkWidgetPtr)
        get() {
            val tmp = gtk_button_get_image(gtkButtonPtr)
            return if (tmp != null) Widget(tmp) else null
        }

    /**
     * The position of the image relative to the text inside the button. Default value is
     * *GtkPositionType.GTK_POS_LEFT*.
     */
    public var imagePosition: GtkPositionType
        get() = gtk_button_get_image_position(gtkButtonPtr)
        set(value) = gtk_button_set_image_position(gtkButtonPtr, value)

    /**
     * If set an underline in the text indicates the next character should be used for the mnemonic accelerator key.
     * Default value is *false*.
     */
    public var useUnderline: Boolean
        get() = gtk_button_get_use_underline(gtkButtonPtr) == TRUE
        set(value) = gtk_button_set_use_underline(gtkButtonPtr, if (value) TRUE else FALSE)

    /**
     * Connects the *clicked* event to a [handler] on a button. This event is used when a user has clicked on the
     * button.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectClickedEvent(handler: CPointer<ClickedHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkButtonPtr, signal = ButtonBaseEvent.clicked, slot = handler, data = userData)

    /**
     * Connects the *activate* event to a [handler] on a button. This event is an action event that causes the button
     * to animate press then release. Applications should **NEVER** connect to this event, but use the **clicked**
     * event.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectActivateEvent(handler: CPointer<ActivateHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkButtonPtr, signal = ButtonBaseEvent.activate, slot = handler, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkButtonPtr, handlerId)
    }
}

/**
 * The event handler for the *activate* event. Arguments:
 * 1. widget: CPointer<GtkButton>
 * 2. userData: gpointer
 */
public typealias ActivateHandler = CFunction<(widget: CPointer<GtkButton>?, userData: gpointer) -> Unit>

/**
 * The event handler for the *clicked* event. Arguments:
 * 1. button: CPointer<GtkButton>
 * 2. userData: gpointer
 */
public typealias ClickedHandler = CFunction<(button: CPointer<GtkButton>?, userData: gpointer) -> Unit>
