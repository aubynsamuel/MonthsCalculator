package com.veivek.monthscalculator.utils

import java.time.YearMonth

fun calculateMonthsBetween(start: YearMonth, end: YearMonth): Int {
    val (earlier, later) = if (start.isBefore(end)) start to end else end to start

    val yearDiff = later.year - earlier.year
    val monthDiff = later.monthValue - earlier.monthValue

    // Total months inclusive (add 1 to include both start and end months)
    return yearDiff * 12 + monthDiff + 1
}
