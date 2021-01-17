package org.guiVista.gui.widget

import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.*
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

/** Displays a calendar, and allows the user to select a date. */
public actual class Calendar(ptr: CPointer<GtkCalendar>? = null) : WidgetBase {
    public val gtkCalendarPtr: CPointer<GtkCalendar>? = ptr ?: gtk_calendar_new()?.reinterpret()
    override val gtkWidgetPtr: CPointer<GtkWidget>?
        get() = gtkCalendarPtr?.reinterpret()

    public actual var detailWidthChars: Int
        get() = gtk_calendar_get_detail_width_chars(gtkCalendarPtr)
        set(value) = gtk_calendar_set_detail_width_chars(gtkCalendarPtr, value)

    public actual var detailHeightRows: Int
        get() = gtk_calendar_get_detail_height_rows(gtkCalendarPtr)
        set(value) = gtk_calendar_set_detail_height_rows(gtkCalendarPtr, value)

    public actual fun selectMonth(month: UInt, year: UInt) {
        gtk_calendar_select_month(calendar = gtkCalendarPtr, month = month, year = year)
    }

    public actual fun selectDay(day: UInt) {
        gtk_calendar_select_day(gtkCalendarPtr, day)
    }

    public actual fun markDay(day: UInt) {
        gtk_calendar_mark_day(gtkCalendarPtr, day)
    }

    public actual fun unmarkDay(day: UInt) {
        gtk_calendar_unmark_day(gtkCalendarPtr, day)
    }

    public actual fun fetchDayIsMarked(day: UInt): Boolean = gtk_calendar_get_day_is_marked(gtkCalendarPtr, day) == TRUE

    public actual fun fetchDate(): Triple<UInt, UInt, UInt> = memScoped {
        val year = alloc<UIntVar>()
        val month = alloc<UIntVar>()
        val day = alloc<UIntVar>()
        gtk_calendar_get_date(calendar = gtkCalendarPtr, year = year.ptr, month = month.ptr, day = day.ptr)
        Triple(year.value, month.value, day.value)
    }

    /**
     * Connects the *day-selected* event to a [handler] on a [Calendar]. The event occurs when the user selects a day.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectDaySelectedEvent(handler: CPointer<DaySelectedHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = CalendarEvent.daySelected, slot = handler,
            data = userData).toULong()

    /**
     * Connects the *day-selected-double-click* event to a [handler] on a [Calendar]. The event occurs when the user
     * double clicks a day.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectDaySelectedDoubleClickEvent(
        handler: CPointer<DaySelectedDoubleClickHandler>,
        userData: gpointer
    ): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = CalendarEvent.daySelectedDoubleClick, slot = handler,
            data = userData).toULong()

    /**
     * Connects the *month-changed* event to a [handler] on a [Calendar]. The event occurs when the user clicks a button
     * to change the selected month on a calendar.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectMonthChangedEvent(handler: CPointer<MonthChangedHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = CalendarEvent.monthChanged, slot = handler,
            data = userData).toULong()

    /**
     * Connects the *next-month* event to a [handler] on a [Calendar]. The event occurs when the user switched to the
     * next month.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectNextMonthEvent(handler: CPointer<NextMonthHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = CalendarEvent.nextMonth, slot = handler,
            data = userData).toULong()

    /**
     * Connects the *next-year* event to a [handler] on a [Calendar]. The event occurs when the user switched to the
     * next year.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectNextYearEvent(handler: CPointer<NextYearHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = CalendarEvent.nextYear, slot = handler, data = userData).toULong()

    /**
     * Connects the *prev-month* event to a [handler] on a [Calendar]. The event occurs when the user switched to the
     * previous month.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectPrevMonthEvent(handler: CPointer<PrevMonthHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = CalendarEvent.prevMonth, slot = handler,
            data = userData).toULong()

    /**
     * Connects the *prev-year* event to a [handler] on a [Calendar]. The event occurs when user switched to the
     * previous year.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectPrevYearEvent(handler: CPointer<PrevYearHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = CalendarEvent.prevYear, slot = handler, data = userData).toULong()

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkCalendarPtr, handlerId.toUInt())
    }
}

/**
 * The event handler for the *day-selected* event. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias DaySelectedHandler = CFunction<(calendar: CPointer<GtkCalendar>, userData: gpointer) -> Unit>

/**
 * The event handler for the *day-selected-double-click* event. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias DaySelectedDoubleClickHandler = CFunction<(
    calendar: CPointer<GtkCalendar>,
    userData: gpointer
) -> Unit>

/**
 * The event handler for the *month-changed* event. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias MonthChangedHandler = CFunction<(calendar: CPointer<GtkCalendar>, userData: gpointer) -> Unit>

/**
 * The event handler for the *next-month* event. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias NextMonthHandler = CFunction<(calendar: CPointer<GtkCalendar>, userData: gpointer) -> Unit>

/**
 * The event handler for the *next-year* event. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias NextYearHandler = CFunction<(calendar: CPointer<GtkCalendar>, userData: gpointer) -> Unit>

/**
 * The event handler for the *prev-month* event. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias PrevMonthHandler = CFunction<(calendar: CPointer<GtkCalendar>, userData: gpointer) -> Unit>

/**
 * The event handler for the *prev-year* event. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias PrevYearHandler = CFunction<(calendar: CPointer<GtkCalendar>, userData: gpointer) -> Unit>

public fun calendarWidget(ptr: CPointer<GtkCalendar>? = null, init: Calendar.() -> Unit = {}): Calendar {
    val result = Calendar(ptr)
    result.init()
    return result
}
