package com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umermahar.charityapp.R
import com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.model.CalendarDay
import com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.model.CalendarMonth
import com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.model.DayOfWeek
import com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.model.DaySelected
import com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.model.DaySelectedStatus
import kotlinx.coroutines.launch

@Composable
fun Calendar(
    calendarYear: CalendarYear,
    from: DaySelected,
    to: DaySelected,
    onDayClicked: (CalendarDay, CalendarMonth) -> Unit,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState { calendarYear.size }

    val month = remember(pagerState.currentPage) {
        calendarYear[pagerState.currentPage]
    }

    Column {

        CalendarHeader(
            pagerState = pagerState,
            calendarYear = calendarYear,
            from = from,
            to = to,
            onPreviousMonthButtonClicked = {
                scope.launch {
                    if (pagerState.currentPage > 0) {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            },
            onNextMonthButtonClicked = {
                scope.launch {
                    if (pagerState.currentPage < calendarYear.lastIndex) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
        ) {
            for (day in DayOfWeek.entries) {
                DayItem(
                    day = day.name.take(1),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        HorizontalPager(
            state = pagerState
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                month.weeks.value.forEachIndexed { index, week ->
                    Week(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        week = week,
                        month = month,
                        onDayClicked = { day ->
                            onDayClicked(day, month)
                        }
                    )
                }
            }
        }
    }
}

typealias CalendarWeek = List<CalendarDay>

@Composable
fun Week(
    modifier: Modifier = Modifier,
    month: CalendarMonth,
    week: CalendarWeek,
    onDayClicked: (CalendarDay) -> Unit
) {
    Row(
        modifier = modifier,
    ) {
        week.forEachIndexed { index, calendarDay ->
            Day(
                modifier = Modifier
                    .weight(1f),
                day = calendarDay,
                onDayClicked = onDayClicked,
                month = month
            )
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    onDayClicked: (CalendarDay) -> Unit,
    month: CalendarMonth,
    modifier: Modifier = Modifier
) {
    val enabled = day.status != DaySelectedStatus.NonClickable
    DayContainer(
        modifier = modifier.semantics {
            if (enabled) text = AnnotatedString("${month.name} ${day.value} ${month.year}")
            dayStatusProperty = day.status
        },
        selected = day.status != DaySelectedStatus.NoSelected,
        onClick = {
            onDayClicked(day)
        },
        onClickEnabled = enabled,
        backgroundColor = when (day.status) {
            DaySelectedStatus.Selected -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.16f)
            else -> Color.Transparent
        },
        onClickLabel = stringResource(id = R.string.click_label_select)
    ) {
        DayStatusContainer(status = day.status) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    // Parent will handle semantics
                    .clearAndSetSemantics {},
                text = if(day.value != -1) {
                    "${day.value}"
                } else "",
                fontSize = 15.sp,
                fontFamily = FontFamily(
                    Font(R.font.poppins_regular)
                ),
                color = when(day.status) {
                    DaySelectedStatus.FirstDay,
                    DaySelectedStatus.LastDay,
                    DaySelectedStatus.FirstLastDay -> Color.White
                    else -> MaterialTheme.colorScheme.onBackground
                }
            )
        }
    }
}

@Composable
private fun Day(name: String) {
    DayContainer {
        Text(
            modifier = Modifier.wrapContentSize(Alignment.Center),
            text = name,
            style = MaterialTheme.typography.labelMedium.copy(Color.Black.copy(alpha = 0.6f))
        )
    }
}

@Composable
private fun DayContainer(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = { },
    onClickEnabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    onClickLabel: String? = null,
    content: @Composable () -> Unit
) {
    val stateDescriptionLabel = stringResource(
        if (selected) R.string.state_descr_selected else R.string.state_descr_not_selected
    )
    Surface(
        modifier = modifier
            .size(CELL_SIZE)
            .then(
                if (onClickEnabled) {
                    modifier.semantics {
                        stateDescription = stateDescriptionLabel
                    }
                } else {
                    modifier.clearAndSetSemantics { }
                }
            )
            .clickable(
                enabled = onClickEnabled,
                onClickLabel = onClickLabel
            ) {
                onClick()
            },
        color = backgroundColor,
    ) {
        content()
    }
}

@Composable
private fun DayStatusContainer(
    status: DaySelectedStatus,
    content: @Composable () -> Unit
) {
    if (status.isMarked()) {
        Box {
            val color = MaterialTheme.colorScheme.tertiary
            when(status) {
                DaySelectedStatus.FirstLastDay -> {
                    Circle(color = color)
                }
                DaySelectedStatus.FirstDay -> {
                    Circle(color = color)
                    SemiRect(color = color.copy(0.16f), lookingLeft = false)
                }
                DaySelectedStatus.LastDay -> {
                    Circle(color = color)
                    SemiRect(color = color.copy(0.16f), lookingLeft = true)
                }
                else -> {}
            }
            content()
        }
    } else {
        content()
    }
}

private fun DaySelectedStatus.isMarked(): Boolean {
    return when (this) {
        DaySelectedStatus.Selected -> true
        DaySelectedStatus.FirstDay -> true
        DaySelectedStatus.LastDay -> true
        DaySelectedStatus.FirstLastDay -> true
        else -> false
    }
}

private val CELL_SIZE = 40.dp
val DayStatusKey = SemanticsPropertyKey<DaySelectedStatus>("DayStatusKey")
var SemanticsPropertyReceiver.dayStatusProperty by DayStatusKey

