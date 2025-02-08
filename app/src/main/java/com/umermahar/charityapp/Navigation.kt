package com.umermahar.charityapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.umermahar.charityapp.choose_category.ChooseCategoryScreen
import com.umermahar.charityapp.food.FoodScreen
import com.umermahar.charityapp.utils.ChooseCategoryScreen
import com.umermahar.charityapp.utils.FoodPlanScreen
import com.umermahar.charityapp.utils.FoodScreen
import kotlinx.serialization.Serializable

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

        composable<FoodPlanScreen> {

        }
    }
}
