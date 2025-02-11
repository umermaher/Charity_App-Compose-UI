package com.umermahar.charityapp.food.presentation.food

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umermahar.charityapp.FoodPlanScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FoodViewModel : ViewModel() {

    var state = mutableStateOf(FoodState())
        private set

    private val eventChannel = Channel<FoodEvent>()
    val event = eventChannel.receiveAsFlow()

    fun onAction(actions: FoodActions) {
        when (actions) {

            is FoodActions.OnMealClick -> viewModelScope.launch {
                eventChannel.send(
                    FoodEvent.Navigate(
                        route = FoodPlanScreen(
                            meal = actions.meal
                        )
                    )
                )
            }

            FoodActions.OnBackButtonClick -> viewModelScope.launch {
                eventChannel.send(FoodEvent.PopBackStack)
            }
        }
    }
}