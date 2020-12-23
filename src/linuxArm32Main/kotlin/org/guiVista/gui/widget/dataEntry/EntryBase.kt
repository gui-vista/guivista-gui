package org.guiVista.gui.widget.dataEntry

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKString
import org.guiVista.gui.widget.WidgetBase

public actual interface EntryBase : WidgetBase {
    public val gtkEntryPtr: CPointer<GtkEntry>?
        get() = gtkWidgetPtr?.reinterpret()

    /**
     * Whether to activate the default widget (such as the default button in a dialog) when Enter is pressed. Default
     * value is *false*.
     */
    public var activatesDefault: Boolean
        get() = gtk_entry_get_activates_default(gtkEntryPtr) == TRUE
        set(value) = gtk_entry_set_activates_default(gtkEntryPtr, if (value) TRUE else FALSE)

    /** If *false* then then the outside bevel is removed from the widget. */
    public var hasFrame: Boolean
        get() = gtk_entry_get_has_frame(gtkEntryPtr) == TRUE
        set(value) = gtk_entry_set_has_frame(gtkEntryPtr, if (value) TRUE else FALSE)

    /** Additional hints (beyond [inputPurpose]) that allow input methods to fine tune their behaviour. */
    public var inputHints: UInt
        get() = gtk_entry_get_input_hints(gtkEntryPtr)
        set(value) = gtk_entry_set_input_hints(gtkEntryPtr, value)

    /**
     * The purpose of this text field. This property can be used by on-screen keyboards, and other input methods to
     * adjust their behaviour. Note that setting the purpose to `GTK_INPUT_PURPOSE_PASSWORD` or `GTK_INPUT_PURPOSE_PIN`
     * is independent from setting `visibility`. Default value is [GtkInputPurpose.GTK_INPUT_PURPOSE_FREE_FORM].
     */
    public var inputPurpose: GtkInputPurpose
        get() = gtk_entry_get_input_purpose(gtkEntryPtr)
        set(value) = gtk_entry_set_input_purpose(gtkEntryPtr, value)

    /**
     * The invisible character is used when masking entry contents (in **password mode**). When it is not explicitly
     * set with the [invisibleChar] property, GTK determines the character to use from a list of possible candidates,
     * depending on availability in the current font. Default value is *.
     *
     * This style property allows the theme to prepend a character to the list of candidates.
     */
    public var invisibleChar: Char
        get() = gtk_entry_get_invisible_char(gtkEntryPtr).toInt().toChar()
        set(value) = gtk_entry_set_invisible_char(gtkEntryPtr, value.toInt().toUInt())

    /** If text is overwritten when typing in the GtkEntry. Default value is *false*. */
    public var overwriteMode: Boolean
        get() = gtk_entry_get_overwrite_mode(gtkEntryPtr) == TRUE
        set(value) = gtk_entry_set_overwrite_mode(gtkEntryPtr, if (value) TRUE else FALSE)

    /** The current fraction of the task that's been completed. Default value is *0*. */
    public var progressFraction: Double
        get() = gtk_entry_get_progress_fraction(gtkEntryPtr)
        set(value) {
            if (value !in (0.0..1.0)) {
                throw IllegalArgumentException("The value for progressFraction must be between 0.0 to 1.0")
            }
            gtk_entry_set_progress_fraction(gtkEntryPtr, value)
        }

    /**
     * The fraction of total entry width to move the progress bouncing block for each call to [progressPulse]. Default
     * value is *0.1*.
     */
    public var progressPulseStep: Double
        get() = gtk_entry_get_progress_pulse_step(gtkEntryPtr)
        set(value) {
            if (value !in (0.0..1.0)) {
                throw IllegalArgumentException("The value for progressPulseStep must be between 0.0 to 1.0")
            }
            gtk_entry_set_progress_pulse_step(gtkEntryPtr, value)
        }

    /** The length of the text in the widget. Default value is *0*. */
    public val textLength: UShort
        get() = gtk_entry_get_text_length(gtkEntryPtr)

    /**
     * When *false* the widget displays the **invisible char** instead of the actual text (password mode). Default
     * value is *true*.
     */
    public var visibility: Boolean
        get() = gtk_entry_get_visibility(gtkEntryPtr) == TRUE
        set(value) = gtk_entry_set_visibility(gtkEntryPtr, if (value) TRUE else FALSE)

    /**
     * The alignment for the contents of the entry. This controls the horizontal positioning of the contents when the
     * displayed text is shorter than the width of the entry.
     *
     * The horizontal alignment from *0* (left) to *1* (right). Reversed for RTL layouts.
     */
    public var alignment: Float
        get() = gtk_entry_get_alignment(gtkEntryPtr)
        set(value) = gtk_entry_set_alignment(gtkEntryPtr, value)

    /** String contents in the entry. Default value is *""* (an empty String). */
    public var text: String
        set(value) = gtk_entry_set_text(gtkEntryPtr, value)
        get() = gtk_entry_get_text(gtkEntryPtr)?.toKString() ?: ""

    /** Placeholder text to use when [text] is empty. Default value is *""* (an empty String). */
    public var placeholderText: String
        set(value) = gtk_entry_set_placeholder_text(gtkEntryPtr, value)
        get() = gtk_entry_get_placeholder_text(gtkEntryPtr)?.toKString() ?: ""

    /** Maximum number of characters for this entry. Zero if no maximum. Default value is *0*. */
    public var maxLength: Int
        set(value) = gtk_entry_set_max_length(gtkEntryPtr, value)
        get() = gtk_entry_get_max_length(gtkEntryPtr)

    /**
     * The desired maximum width of the [Entry] in characters. If this property is set to *-1* the width will be
     * calculated automatically. Default value is *-1*.
     */
    public var maxWidthChars: Int
        set(value) {
            if (value >= -1) gtk_entry_set_max_width_chars(gtkEntryPtr, value)
        }
        get() = gtk_entry_get_max_width_chars(gtkEntryPtr)

    /** Number of characters to leave space for in the [Entry]. Default value is *-1*. */
    public var widthChars: Int
        set(value) {
            if (value >= -1) gtk_entry_set_width_chars(gtkEntryPtr, value)
        }
        get() = gtk_entry_get_width_chars(gtkEntryPtr)

    /**
     * Indicates that some progress is made but you don’t know how much. Causes the entry’s progress indicator to enter
     * **activity mode** where a block bounces back and forth. Each call to [progressPulse] causes the block to move by
     * a little bit (the amount of movement per pulse is determined by [progressPulseStep]).
     */
    public fun progressPulse() {
        gtk_entry_progress_pulse(gtkEntryPtr)
    }

    /**
     * Unsets the invisible char previously set with [invisibleChar] so that the default invisible char is used again.
     */
    public fun unsetInvisibleChar() {
        gtk_entry_unset_invisible_char(gtkEntryPtr)
    }

    /**
     * Sets whether the icon is activatable.
     * @param iconPos Icon position.
     * @param activatable If *true* then the icon should be activatable.
     */
    public fun changeIconActivatable(iconPos: GtkEntryIconPosition, activatable: Boolean) {
        gtk_entry_set_icon_activatable(entry = gtkEntryPtr, icon_pos = iconPos,
            activatable = if (activatable) TRUE else FALSE)
    }

    /**
     * Returns whether the icon is activatable.
     * @return A value of *true* if the icon is activatable.
     */
    public fun fetchIconActivatable(iconPos: GtkEntryIconPosition): Boolean =
        gtk_entry_get_icon_activatable(gtkEntryPtr, iconPos) == TRUE

    /**
     * Causes entry to have keyboard focus. It behaves like [grabFocus], except that it doesn't select the contents of
     * the entry. You only want to call this on some special entries, which the user usually doesn't want to replace all
     * text in such as **search-as-you-type** entries.
     */
    public fun grabFocusWithoutSelecting() {
        gtk_entry_grab_focus_without_selecting(gtkEntryPtr)
    }
}
