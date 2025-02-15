package com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar

import com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.model.CalendarMonth
import com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.model.DayOfWeek

object DatesUtils {

    val year: CalendarYear = getRemainingMonths()

    private fun getRemainingMonths(): List<CalendarMonth> {
        val calendar = java.util.Calendar.getInstance()
        val currentMonth = calendar.get(java.util.Calendar.MONTH) // 0-based (Jan = 0, Dec = 11)
        val year = calendar.get(java.util.Calendar.YEAR)

        val months = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

        val daysInMonth = listOf(31, if (isLeapYear(year)) 29 else 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

        val remainingMonths = mutableListOf<CalendarMonth>()

        for (month in currentMonth..11) {
            calendar.set(java.util.Calendar.MONTH, month)
            calendar.set(java.util.Calendar.DAY_OF_MONTH, 1) // Set to first day of the month

            val startDayIndex = calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1 // Convert to 0-based index
            val startDayOfWeek = DayOfWeek.entries[startDayIndex]

            remainingMonths.add(
                CalendarMonth(
                    name = months[month],
                    year = year.toString(),
                    numDays = daysInMonth[month],
                    monthNumber = month + 1, // Convert to 1-based index
                    startDayOfWeek = startDayOfWeek
                )
            )
        }

        return remainingMonths
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
}

typealias CalendarYear = List<CalendarMonth>