package org.guiVista.gui.widget.textEditor

import glib2.FALSE
import glib2.TRUE
import glib2.gboolean
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal
import org.guiVista.gui.layout.Container
import org.guiVista.gui.text.TextBuffer
import org.guiVista.gui.text.textBuffer
import org.guiVista.gui.widget.WidgetBase

private const val BACKSPACE_SIGNAL = "backspace"
private const val COPY_CLIPBOARD_SIGNAL = "copy-clipboard"
private const val CUT_CLIPBOARD_SIGNAL = "cut-clipboard"
private const val INSERT_AT_CURSOR_SIGNAL = "insert-at-cursor"
private const val PASTE_CLIPBOARD_SIGNAL = "paste-clipboard"
private const val POPULATE_POPUP_SIGNAL = "populate-popup"
private const val SELECT_ALL_SIGNAL = "select-all"

public actual class TextView(
    textViewPtr: CPointer<GtkTextView>? = null,
    textBufferPtr: CPointer<GtkTextBuffer>? = null
) : Container {
    override val gtkWidgetPtr: CPointer<GtkWidget>? =
        if (textViewPtr != null && textBufferPtr == null) textViewPtr.reinterpret()
        else if (textBufferPtr != null && textViewPtr == null) gtk_text_view_new_with_buffer(textBufferPtr)
        else gtk_text_view_new()
    public val gtkTextViewPtr: CPointer<GtkTextView>?
        get() = gtkWidgetPtr?.reinterpret()
    public actual var buffer: TextBuffer
        get() = TextBuffer(gtk_text_view_get_buffer(gtkTextViewPtr))
        set(value) = gtk_text_view_set_buffer(gtkTextViewPtr, value.gtkTextBufferPtr)
    public actual var editable: Boolean
        get() = gtk_text_view_get_editable(gtkTextViewPtr) == TRUE
        set(value) = gtk_text_view_set_editable(gtkTextViewPtr, if (value) TRUE else FALSE)
    public actual var cursorVisible: Boolean
        get() = gtk_text_view_get_cursor_visible(gtkTextViewPtr) == TRUE
        set(value) = gtk_text_view_set_cursor_visible(gtkTextViewPtr, if (value) TRUE else FALSE)
    public actual var overwrite: Boolean
        get() = gtk_text_view_get_overwrite(gtkTextViewPtr) == TRUE
        set(value) = gtk_text_view_set_overwrite(gtkTextViewPtr, if (value) TRUE else FALSE)
    public actual var pixelsAboveLines: Int
        get() = gtk_text_view_get_pixels_above_lines(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The pixelsAboveLines value must be >= 0.")
            gtk_text_view_set_pixels_above_lines(gtkTextViewPtr, value)
        }
    public actual var pixelsBelowLines: Int
        get() = gtk_text_view_get_pixels_below_lines(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The pixelsBelowLines value must be >= 0.")
            gtk_text_view_set_pixels_below_lines(gtkTextViewPtr, value)
        }
    public actual var pixelsInsideWrap: Int
        get() = gtk_text_view_get_pixels_inside_wrap(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The pixelsInsideWrap value must be >= 0.")
            gtk_text_view_set_pixels_inside_wrap(gtkTextViewPtr, value)
        }
    public actual var leftMargin: Int
        get() = gtk_text_view_get_left_margin(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The leftMargin value must be >= 0.")
            gtk_text_view_set_left_margin(gtkTextViewPtr, value)
        }
    public actual var rightMargin: Int
        get() = gtk_text_view_get_right_margin(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The rightMargin value must be >= 0.")
            gtk_text_view_set_right_margin(gtkTextViewPtr, value)
        }
    public actual var topMargin: Int
        get() = gtk_text_view_get_top_margin(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The topMargin value must be >= 0.")
            gtk_text_view_set_top_margin(gtkTextViewPtr, value)
        }
    public actual var bottomMargin: Int
        get() = gtk_text_view_get_bottom_margin(gtkTextViewPtr)
        set(value) {
            if (value < 0) throw IllegalArgumentException("The bottomMargin value must be >= 0.")
            gtk_text_view_set_bottom_margin(gtkTextViewPtr, value)
        }
    public actual var indent: Int
        get() = gtk_text_view_get_indent(gtkTextViewPtr)
        set(value) = gtk_text_view_set_indent(gtkTextViewPtr, value)
    public actual var acceptsTab: Boolean
        get() = gtk_text_view_get_accepts_tab(gtkTextViewPtr) == TRUE
        set(value) = gtk_text_view_set_accepts_tab(gtkTextViewPtr, if (value) TRUE else FALSE)
    public actual var monospace: Boolean
        get() = gtk_text_view_get_monospace(gtkTextViewPtr) == TRUE
        set(value) = gtk_text_view_set_monospace(gtkTextViewPtr, if (value) TRUE else FALSE)

    public actual fun placeCursorOnscreen(): Boolean = gtk_text_view_place_cursor_onscreen(gtkTextViewPtr) == TRUE

    public actual fun moveChild(child: WidgetBase, xPos: Int, yPos: Int) {
        gtk_text_view_move_child(text_view = gtkTextViewPtr, child = child.gtkWidgetPtr, xpos = xPos, ypos = yPos)
    }

    public actual fun resetCursorBlink() {
        gtk_text_view_reset_cursor_blink(gtkTextViewPtr)
    }

    /**
     * Connects the *backspace* signal to a [slot] on a [TextView]. This signal occurs when the user asks for it. The
     * default bindings for this signal are **Backspace** and **Shift-Backspace**.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectBackspaceSignal(slot: CPointer<BackspaceSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkTextViewPtr, signal = BACKSPACE_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *copy-clipboard* signal to a [slot] on a [TextView]. This signal occurs when a selection is copied
     * to the clipboard. The default bindings for this signal are **Ctrl-c**, and **Ctrl-Insert**.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectCopyClipboardSignal(slot: CPointer<CopyClipboardSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkTextViewPtr, signal = COPY_CLIPBOARD_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *cut-clipboard* signal to a [slot] on a [TextView]. This signal occurs when a selection is cut
     * from the clipboard. The default bindings for this signal are **Ctrl-x**, and **Shift-Delete**.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectCutClipboardSignal(slot: CPointer<CutClipboardSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkTextViewPtr, signal = CUT_CLIPBOARD_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *insert-at-cursor* signal to a [slot] on a [TextView]. This signal occurs when the user initiates
     * the insertion of a fixed string at the cursor. This signal has no default bindings.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectInsertAtCursorSignal(slot: CPointer<InsertAtCursorSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkTextViewPtr, signal = INSERT_AT_CURSOR_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *paste-clipboard* signal to a [slot] on a [TextView]. This signal occurs when a selection is
     * pasted from the clipboard. The default bindings for this signal are **Ctrl-v**, and **Shift-Insert**.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectPasteClipboardSignal(slot: CPointer<PasteClipboardSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkTextViewPtr, signal = PASTE_CLIPBOARD_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *populate-popup* signal to a [slot] on a [TextView]. This signal occurs before showing the context
     * menu of the text view. If you need to add items to the context menu, connect to this signal and append your
     * items to the popup, which will be a GtkMenu in this case. If **populate-all** is *true* then this signal will
     * also be emitted to populate touch popups. In this case, popup will be a different container, e.g. a GtkToolbar.
     *
     * The signal handler should **NOT** make assumptions about the type of widget, but check whether popup is a
     * GtkMenu, or GtkToolbar, or another kind of [container][Container].
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectPopulatePopupSignal(slot: CPointer<PopuplatePopupSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkTextViewPtr, signal = POPULATE_POPUP_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *select-all* signal to a [slot] on a [TextView]. This signal occurs when selecting or unselecting
     * the complete contents of the text view. The default bindings for this signal are **Ctrl-a** and
     * **Ctrl-forward_slash** for selecting, and **Shift-Ctrl-a** and **Ctrl-\** for unselecting.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectSelectAllSignal(slot: CPointer<SelectAllSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkTextViewPtr, signal = SELECT_ALL_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkTextViewPtr, handlerId)
    }
}

public fun textViewWidget(
    textViewPtr: CPointer<GtkTextView>? = null,
    textBufferPtr: CPointer<GtkTextBuffer>? = null,
    init: TextView.() -> Unit = {}
): TextView {
    val textView = TextView(textViewPtr, textBufferPtr)
    textView.init()
    return textView
}

/**
 * The event handler for the *backspace* signal. Arguments:
 * 1. textView: CPointer<GtkTextView>
 * 2. userData: gpointer
 */
public typealias BackspaceSlot = CFunction<(textView: CPointer<GtkTextView>?, userData: gpointer) -> Unit>

/**
 * The event handler for the *copy-clipboard* signal. Arguments:
 * 1. textView: CPointer<GtkTextView>
 * 2. userData: gpointer
 */
public typealias CopyClipboardSlot = CFunction<(textView: CPointer<GtkTextView>?, userData: gpointer) -> Unit>

/**
 * The event handler for the *cut-clipboard* signal. Arguments:
 * 1. textView: CPointer<GtkTextView>
 * 2. userData: gpointer
 */
public typealias CutClipboardSlot = CFunction<(textView: CPointer<GtkTextView>?, userData: gpointer) -> Unit>

/**
 * The event handler for the *insert-at-cursor* signal. Arguments:
 * 1. textView: CPointer<GtkTextView>
 * 2. string: CPointer<ByteVar>
 * 3. userData: gpointer
 */
public typealias InsertAtCursorSlot = CFunction<(
    textView: CPointer<GtkTextView>?,
    string: CPointer<ByteVar>,
    userData: gpointer
) -> Unit>

/**
 * The event handler for the *paste-clipboard* signal. Arguments:
 * 1. textView: CPointer<GtkTextView>
 * 2. userData: gpointer
 */
public typealias PasteClipboardSlot = CFunction<(textView: CPointer<GtkTextView>?, userData: gpointer) -> Unit>

/**
 * The event handler for the *populate-popup* signal. Arguments:
 * 1. textView: CPointer<GtkTextView>
 * 2. widget: CPointer<GtkWidget>
 * 3. userData: gpointer
 */
public typealias PopuplatePopupSlot = CFunction<(
    textView: CPointer<GtkTextView>?,
    widget: CPointer<GtkWidget>,
    userData: gpointer
) -> Unit>

/**
 * The event handler for the *select-all* signal. Arguments:
 * 1. textView: CPointer<GtkTextView>
 * 2. select: gboolean
 * 3. userData: gpointer
 */
public typealias SelectAllSlot = CFunction<(
    textView: CPointer<GtkTextView>?,
    select: gboolean,
    userData: gpointer
) -> Unit>
