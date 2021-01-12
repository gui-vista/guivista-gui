package org.guiVista.gui.widget

import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.*
import org.guiVista.core.connectGSignal
import org.guiVista.core.disconnectGSignal

private const val DAY_SELECTED_SIGNAL = "day-selected"
private const val DAY_SELECTED_DOUBLE_CLICK_SIGNAL = "day-selected-double-click"
private const val MONTH_CHANGED_SIGNAL = "month-changed"
private const val NEXT_MONTH_SIGNAL = "next-month"
private const val NEXT_YEAR_SIGNAL = "next-year"
private const val PREV_MONTH_SIGNAL = "prev-month"
private const val PREV_YEAR_SIGNAL = "prev-year"

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
     * Connects the *day-selected* signal to a [slot] on a [Calendar]. The signal occurs when the user selects a day.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectDaySelectedSignal(slot: CPointer<DaySelectedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = DAY_SELECTED_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *day-selected-double-click* signal to a [slot] on a [Calendar]. The signal occurs when the user
     * double clicks a day.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectDaySelectedDoubleClickSignal(
        slot: CPointer<DaySelectedDoubleClickSlot>,
        userData: gpointer
    ): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = DAY_SELECTED_DOUBLE_CLICK_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *month-changed* signal to a [slot] on a [Calendar]. The signal occurs when the user clicks a button
     * to change the selected month on a calendar.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectMonthChangedSignal(slot: CPointer<MonthChangedSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = MONTH_CHANGED_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *next-month* signal to a [slot] on a [Calendar]. The signal occurs when the user switched to the
     * next month.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectNextMonthSignal(slot: CPointer<NextMonthSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = NEXT_MONTH_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *next-year* signal to a [slot] on a [Calendar]. The signal occurs when the user switched to the
     * next year.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectNextYearSignal(slot: CPointer<NextYearSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = NEXT_YEAR_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *prev-month* signal to a [slot] on a [Calendar]. The signal occurs when the user switched to the
     * previous month.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectPrevMonthSignal(slot: CPointer<PrevMonthSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = PREV_MONTH_SIGNAL, slot = slot, data = userData)

    /**
     * Connects the *prev-year* signal to a [slot] on a [Calendar]. The signal occurs when user switched to the
     * previous year.
     * @param slot The event handler for the signal.
     * @param userData User data to pass through to the [slot].
     */
    public fun connectPrevYearSignal(slot: CPointer<PrevYearSlot>, userData: gpointer): ULong =
        connectGSignal(obj = gtkCalendarPtr, signal = PREV_YEAR_SIGNAL, slot = slot, data = userData)

    override fun disconnectSignal(handlerId: ULong) {
        super.disconnectSignal(handlerId)
        disconnectGSignal(gtkCalendarPtr, handlerId)
    }
}

/**
 * The event handler for the *day-selected* signal. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias DaySelectedSlot = CFunction<(calendar: CPointer<GtkCalendar>, userData: gpointer) -> Unit>

/**
 * The event handler for the *day-selected-double-click* signal. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias DaySelectedDoubleClickSlot = CFunction<(calendar: CPointer<GtkCalendar>, userData: gpointer) -> Unit>

/**
 * The event handler for the *month-changed* signal. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias MonthChangedSlot = CFunction<(calendar: CPointer<GtkCalendar>, userData: gpointer) -> Unit>

/**
 * The event handler for the *next-month* signal. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias NextMonthSlot = CFunction<(calendar: CPointer<GtkCalendar>, userData: gpointer) -> Unit>

/**
 * The event handler for the *next-year* signal. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias NextYearSlot = CFunction<(calendar: CPointer<GtkCalendar>, userData: gpointer) -> Unit>

/**
 * The event handler for the *prev-month* signal. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias PrevMonthSlot = CFunction<(calendar: CPointer<GtkCalendar>, userData: gpointer) -> Unit>

/**
 * The event handler for the *prev-year* signal. Arguments:
 * 1. calendar: CPointer<GtkCalendar>
 * 2. userData: gpointer
 */
public typealias PrevYearSlot = CFunction<(calendar: CPointer<GtkCalendar>, userData: gpointer) -> Unit>

public fun calendarWidget(ptr: CPointer<GtkCalendar>? = null, init: Calendar.() -> Unit = {}): Calendar {
    val result = Calendar(ptr)
    result.init()
    return result
}
