package org.guivista.core.widget.display

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.connectGObjectSignal
import org.guivista.core.widget.Widget

/** A bar that can used as a level indicator. */
class LevelBar : Widget {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = gtk_level_bar_new()
    val gtkLevelBarPtr: CPointer<GtkLevelBar>?
        get() = gtkWidgetPtr?.reinterpret()
    /**
     * Level bars normally grow from top to bottom or left to right. Inverted level bars grow in the opposite direction.
     */
    var inverted: Boolean
        get() = gtk_level_bar_get_inverted(gtkLevelBarPtr) == TRUE
        set(value) = gtk_level_bar_set_inverted(gtkLevelBarPtr, if (value) TRUE else FALSE)
    /** Determines the maximum value of the interval that can be displayed by the bar.*/
    var maxValue: Double
        get() = gtk_level_bar_get_max_value(gtkLevelBarPtr)
        set(value) = gtk_level_bar_set_max_value(gtkLevelBarPtr, value)
    /** determines the minimum value of the interval that can be displayed by the bar. */
    var minValue: Double
        get() = gtk_level_bar_get_min_value(gtkLevelBarPtr)
        set(value) = gtk_level_bar_set_min_value(gtkLevelBarPtr, value)
    /**
     * Determines the way [LevelBar] interprets the value properties to draw the level fill area. Specifically when the
     * value is **GTK_LEVEL_BAR_MODE_CONTINUOUS** the [LevelBar] will draw a single block representing the current
     * value in that area. When the value is **GTK_LEVEL_BAR_MODE_DISCRETE** the widget will draw a succession of
     * separate blocks filling the draw area, with the number of blocks being equal to the units separating the
     * integral roundings of [minValue] and [maxValue].
     */
    var mode: GtkLevelBarMode
        get() = gtk_level_bar_get_mode(gtkLevelBarPtr)
        set(value) = gtk_level_bar_set_mode(gtkLevelBarPtr, value)
    /** Determines the currently filled value of the level bar. */
    var value: Double
        get() = gtk_level_bar_get_value(gtkLevelBarPtr)
        set(value) = gtk_level_bar_set_value(gtkLevelBarPtr, value)

    /**
     * Connects the *offset-changed* signal to a [slot] on a level bar. The *offset-changed* signal is used when the
     * bar changes value as an effect to ?? being called. The signal supports detailed connections. You can connect to
     * the detailed signal "changed::x" in order to only receive callbacks when the value of offset *x* changes.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectOffsetChangedSignal(slot: CPointer<OffsetChangedSlot>, userData: gpointer): ULong =
            connectGObjectSignal(obj = gtkLevelBarPtr, signal = "offset-changed", slot = slot, data = userData)
}

fun levelBarWidget(init: LevelBar.() -> Unit): LevelBar {
    val levelBar = LevelBar()
    levelBar.init()
    return levelBar
}

/**
 * The event handler for the *offset-changed* signal. Arguments:
 * 1. levelBar: CPointer<GtkLevelBar>
 * 2. name: String
 * 3. userData: gpointer
 */
typealias OffsetChangedSlot = CFunction<(app: CPointer<GtkLevelBar>, name: String, userData: gpointer) -> Unit>