package org.guiVista.gui.widget.display

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal
import org.guiVista.gui.widget.WidgetBase

private const val OFFSET_CHANGED_SIGNAL = "offset-changed"

public actual class LevelBar(levelBarPtr: CPointer<GtkLevelBar>? = null) : WidgetBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = levelBarPtr?.reinterpret() ?: gtk_level_bar_new()
    public val gtkLevelBarPtr: CPointer<GtkLevelBar>?
        get() = gtkWidgetPtr?.reinterpret()
    public actual var inverted: Boolean
        get() = gtk_level_bar_get_inverted(gtkLevelBarPtr) == TRUE
        set(value) = gtk_level_bar_set_inverted(gtkLevelBarPtr, if (value) TRUE else FALSE)
    public actual var maxValue: Double
        get() = gtk_level_bar_get_max_value(gtkLevelBarPtr)
        set(value) {
            if (value >= 0.0) gtk_level_bar_set_max_value(gtkLevelBarPtr, value)
        }
    public actual var minValue: Double
        get() = gtk_level_bar_get_min_value(gtkLevelBarPtr)
        set(value) {
            if (value >= 0.0) gtk_level_bar_set_min_value(gtkLevelBarPtr, value)
        }

    /**
     * Determines the way [LevelBar] interprets the value properties to draw the level fill area. Specifically when the
     * value is **GTK_LEVEL_BAR_MODE_CONTINUOUS** the [LevelBar] will draw a single block representing the current
     * value in that area. When the value is **GTK_LEVEL_BAR_MODE_DISCRETE** the widget will draw a succession of
     * separate blocks filling the draw area, with the number of blocks being equal to the units separating the
     * integral roundings of [minValue] and [maxValue].
     *
     * Default value is *GtkLevelBarMode.GTK_LEVEL_BAR_MODE_CONTINUOUS*.
     */
    public var mode: GtkLevelBarMode
        get() = gtk_level_bar_get_mode(gtkLevelBarPtr)
        set(value) = gtk_level_bar_set_mode(gtkLevelBarPtr, value)
    public actual var value: Double
        get() = gtk_level_bar_get_value(gtkLevelBarPtr)
        set(value) {
            if (value >= 0.0) gtk_level_bar_set_value(gtkLevelBarPtr, value)
        }

    /**
     * Connects the *offset-changed* signal to a [slot] on a [LevelBar]. This signal is used when the
     * bar changes value as an effect to ?? being called. The signal supports detailed connections. You can connect to
     * the detailed signal "changed::x" in order to only receive callbacks when the value of offset *x* changes.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectOffsetChangedSignal(slot: CPointer<OffsetChangedSlot>, userData: gpointer): UInt =
        connectGSignal(obj = gtkLevelBarPtr, signal = OFFSET_CHANGED_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkLevelBarPtr, handlerId.toUInt())
    }
}

public fun levelBarWidget(levelBarPtr: CPointer<GtkLevelBar>? = null, init: LevelBar.() -> Unit): LevelBar {
    val levelBar = LevelBar(levelBarPtr)
    levelBar.init()
    return levelBar
}

/**
 * The event handler for the *offset-changed* signal. Arguments:
 * 1. levelBar: CPointer<GtkLevelBar>
 * 2. name: String
 * 3. userData: gpointer
 */
public typealias OffsetChangedSlot = CFunction<(app: CPointer<GtkLevelBar>, name: String, userData: gpointer) -> Unit>
