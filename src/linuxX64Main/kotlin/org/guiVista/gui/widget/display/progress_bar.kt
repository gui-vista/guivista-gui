package org.guiVista.gui.widget.display

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guiVista.gui.widget.WidgetBase

public actual class ProgressBar(progressBarPtr: CPointer<GtkProgressBar>? = null) : WidgetBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = progressBarPtr?.reinterpret() ?: gtk_progress_bar_new()
    public val gtkProgressBarPtr: CPointer<GtkProgressBar>?
        get() = gtkWidgetPtr?.reinterpret()
    public actual var fraction: Double
        get() = gtk_progress_bar_get_fraction(gtkProgressBarPtr)
        set(value) {
            if (value in (0.0..1.0)) gtk_progress_bar_set_fraction(gtkProgressBarPtr, value)
        }
    public actual var inverted: Boolean
        get() = gtk_progress_bar_get_inverted(gtkProgressBarPtr) == TRUE
        set(value) = gtk_progress_bar_set_inverted(gtkProgressBarPtr, if (value) TRUE else FALSE)
    public actual var pulseStep: Double
        get() = gtk_progress_bar_get_pulse_step(gtkProgressBarPtr)
        set(value) {
            if (value in (0.0..1.0)) gtk_progress_bar_set_pulse_step(gtkProgressBarPtr, value)
        }
    public actual var showText: Boolean
        get() = gtk_progress_bar_get_show_text(gtkProgressBarPtr) == TRUE
        set(value) = gtk_progress_bar_set_show_text(gtkProgressBarPtr, if (value) TRUE else FALSE)
    public actual var text: String
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
    public var ellipsize: PangoEllipsizeMode
        get() = gtk_progress_bar_get_ellipsize(gtkProgressBarPtr)
        set(value) = gtk_progress_bar_set_ellipsize(gtkProgressBarPtr, value)

    public actual fun pulse() {
        gtk_progress_bar_pulse(gtkProgressBarPtr)
    }
}

public fun progressBarWidget(
    progressBarPtr: CPointer<GtkProgressBar>? = null,
    init: ProgressBar.() -> Unit
): ProgressBar {
    val progressBar = ProgressBar(progressBarPtr)
    progressBar.init()
    return progressBar
}
