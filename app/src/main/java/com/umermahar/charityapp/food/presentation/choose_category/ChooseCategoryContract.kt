package com.umermahar.charityapp.food.presentation.choose_category

import kotlin.reflect.KClass


sealed interface ChooseCategoryActions {
    data class OnCategorySelected(val categoryType: CategoryType): ChooseCategoryActions
}

sealed interface ChooseCategoryEvent {
    data class Navigate(val route: Any): ChooseCategoryEvent
}

enum class CategoryType {
    MEDICAL, FOOD, DONATE
}