package org.guiVista.gui.widget.dataEntry

import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.reinterpret
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

public actual class SearchEntry(searchEntryPtr: CPointer<GtkSearchEntry>? = null) : EntryBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = searchEntryPtr?.reinterpret() ?: gtk_search_entry_new()
    public val gtkSearchEntryPtr: CPointer<GtkSearchEntry>?
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
    public fun handleEvent(event: CPointer<GdkEvent>): Boolean =
        gtk_search_entry_handle_event(gtkSearchEntryPtr, event) == TRUE

    /**
     * Connects the *next-match* event to a [handler] on a [SearchEntry]. This event is a key binding event that is used
     * when the user initiates a move to the next match for the current search String. Applications should connect to
     * it to implement moving between matches. The default bindings for this event is *Ctrl-g*.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectNextMatchEvent(handler: CPointer<NextMatchHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkSearchEntryPtr, signal = SearchEntryEvent.nextMatch, slot = handler,
            data = userData).toULong()

    /**
     * Connects the *previous-match* event to a [handler] on a [SearchEntry]. This event is a key binding event that is
     * used when the user initiates a move to the previous match for the current search String. Applications should
     * connect to it to implement moving between matches. The default bindings for this event is *Ctrl-Shift-g*.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectPreviousMatchEvent(handler: CPointer<PreviousMatchHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkSearchEntryPtr, signal = SearchEntryEvent.previousMatch, slot = handler,
            data = userData).toULong()

    /**
     * Connects the *search-changed* event to a [handler] on a [SearchEntry]. This event is emitted with a short delay
     * of 150 milliseconds after the last change to the entry text.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectSearchChangedEvent(handler: CPointer<SearchChangedHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkSearchEntryPtr, signal = SearchEntryEvent.searchChanged, slot = handler,
            data = userData).toULong()

    /**
     * Connects the *stop-search* event to a [handler] on a [SearchEntry]. This event is a key binding event that is
     * used when the user stops a search via keyboard input. Applications should connect to it to implement hiding the
     * [SearchEntry] in this case. The default bindings for this event is *Escape*.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectStopSearchEvent(handler: CPointer<StopSearchHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkSearchEntryPtr, signal = SearchEntryEvent.stopSearch, slot = handler,
            data = userData).toULong()

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkSearchEntryPtr, handlerId.toUInt())
    }
}

public fun searchEntryWidget(
    searchEntryPtr: CPointer<GtkSearchEntry>? = null,
    init: SearchEntry.() -> Unit = {}
): SearchEntry {
    val searchEntry = SearchEntry(searchEntryPtr)
    searchEntry.init()
    return searchEntry
}

/**
 * The event handler for the *next-match* event. Arguments:
 * 1. entry: CPointer<GtkSearchEntry>
 * 2. userData: gpointer
 */
public typealias NextMatchHandler = CFunction<(entry: CPointer<GtkSearchEntry>, userData: gpointer) -> Unit>

/**
 * The event handler for the *previous-match* event. Arguments:
 * 1. entry: CPointer<GtkSearchEntry>
 * 2. userData: gpointer
 */
public typealias PreviousMatchHandler = CFunction<(entry: CPointer<GtkSearchEntry>, userData: gpointer) -> Unit>

/**
 * The event handler for the *search-changed* event. Arguments:
 * 1. entry: CPointer<GtkSearchEntry>
 * 2. userData: gpointer
 */
public typealias SearchChangedHandler = CFunction<(entry: CPointer<GtkSearchEntry>, userData: gpointer) -> Unit>

/**
 * The event handler for the *stop-search* event. Arguments:
 * 1. entry: CPointer<GtkSearchEntry>
 * 2. userData: gpointer
 */
public typealias StopSearchHandler = CFunction<(app: CPointer<GtkSearchEntry>, userData: gpointer) -> Unit>
