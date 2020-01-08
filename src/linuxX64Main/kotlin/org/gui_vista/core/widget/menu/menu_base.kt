package org.gui_vista.core.widget.menu

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.gui_vista.core.connectGSignal
import org.gui_vista.core.layout.Container
import org.gui_vista.core.widget.WidgetBase

/** Base interface for menu objects. */
interface MenuBase : Container {
    val gtkMenuPtr: CPointer<GtkMenu>?
        get() = gtkWidgetPtr?.reinterpret()
    /**
     * An accel path used to conveniently construct accel paths of child items. Default value is *""* (an empty
     * String).
     */
    var accelPath: String
        get() = gtk_menu_get_accel_path(gtkMenuPtr)?.toKString() ?: ""
        set(value) = gtk_menu_set_accel_path(gtkMenuPtr, value)

    /**
     * Gets the selected menu item from the menu. This is used by the GtkComboBox. Default value is *-1*.
     * @return The index for the selected item, or *-1* if no item is selected.
     */
    fun fetchActive(): CPointer<GtkWidget>? = gtk_menu_get_active(gtkMenuPtr)

    /**
     * Selects the specified menu item within the menu. This is used by the GtkComboBox and should not be used by
     * anyone else.
     * @param index The index of the menu item to select. Index values are from 0 to n-1.
     */
    fun changeActive(index: UInt) {
        gtk_menu_set_active(gtkMenuPtr, index)
    }

    /**
     * Displays menu and makes it available for selection.
     * @param widget The widget to align the menu with.
     * @param widgetAnchor The point on [widget] to align with menu's anchor point.
     * @param menuAnchor The point on menu to align with the widget's anchor point.
     * @param triggerEvent The GdkEvent that initiated this request, or *null* if it's the current event.
     */
    fun popupAtWidget(widget: WidgetBase, widgetAnchor: GdkGravity, menuAnchor: GdkGravity,
                      triggerEvent: CPointer<GdkEvent>?) {
        gtk_menu_popup_at_widget(
            menu = gtkMenuPtr,
            widget = widget.gtkWidgetPtr,
            widget_anchor = widgetAnchor,
            menu_anchor = menuAnchor,
            trigger_event = triggerEvent
        )
    }

    /**
     * Displays menu and makes it available for selection.
     * @param rectWindow The GdkWindow [rect] is relative to.
     * @param rect The GdkRectangle to align menu with.
     * @param rectAnchor The point on [rect] to align with menu's anchor point.
     * @param menuAnchor The point on menu to align with rect's anchor point.
     * @param triggerEvent The GdkEvent that initiated this request or *null* if it's the current event.
     */
    fun popupAtRect(
        rectWindow: CPointer<GdkWindow>?,
        rect: CPointer<GdkRectangle>?,
        rectAnchor: GdkGravity,
        menuAnchor: GdkGravity,
        triggerEvent: CPointer<GdkEvent>?
    ) {
        gtk_menu_popup_at_rect(
            menu = gtkMenuPtr,
            rect_window = rectWindow,
            rect = rect,
            rect_anchor = rectAnchor,
            menu_anchor = menuAnchor,
            trigger_event = triggerEvent
        )
    }

    /**
     * Displays menu and makes it available for selection.
     * @param triggerEvent The GdkEvent that initiated this request or *null* if it's the current event.
     */
    fun popupAtPointer(triggerEvent: CPointer<GdkEvent>?) {
        gtk_menu_popup_at_pointer(gtkMenuPtr, triggerEvent)
    }

    /** Gets the GtkWidget that the menu is attached to. */
    fun fetchAttachWidget(): CPointer<GtkWidget>? = gtk_menu_get_attach_widget(gtkMenuPtr)

    /**
     * Connects the *popped-up* signal to a [slot] on a menu. This signal is used when the position of menu is
     * finalized after being popped up with [popupAtRect], [popupAtWidget], or [popupAtPointer].
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectPoppedUpSignal(slot: CPointer<PoppedUpSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkMenuPtr, signal = "popped-up", slot = slot, data = userData)
}

/**
 * The event handler for the *popped-up* signal. Arguments:
 * 1. menu: CPointer<GtkMenu>
 * 2. flippedRect: gpointer
 * 3. finalRect: gpointer
 * 4. flippedX: Boolean
 * 5. flippedY: Boolean
 * 6. userData: gpointer
 */
typealias PoppedUpSlot = CFunction<(
    menu: CPointer<GtkMenu>,
    flippedRect: gpointer,
    finalRect: gpointer,
    flippedX: Boolean,
    flippedY: Boolean,
    userData: gpointer
) -> Unit>
