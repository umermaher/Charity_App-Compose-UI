package com.umermahar.charityapp.food.presentation.food_plan

import com.umermahar.charityapp.food.presentation.food.models.Meal
import java.time.YearMonth

data class FoodPlanState(
    val meal: Meal? = null,
    val mealType: MealType = MealType.LUNCH,
    val calendarUiState: CalendarUiState = CalendarUiState()
)

data class CalendarUiState(
    val yearMonth: YearMonth = YearMonth.now(),
    val dates: List<Date> = emptyList(),
    val startDate: Date? = null,  // NEW: Start of range
    val endDate: Date? = null     // NEW: End of range
) {

    fun updateSelection(clickedDate: Date): CalendarUiState {
        val updatedDates = if(
            clickedDate.dayOfMonth < (startDate?.dayOfMonth ?: 0)
        ) {
            // If the clicked date is before the start date, reset the range
            updateRange(clickedDate, null)
        } else if(
            clickedDate.dayOfMonth > (startDate?.dayOfMonth ?: 0) && clickedDate.dayOfMonth < (endDate?.dayOfMonth ?: 0)
            ) {
            updateRange(startDate, clickedDate)
        }else if(startDate == null) {
            // If there's no start date, set it
            updateRange(clickedDate, null)
        } else {
            // If there's a start date but no end date, set the range
            updateRange(startDate, clickedDate)
        }
        val start = updatedDates.firstOrNull { it.dateRange == DateRange.START }
        val end = updatedDates.firstOrNull { it.dateRange == DateRange.END }
        return copy(startDate = start, endDate = end, dates = updatedDates)
    }

    private fun updateRange(start: Date?, end: Date?): List<Date> {
        return if(start != null && end == null) {
            dates.map { date ->
                val isSelected = date.isCurrentMonth &&
                        date.dayOfMonth.let { day ->
                            val startDay = start.dayOfMonth
                            day == startDay
                        }

                date.copy(
                    dateRange = if (isSelected) {
                        DateRange.START
                    } else DateRange.IS_NOT_IN_RANGE
                )
            }
        } else {
            dates.map { date ->
                val isInRange = start != null && end != null && date.isCurrentMonth &&
                        date.dayOfMonth.let { day ->
                            val startDay = start.dayOfMonth
                            val endDay = end.dayOfMonth
                            day in startDay..endDay
                        }

                date.copy(
                    dateRange = if(isInRange &&
                        (date.dayOfMonth == start?.dayOfMonth)) {
                        DateRange.START
                    } else if(isInRange &&
                        (date.dayOfMonth == end?.dayOfMonth)) {
                        DateRange.END
                    } else if(isInRange) {
                        DateRange.IS_ONLY_IN_RANGE
                    } else {
                        DateRange.IS_NOT_IN_RANGE
                    }
                )
            }
        }
    }

    data class Date(
        val dayOfMonth: Int,
        val isCurrentMonth: Boolean = true,
        val dateRange: DateRange = DateRange.IS_NOT_IN_RANGE,
    ) {
        companion object {
            val Empty = Date(1, false)
        }
    }
}

enum class DateRange {
    START, END, IS_ONLY_IN_RANGE, IS_NOT_IN_RANGE
}

enum class MealType {
    LUNCH, DINNER
}

sealed interface FoodPlanActions {
    class OnMealTypeClick(val mealType: MealType) : FoodPlanActions
    data object OnBackButtonClick : FoodPlanActions
    data class OnPreviousMonthButtonClick(val yearMonth: YearMonth) : FoodPlanActions
    data class OnNextMonthButtonClick(val yearMonth: YearMonth) : FoodPlanActions
    data class OnDateClick(val date: CalendarUiState.Date) : FoodPlanActions
    data object OnBookNowButtonSwiped : FoodPlanActions
}

sealed interface FoodPlanEvent {
    data object PopBackStack : FoodPlanEvent
    data class Navigate(val route: Any) : FoodPlanEvent
}