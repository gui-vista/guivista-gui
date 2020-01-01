package org.guivista.core.widget.data_entry

import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guivista.core.connectGSignal
import org.guivista.core.disconnectGSignal

private const val NEXT_MATCH_SIGNAL = "next-match"
private const val PREVIOUS_MATCH_SIGNAL = "previous-match"
private const val STOP_SEARCH_SIGNAL = "stop-search"
private const val SEARCH_CHANGED_SIGNAL = "search-changed"

/** An entry which shows a search icon. */
class SearchEntry(searchEntryPtr: CPointer<GtkSearchEntry>? = null) : EntryBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = searchEntryPtr?.reinterpret() ?: gtk_search_entry_new()
    val gtkSearchEntryPtr: CPointer<GtkSearchEntry>?
        get() = gtkWidgetPtr?.reinterpret()

    /**
     * This function should be called when the top level window which contains the search entry received a key event.
     * If the entry is part of a GtkSearchBar it is preferable to call `gtk_search_bar_handle_event()` instead, which
     * will reveal the entry in addition to passing the event to this function. If the key event is handled by the
     * [SearchEntry] and starts or continues a search, *GDK_EVENT_STOP* will be returned. The caller should ensure that
     * the entry is shown in this case, and not propagate the event further.
     * @param event A key event.
     * @return *GDK_EVENT_STOP* if the key press event resulted in a search beginning or continuing. Otherwise return
     * *GDK_EVENT_PROPAGATE*.
     */
    fun handleEvent(event: CPointer<GdkEvent>): Boolean =
        gtk_search_entry_handle_event(gtkSearchEntryPtr, event) == TRUE

    /**
     * Connects the *next-match* signal to a [slot] on a [SearchEntry]. This signal is a key binding signal that is used
     * when the user initiates a move to the next match for the current search string. Applications should connect to
     * it to implement moving between matches. The default bindings for this signal is *Ctrl-g*.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectNextMatchSignal(slot: CPointer<NextMatchSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkSearchEntryPtr, signal = NEXT_MATCH_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *previous-match* signal to a [slot] on a [SearchEntry]. This signal is a key binding signal that is
     * used when the user initiates a move to the previous match for the current search string. Applications should
     * connect to it to implement moving between matches. The default bindings for this signal is *Ctrl-Shift-g*.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectPreviousMatchSignal(slot: CPointer<PreviousMatchSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkSearchEntryPtr, signal = PREVIOUS_MATCH_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *search-changed* signal to a [slot] on a [SearchEntry]. This signal is emitted with a short delay of
     * 150 milliseconds after the last change to the entry text.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectSearchChangedSignal(slot: CPointer<SearchChangedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkSearchEntryPtr, signal = SEARCH_CHANGED_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *stop-search* signal to a [slot] on a [SearchEntry]. This signal is a key binding signal that is
     * used when the user stops a search via keyboard input. Applications should connect to it to implement hiding the
     * [SearchEntry] in this case. The default bindings for this signal is *Escape*.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    fun connectStopSearchSignal(slot: CPointer<StopSearchSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkSearchEntryPtr, signal = STOP_SEARCH_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkSearchEntryPtr, handlerId)
    }
}

fun searchEntryWidget(searchEntryPtr: CPointer<GtkSearchEntry>? = null, init: SearchEntry.() -> Unit): SearchEntry {
    val searchEntry = SearchEntry(searchEntryPtr)
    searchEntry.init()
    return searchEntry
}

/**
 * The event handler for the *next-match* signal. Arguments:
 * 1. entry: CPointer<GtkSearchEntry>
 * 2. userData: gpointer
 */
typealias NextMatchSlot = CFunction<(entry: CPointer<GtkSearchEntry>, userData: gpointer) -> Unit>

/**
 * The event handler for the *previous-match* signal. Arguments:
 * 1. entry: CPointer<GtkSearchEntry>
 * 2. userData: gpointer
 */
typealias PreviousMatchSlot = CFunction<(entry: CPointer<GtkSearchEntry>, userData: gpointer) -> Unit>

/**
 * The event handler for the *search-changed* signal. Arguments:
 * 1. entry: CPointer<GtkSearchEntry>
 * 2. userData: gpointer
 */
typealias SearchChangedSlot = CFunction<(entry: CPointer<GtkSearchEntry>, userData: gpointer) -> Unit>

/**
 * The event handler for the *stop-search* signal. Arguments:
 * 1. entry: CPointer<GtkSearchEntry>
 * 2. userData: gpointer
 */
typealias StopSearchSlot = CFunction<(app: CPointer<GtkSearchEntry>, userData: gpointer) -> Unit>
