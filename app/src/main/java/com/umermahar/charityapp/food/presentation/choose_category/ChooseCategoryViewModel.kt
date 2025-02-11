package com.umermahar.charityapp.food.presentation.choose_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umermahar.charityapp.FoodScreen
import com.umermahar.charityapp.food.presentation.choose_category.CategoryType.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ChooseCategoryViewModel: ViewModel() {

    private val eventChannel = Channel<ChooseCategoryEvent>()
    val event = eventChannel.receiveAsFlow()

    fun onEvent(event: ChooseCategoryActions) {
        when(event) {
            is ChooseCategoryActions.OnCategorySelected -> {
                when (event.categoryType) {
                    MEDICAL -> {}
                    FOOD -> viewModelScope.launch {
                        eventChannel.send(ChooseCategoryEvent.Navigate(FoodScreen))
                    }
                    DONATE -> {}
                }
            }
        }
    }
}