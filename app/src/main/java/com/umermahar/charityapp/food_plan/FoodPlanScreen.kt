package com.umermahar.charityapp.food_plan

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.umermahar.charityapp.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun FoodPlanScreen(
    viewModel: FoodPlanViewModel = koinViewModel(),
    navigate: (Any) -> Unit,
    popBackStack: () -> Unit
) {

}

@Composable
fun FoodPlanScreenContent(

) {

}

@Preview
@Composable
fun FoodScreenPreview() {
    AppTheme {
        FoodPlanScreenContent()
    }
}