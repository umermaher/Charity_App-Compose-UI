package com.umermahar.charityapp.food.presentation.food_plan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umermahar.charityapp.BillingDetailScreen
import com.umermahar.charityapp.core.presentation.utils.getDaysOfMonthStartingFromSundayToSaturday
import com.umermahar.charityapp.food.presentation.food.models.Meal
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class FoodPlanViewModel: ViewModel() {

    private val _state = MutableStateFlow(FoodPlanState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<FoodPlanEvent>()
    val event = eventChannel.receiveAsFlow()

    private val _swipeComplete = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            _state.update {
                val dates = getDates(it.calendarUiState.yearMonth)
                it.copy(
                    calendarUiState = it.calendarUiState.copy(
                        dates = dates,
                        startDate = dates.firstOrNull { date ->
                            date.dayOfMonth == LocalDate.now().dayOfMonth && date.isCurrentMonth
                        },
                    ),
                )
            }
        }
    }

    fun setMeal(meal: Meal) {
        _state.update { it.copy(meal = meal) }
    }

    fun onAction(action: FoodPlanActions) {
        when (action) {
            FoodPlanActions.OnBackButtonClick -> viewModelScope.launch {
                eventChannel.send(
                    FoodPlanEvent.PopBackStack
                )
            }

            is FoodPlanActions.OnMealTypeClick -> {
                _state.update {
                    it.copy(mealType = action.mealType)
                }
            }

            is FoodPlanActions.OnDateClick -> {
                if(
                    action.date.dayOfMonth == state.value.calendarUiState.startDate?.dayOfMonth ||
                    action.date.dayOfMonth == state.value.calendarUiState.endDate?.dayOfMonth
                    ) return
                _state.update {
                    it.copy(
                        calendarUiState = it.calendarUiState.updateSelection(action.date)
                    )
                }
            }
            is FoodPlanActions.OnNextMonthButtonClick -> toNextMonth(action.yearMonth)
            is FoodPlanActions.OnPreviousMonthButtonClick -> toPreviousMonth(action.yearMonth)
            FoodPlanActions.OnBookNowButtonSwiped -> onSwipeComplete()
        }
    }

    private fun onSwipeComplete() {
        if (_swipeComplete.value) return // Prevent multiple triggers

        _swipeComplete.value = true
        viewModelScope.launch {
            _state.value.meal?.let { meal ->
                eventChannel.send(
                    FoodPlanEvent.Navigate(
                        BillingDetailScreen(meal = meal)
                    )
                )
            }

            delay(500) // Debounce for 500ms (adjust as needed)
            _swipeComplete.value = false // Reset after debounce time
        }
    }

    private fun toNextMonth(nextMonth: YearMonth) {
        _state.update {
            it.copy(
                calendarUiState = CalendarUiState(
                    yearMonth = nextMonth,
                    dates = getDates(nextMonth)
                )
            )
        }
    }

    private fun toPreviousMonth(prevMonth: YearMonth) {
        _state.update {
            it.copy(
                calendarUiState = CalendarUiState(
                    yearMonth = prevMonth,
                    dates = getDates(prevMonth)
                )
            )
        }
    }

    private fun getDates(yearMonth: YearMonth): List<CalendarUiState.Date> {
        return yearMonth.getDaysOfMonthStartingFromSundayToSaturday()
            .map { date ->
                CalendarUiState.Date(
                    dayOfMonth = date.dayOfMonth,
                    isCurrentMonth = date.monthValue == yearMonth.monthValue,
                    dateRange = if(date.dayOfMonth == LocalDate.now().dayOfMonth && date.monthValue == yearMonth.monthValue) {
                        DateRange.START
                    } else {
                        DateRange.IS_NOT_IN_RANGE
                    }
                )
            }
    }
}