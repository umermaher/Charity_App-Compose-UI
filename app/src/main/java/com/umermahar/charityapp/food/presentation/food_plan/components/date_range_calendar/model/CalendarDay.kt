package com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class DayOfWeek {
    Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
}

enum class DaySelectedStatus {
    NoSelected, Selected, NonClickable, FirstDay, LastDay, FirstLastDay
}

class CalendarDay(val value: Int, status: DaySelectedStatus) {
    var status by mutableStateOf(status)
}