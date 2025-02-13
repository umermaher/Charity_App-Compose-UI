package com.umermahar.charityapp

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.umermahar.charityapp.food.presentation.billing_details.BillingDetailsScreen
import com.umermahar.charityapp.food.presentation.choose_category.ChooseCategoryScreen
import com.umermahar.charityapp.food.presentation.food.FoodScreen
import com.umermahar.charityapp.food.presentation.food.models.Meal
import com.umermahar.charityapp.food.presentation.food_plan.FoodPlanScreen
import com.umermahar.charityapp.food.presentation.thank_you.ThankYouScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Navigation() {

    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = ChooseCategoryScreen,
            modifier = Modifier
        ) {
            composable<ChooseCategoryScreen> {
                ChooseCategoryScreen(
                    navigate = { route ->
                        navController.navigate(route)
                    }
                )
            }

            composable<FoodScreen> {
                FoodScreen(
                    animatedVisibilityScope = this,
                    navigate = { route ->
                        navController.navigate(route)
                    },
                    popBackStack = {
                        navController.navigateUp()
                    }
                )
            }

            composable<FoodPlanScreen>(
                typeMap = mapOf(
                    typeOf<Meal>() to CustomNavType.mealType,
                )
            ) {
                val meal = it.toRoute<FoodPlanScreen>().meal
                FoodPlanScreen(
                    meal = meal,
                    animatedVisibilityScope = this,
                    navigate = { route ->
                        navController.navigate(route)
                    },
                    popBackStack = {
                        navController.navigateUp()
                    }
                )
            }

            composable<BillingDetailScreen>(
                typeMap = mapOf(
                    typeOf<Meal>() to CustomNavType.mealType,
                )
            ) {
                val meal = it.toRoute<BillingDetailScreen>().meal
                BillingDetailsScreen(
                    meal = meal,
                    animatedVisibilityScope = this,
                    navigate = { route ->
                        navController.navigate(route)
                    },
                    popBackStack = {
                        navController.navigateUp()
                    }
                )
            }

            composable<ThankYouScreen>() {
                ThankYouScreen(
                    animatedVisibilityScope = this
                )
            }
        }
    }
}

@Serializable
object ChooseCategoryScreen

@Serializable
object FoodScreen

@Serializable
data class FoodPlanScreen(
    val meal: Meal
)

@Serializable
data class BillingDetailScreen(
    val meal: Meal
)

@Serializable
object ThankYouScreen

const val PAY_NOW_EXPLODE_BOUNDS_KEY = "PAY_NOW_EXPLODE_BOUNDS_KEY"
const val MEAL_CARD_ANIMATION_BOUNDS_KEY = "MEAL_CARD_ANIMATION_BOUNDS_KEY"
const val MEAL_COUNT_ANIMATION_BOUNDS_KEY = "MEAL_COUND_ANIMATION_BOUNDS_KEY"