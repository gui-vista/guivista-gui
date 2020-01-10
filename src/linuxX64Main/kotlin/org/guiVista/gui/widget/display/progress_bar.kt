package org.guiVista.gui.widget.display

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guiVista.gui.widget.WidgetBase

/** A widget which indicates progress visually. */
class ProgressBar(progressBarPtr: CPointer<GtkProgressBar>? = null) : WidgetBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = progressBarPtr?.reinterpret() ?: gtk_progress_bar_new()
    val gtkProgressBarPtr: CPointer<GtkProgressBar>?
        get() = gtkWidgetPtr?.reinterpret()
    /** The fraction of total work that has been completed. Default value is *0.0*. */
    var fraction: Double
        get() = gtk_progress_bar_get_fraction(gtkProgressBarPtr)
        set(value) {
            if (value in (0.0..1.0)) gtk_progress_bar_set_fraction(gtkProgressBarPtr, value)
        }
    /** Invert the direction in which the progress bar grows. Default value is *false*. */
    var inverted: Boolean
        get() = gtk_progress_bar_get_inverted(gtkProgressBarPtr) == TRUE
        set(value) = gtk_progress_bar_set_inverted(gtkProgressBarPtr, if (value) TRUE else FALSE)
    /** The fraction of total progress to move the bouncing block when pulsed. Default value is *0.1*. */
    var pulseStep: Double
        get() = gtk_progress_bar_get_pulse_step(gtkProgressBarPtr)
        set(value) {
            if (value in (0.0..1.0)) gtk_progress_bar_set_pulse_step(gtkProgressBarPtr, value)
        }
    /**
     * Sets whether the progress bar will show a text in addition to the bar itself. The shown text is either the
     * value of the [text] property, or if that is *null* the “fraction” value as a percentage. To make a progress bar
     * that is styled and sized suitably for showing text (even if the actual text is blank), set “show-text” to *true*
     * and “text” to the empty string (not *null*).
     *
     * Default value is *false*.
     */
    var showText: Boolean
        get() = gtk_progress_bar_get_show_text(gtkProgressBarPtr) == TRUE
        set(value) = gtk_progress_bar_set_show_text(gtkProgressBarPtr, if (value) TRUE else FALSE)
    /** Text to be displayed in the progress bar. Default value is *""* (an empty String). */
    var text: String
        get() = gtk_progress_bar_get_text(gtkProgressBarPtr)?.toKString() ?: ""
        set(value) = gtk_progress_bar_set_text(gtkProgressBarPtr, value)
    /**
     * The preferred place to "ellipsize" the string, if the progress bar does not have enough room to display the
     * entire string, specified as a PangoEllipsizeMode. Note that setting this property to a value other than
     * PANGO_ELLIPSIZE_NONE has the side-effect that the progress bar requests only enough space to display the
     * ellipsis ("..."). Another means to set a [progress bar's][ProgressBar] width is `gtk_widget_set_size_request()`.
     *
     * Default value is *PangoEllipsizeMode.PANGO_ELLIPSIZE_NONE*.
     */
    var ellipsize: PangoEllipsizeMode
        get() = gtk_progress_bar_get_ellipsize(gtkProgressBarPtr)
        set(value) = gtk_progress_bar_set_ellipsize(gtkProgressBarPtr, value)

    /**
     * Indicates that some progress has been made, but you don’t know how much. Causes the progress bar to enter
     * “activity mode” where a block bounces back and forth. Each call to [pulse] causes the block to move by a little
     * bit (the amount of movement per pulse is determined by [pulseStep]).
     */
    fun pulse() {
        gtk_progress_bar_pulse(gtkProgressBarPtr)
    }
}

fun progressBarWidget(progressBarPtr: CPointer<GtkProgressBar>? = null, init: ProgressBar.() -> Unit): ProgressBar {
    val progressBar = ProgressBar(progressBarPtr)
    progressBar.init()
    return progressBar
}
