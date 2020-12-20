package org.guiVista.gui.widget.dataEntry

import org.guiVista.gui.widget.Range

/** A slider widget for selecting a value from a range. */
public expect class Scale : Range {
    /** The number of decimal places that are displayed in the value. Default value is *1*. */
    public var digits: Int

    /** Whether the current value is displayed as a string next to the slider. Default value is *false*. */
    public var drawValue: Boolean

    /** Whether the scale has an origin. Default value is *true*. */
    public var hasOrigin: Boolean
}
