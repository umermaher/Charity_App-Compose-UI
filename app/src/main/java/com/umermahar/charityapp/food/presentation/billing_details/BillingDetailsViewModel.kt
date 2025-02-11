package com.umermahar.charityapp.food.presentation.billing_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umermahar.charityapp.BillingDetailScreen
import com.umermahar.charityapp.food.presentation.food.models.Meal
import com.umermahar.charityapp.food.presentation.food_plan.FoodPlanEvent
import com.umermahar.charityapp.food.presentation.food_plan.FoodPlanState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BillingDetailsViewModel: ViewModel() {
    private val _state = MutableStateFlow(BillingDetailsState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<BillingDetailsEvent>()
    val event = eventChannel.receiveAsFlow()

    private val _swipeComplete = MutableStateFlow(false)

    fun setMeal(meal: Meal) {
        _state.update { it.copy(meal = meal) }
    }

    fun onAction(action: BillingDetailsActions) {
        when (action) {
            BillingDetailsActions.OnBackButtonClick -> viewModelScope.launch {
                eventChannel.send(
                    BillingDetailsEvent.PopBackStack
                )
            }
            BillingDetailsActions.OnCheckoutSwiped -> viewModelScope.launch {
//                eventChannel.send(
//                    BillingDetailsEvent.Navigate(
//                        FoodPlanScreen()
//                    )
//                )
            }

            is BillingDetailsActions.OnPaymentTypeClick -> {
                if(_state.value.selectedPaymentType == action.paymentType) return
                _state.update {
                    it.copy(
                        selectedPaymentType = action.paymentType
                    )
                }
            }
        }
    }

    private fun onSwipeComplete() {
        if (_swipeComplete.value) return // Prevent multiple triggers

        _swipeComplete.value = true
        viewModelScope.launch {
            _state.update {
                it.copy(
                    shouldShowAddCardBottomSheet = true
                )
            }

            delay(500) // Debounce for 500ms (adjust as needed)
            _swipeComplete.value = false // Reset after debounce time
        }
    }
}