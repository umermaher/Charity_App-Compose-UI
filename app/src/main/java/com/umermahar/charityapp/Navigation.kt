package com.umermahar.charityapp

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
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Composable
fun Navigation() {

    val navController = rememberNavController()

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
                navigate = { route ->
                    navController.navigate(route)
                },
                popBackStack = {
                    navController.navigateUp()
                }
            )
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