@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.umermahar.charityapp.food.presentation.food_plan

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umermahar.charityapp.MEAL_CARD_ANIMATION_BOUNDS_KEY
import com.umermahar.charityapp.R
import com.umermahar.charityapp.food.presentation.food.models.Meal
import com.umermahar.charityapp.food.presentation.food_plan.components.FoodPlanHeader
import com.umermahar.charityapp.food.presentation.food_plan.components.MealTypeCard
import com.umermahar.charityapp.core.presentation.utils.compose.SwipableButton
import com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.Calendar
import com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.CalendarYear
import com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.model.DatesSelectedState
import com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.model.DaySelected
import com.umermahar.charityapp.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SharedTransitionScope.FoodPlanScreen(
    viewModel: FoodPlanViewModel = koinViewModel(),
    meal: Meal,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigate: (Any) -> Unit,
    popBackStack: () -> Unit
) {
    LaunchedEffect(Unit) {
        Log.d("FoodPlanScreen", "$meal")
        viewModel.setMeal(meal)
    }

    LaunchedEffect (Unit) {
        viewModel.event.collect { event ->
            when (event) {
                FoodPlanEvent.PopBackStack -> popBackStack()
                is FoodPlanEvent.Navigate -> navigate(event.route)
            }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    FoodPlanScreenContent(
        state = state,
        calendarYear = remember { viewModel.calendarYear },
        datesSelectedState = viewModel.datesSelected,
        onAction = viewModel::onAction,
        animatedVisibilityScope = animatedVisibilityScope
    )
}

@Composable
fun SharedTransitionScope.FoodPlanScreenContent(
    state: FoodPlanState,
    calendarYear: CalendarYear,
    datesSelectedState: DatesSelectedState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onAction: (FoodPlanActions) -> Unit
) {
    if(state.meal != null) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { pd ->
            Column(
                modifier = Modifier
                    .padding(pd)
            ) {
                FoodPlanHeader(
                    modifier = Modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = MEAL_CARD_ANIMATION_BOUNDS_KEY + state.meal.id
                            ),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    meal = state.meal,
                    animatedVisibilityScope = animatedVisibilityScope,
                    onAction = {}
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 18.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = stringResource(R.string.select_days),
                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                            fontSize = 16.sp
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = stringResource(R.string.calendar)
                        )
                    }

//                    CalendarWidget(
//                        days = DateUtil.daysOfWeek,
//                        calendarUiState = state.calendarUiState,
//                        onPreviousMonthButtonClicked = { prevMonth ->
//                            onAction(
//                                FoodPlanActions.OnPreviousMonthButtonClick(prevMonth)
//                            )
//                        },
//                        onNextMonthButtonClicked = { nextMonth ->
//                            onAction(
//                                FoodPlanActions.OnNextMonthButtonClick(nextMonth)
//                            )
//                        },
//                        onDateClickListener = {
//                            onAction(
//                                FoodPlanActions.OnDateClick(it)
//                            )
//                        }
//                    )
                    Calendar(
                        modifier = Modifier
                            .weight(1f),
                        calendarYear = calendarYear,
                        from = datesSelectedState.from,
                        to = datesSelectedState.to,
                        onDayClicked = { day, month ->
                            onAction(
                                FoodPlanActions.OnDaySelected(
                                    DaySelected(
                                        day = day.value,
                                        month = month,
                                        year = calendarYear
                                    )
                                )
                            )
                        }
                    )

                    Column (
                        modifier = Modifier.padding(30.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            MealTypeCard(
                                modifier = Modifier
                                    .weight(1f),
                                title = stringResource(R.string.lunch),
                                isSelected = state.mealType == MealType.LUNCH,
                                onClick = {
                                    onAction(FoodPlanActions.OnMealTypeClick(MealType.LUNCH))
                                }
                            )

                            Spacer(modifier = Modifier.width(30.dp))

                            MealTypeCard(
                                modifier = Modifier
                                    .weight(1f),
                                title = stringResource(R.string.dinner),
                                isSelected = state.mealType == MealType.DINNER,
                                onClick = {
                                    onAction(FoodPlanActions.OnMealTypeClick(MealType.DINNER))
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(30.dp))

                        SwipableButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            price = "$${state.meal.pricePerDayUSD.toInt()}",
                            buttonText = stringResource(R.string.book_now),
                            onSwiped = {
                                onAction(FoodPlanActions.OnBookNowButtonSwiped)
                            }
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
//        SharedTransitionScope {
//            FoodPlanScreenContent(
//                state = FoodPlanState(
//                    meal = Meal(
//                        id = 1,
//                        count = 1,
//                        pricePerDayUSD = 1f
//                    )
//                ),
//                onAction = {}
//            )
//        }
    }
}