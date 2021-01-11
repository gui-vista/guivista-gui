package org.guiVista.gui.widget

/** Displays a calendar, and allows the user to select a date. */
public expect class Calendar : WidgetBase {
    /** Width of a detail cell in characters. A value of *0* allows any width. Default value is *0*. */
    public var detailWidthChars: Int

    /** Height of a detail cell, in rows. A value of 0 allows any width. Default value is *0*. */
    public var detailHeightRows: Int

    /**
     * Shifts the calendar to a different month.
     * @param month A month number between *0*, and *11*.
     * @param year The year the month is in.
     */
    public fun selectMonth(month: UInt, year: UInt)

    /**
     * Selects a day from the current month.
     * @param day The day number between *1*, and *31*, or *0* to unselect the currently selected day.
     */
    public fun selectDay(day: UInt = 0u)

    /**
     * Places a visual marker on a particular day.
     * @param day The day number to mark between *1*, and *31*.
     */
    public fun markDay(day: UInt)

    /**
     * Removes the visual marker from a particular day.
     * @param day The day number to unmark between *1*, and *31*.
     */
    public fun unmarkDay(day: UInt)

    /**
     * Returns if the day of the calendar is already marked.
     * @param day The day number between *1*, and *31*.
     * @return Whether the day is marked.
     */
    public fun isMarked(day: UInt): Boolean

    /**
     * Obtains the selected date from a [Calendar].
     * @return A Triple containing the following:
     * 1. Year - a decimal number (e.g. 2011)
     * 2. Month - a month number between 0 and 11
     * 3. Day - a number between 1 and 31
     */
    public fun fetchDate(): Triple<UInt, UInt, UInt>
}
