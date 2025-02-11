package com.umermahar.charityapp.food.presentation.food

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umermahar.charityapp.R
import com.umermahar.charityapp.core.presentation.utils.compose.GeneralTopBar
import com.umermahar.charityapp.food.presentation.food.components.FeatureCard
import com.umermahar.charityapp.food.presentation.food.components.MealCard
import com.umermahar.charityapp.ui.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun FoodScreen(
    viewModel: FoodViewModel = koinViewModel(),
    navigate: (Any) -> Unit,
    popBackStack: () -> Unit
) {
    val state by viewModel.state

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is FoodEvent.Navigate -> navigate(event.route)
                FoodEvent.PopBackStack -> popBackStack()
            }
        }
    }

    FoodScreenContent(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun FoodScreenContent(
    state: FoodState,
    onAction: (FoodActions) -> Unit
) {
    Scaffold { pd ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(235.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(bottomStart = 30.dp)
                        )
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Color.Red)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                GeneralTopBar(
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            end = 30.dp,
                            start = 14.dp,
                            top = 27.dp,
                            bottom = 26.dp
                        ),
                    onBackButtonClick = {
                        onAction(FoodActions.OnBackButtonClick)
                    },
                    title = stringResource(R.string.food),
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    actionIcons = {
                        Icon(
                            modifier = Modifier
                                .size(40.dp)
                                .border(
                                    width = 2.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(100)
                                )
                                .padding(5.dp),
                            imageVector = Icons.Default.Person,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            contentDescription = null
                        )
                    },
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = 28.dp),
                    text = stringResource(R.string.select_plan_msg),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        color = Color.White
                    ),
                )

                Spacer(
                    modifier = Modifier.height(35.dp)
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    item {
                        Spacer(modifier = Modifier.width(15.dp))
                    }

                    items(state.meals) { meal ->
                        MealCard(
                            modifier = Modifier
                                .padding(
                                    horizontal = 15.dp
                                ),
                            meal = meal,
                            onClick = {
                                onAction(FoodActions.OnMealClick(meal))
                            }
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(top = 30.dp, start = 30.dp, bottom = 20.dp),
                    text = stringResource(R.string.our_features),
                    style = MaterialTheme.typography.headlineSmall,
                )

                LazyVerticalGrid (
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(25.dp),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 30.dp)
                ) {
                    items(state.features) { feature ->
                        FeatureCard(
                            modifier = Modifier,
                            feature = feature
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun FoodScreenPreview() {
    AppTheme {
        FoodScreenContent(FoodState(), {})
    }
}