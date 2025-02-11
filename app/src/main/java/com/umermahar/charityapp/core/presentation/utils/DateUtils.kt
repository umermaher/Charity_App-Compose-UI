package com.umermahar.charityapp.core.presentation.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale

object DateUtil {

    val daysOfWeek: Array<String>
        get()  = daysOfWeekStartsWithSunday()

    private fun daysOfWeekStartsWithSunday(): Array<String> {
        // Rotate the list to start with Sunday
        val reorderedDays = listOf(DayOfWeek.SUNDAY) + DayOfWeek.entries.dropLast(1)

        return reorderedDays.map { dayOfWeek ->
            dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault()) // Use NARROW for single letters
        }.toTypedArray()
    }

    private fun daysOfWeekStartsWithMonday(): Array<String> {
        return DayOfWeek.entries.map { dayOfWeek ->
            dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault()) // Use NARROW for single letters
        }.toTypedArray()
    }
}

fun YearMonth.getDaysOfMonthStartingFromMonday(): List<LocalDate> {
    val firstDayOfMonth = LocalDate.of(year, month, 1) // Get the first day of the month
    val firstMondayOfMonth = firstDayOfMonth.with(DayOfWeek.MONDAY) // Find the first Monday on or before the 1st
    val firstDayOfNextMonth = firstDayOfMonth.plusMonths(1) // Find the first day of the next month

    return generateSequence(firstMondayOfMonth) { it.plusDays(1) } // Start from that Monday and add days one by one
        .takeWhile { it.isBefore(firstDayOfNextMonth) } // Stop when reaching the next month
        .toList() // Convert to a list
}

fun YearMonth.getDaysOfMonthStartingFromSunday(): List<LocalDate> {
    val firstDayOfMonth = LocalDate.of(year, month, 1) // Get the first day of the month
    val firstSundayOfMonth = firstDayOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)) // Find the first Sunday on or before the 1st
    val firstDayOfNextMonth = firstDayOfMonth.plusMonths(1) // Find the first day of the next month

    return generateSequence(firstSundayOfMonth) { it.plusDays(1) } // Start from that Sunday and add days one by one
        .takeWhile { it.isBefore(firstDayOfNextMonth) } // Stop when reaching the next month
        .toList() // Convert to a list
}

fun YearMonth.getDaysOfMonthStartingFromSundayToSaturday(): List<LocalDate> {
    val firstDayOfMonth = LocalDate.of(year, month, 1) // Get the first day of the current month
    val firstSundayOfMonth = firstDayOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)) // Find the first Sunday before or on the 1st
    val lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1) // Get the last day of the month

    // If the last day of the month is already a Saturday, stop there
    val endDate = if (lastDayOfMonth.dayOfWeek == DayOfWeek.SATURDAY) {
        lastDayOfMonth
    } else {
        lastDayOfMonth.with(TemporalAdjusters.next(DayOfWeek.SATURDAY)) // Get the first Saturday of next month
    }

    return generateSequence(firstSundayOfMonth) { it.plusDays(1) } // Generate dates from the first Sunday
        .takeWhile { it <= endDate } // Continue until the correct Saturday
        .toList() // Convert to a list
}

fun YearMonth.getDisplayName(): String {
    return "${month.getDisplayName(TextStyle.FULL, Locale.getDefault())} $year"
}