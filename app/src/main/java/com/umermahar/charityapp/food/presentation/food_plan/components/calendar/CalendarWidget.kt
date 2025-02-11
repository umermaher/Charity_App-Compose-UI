package com.umermahar.charityapp.food.presentation.food_plan.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umermahar.charityapp.R
import com.umermahar.charityapp.food.presentation.food_plan.CalendarUiState
import com.umermahar.charityapp.food.presentation.food_plan.DateRange
import java.time.YearMonth

@Composable
fun CalendarWidget(
    modifier: Modifier = Modifier,
    days: Array<String>,
    calendarUiState: CalendarUiState,
    onPreviousMonthButtonClicked: (YearMonth) -> Unit,
    onNextMonthButtonClicked: (YearMonth) -> Unit,
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {
    Column(
        modifier = modifier
    ) {

        CalendarHeader(
            yearMonth = calendarUiState.yearMonth,
            startDayOfMonth = calendarUiState.startDate?.dayOfMonth,
            endDayOfMonth = calendarUiState.endDate?.dayOfMonth,
            onPreviousMonthButtonClicked = onPreviousMonthButtonClicked,
            onNextMonthButtonClicked = onNextMonthButtonClicked
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 30.dp)
        ) {
            repeat(days.size) {
                val item = days[it]
                DayItem(item, modifier = Modifier.weight(1f))
            }
        }

        Content(
            modifier = Modifier
                .padding(horizontal = 30.dp),
            calendarUiState = calendarUiState,
            onDateClickListener = onDateClickListener
        )
    }
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    calendarUiState: CalendarUiState,
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {
    val dates = calendarUiState.dates
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var index = 0
        repeat(6) {
            if (index >= dates.size) return@repeat
            Row {
                repeat(7) {
                    val item = if (index < dates.size) dates[index] else CalendarUiState.Date.Empty
                    CalendarContentItem(
                        date = item,
                        onClickListener = onDateClickListener,
                        modifier = Modifier.weight(1f)
                    )
                    index++
                }
            }
        }
    }
}

@Composable
fun CalendarContentItem(
    date: CalendarUiState.Date,
    onClickListener: (CalendarUiState.Date) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = when {
                    date.dateRange == DateRange.IS_ONLY_IN_RANGE -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.16f) // Range dates
                    else -> Color.Transparent
                },
            ),
        contentAlignment = Alignment.Center
    ) {

        if(date.dateRange == DateRange.START || date.dateRange == DateRange.END) {
            Row(
                modifier = Modifier
                    .size(36.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .background(
                            if(date.dateRange == DateRange.END) {
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.16f)
                            } else Color.Transparent
                        )
                )
                Box(
                    modifier = modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .background(
                            if(date.dateRange == DateRange.START) {
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.16f)
                            } else Color.Transparent
                        )
                )
            }
        }

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(100))
                .background(
                    when (date.dateRange) {
                        DateRange.START, DateRange.END -> MaterialTheme.colorScheme.tertiary // Selected date
                        else -> Color.Transparent
                    }
                )
                .clickable(date.isCurrentMonth) {
                    onClickListener(date)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${date.dayOfMonth}",
                fontSize = 15.sp,
                fontFamily = FontFamily(
                    Font(R.font.poppins_regular)
                ),
                color = when {
                    date.dateRange == DateRange.END
                            || date.dateRange == DateRange.START-> Color.White
                    date.isCurrentMonth || date.dateRange == DateRange.IS_ONLY_IN_RANGE -> MaterialTheme.colorScheme.onBackground
                    else -> MaterialTheme.colorScheme.onBackground.copy(0.2f)
                },
                textAlign = TextAlign.Center
            )
        }
    }
}