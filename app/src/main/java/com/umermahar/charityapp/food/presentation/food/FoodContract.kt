package com.umermahar.charityapp.food.presentation.food

import com.umermahar.charityapp.R
import com.umermahar.charityapp.food.presentation.food.models.FeatureItem
import com.umermahar.charityapp.food.presentation.food.models.Meal

data class FoodState(
    val meals: List<Meal> = listOf(
        Meal(
            id = 1,
            count = 1,
            pricePerDayUSD = 12f
        ),
        Meal(
            id = 2,
            count = 2,
            pricePerDayUSD = 18f
        ),
        Meal(
            id = 3,
            count = 3,
            pricePerDayUSD = 24f
        ),
    ),
    val features: List<FeatureItem> = getFeaturesItem()
)

sealed interface FoodActions {
    data class OnMealClick(val meal: Meal) : FoodActions
    data object OnBackButtonClick : FoodActions
}

sealed interface FoodEvent {
    data class Navigate(val route: Any) : FoodEvent
    data object PopBackStack : FoodEvent
}

private fun getFeaturesItem() = listOf(
    FeatureItem(
        iconRes = R.drawable.img_our_features_1,
        textRes = R.string.order_your_meal_online
    ),
    FeatureItem(
        iconRes = R.drawable.img_our_features_2,
        textRes = R.string.schedule_as_per_your_ease
    ),
    FeatureItem(
        iconRes = R.drawable.img_our_features_3,
        textRes = R.string.track_delivery
    ),
    FeatureItem(
        iconRes = R.drawable.img_our_features_4,
        textRes = R.string.enjoy_a_warm_meal
    )
)

