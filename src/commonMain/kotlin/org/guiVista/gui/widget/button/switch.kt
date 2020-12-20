package org.guiVista.gui.widget.button

import org.guiVista.gui.widget.WidgetBase

/** A *light switch* style toggle. */
public expect class Switch : WidgetBase {
    /** Whether the GtkSwitch widget is in its on or off state. Default value is *false*. */
    public var active: Boolean

    /**
     * The backend state that is controlled by the switch. Normally this is the same as “active”, unless the switch is
     * set up for delayed state changes. This function is typically called from a “state-set” signal handler. Default
     * value is *false*.
     */
    public var state: Boolean
}
