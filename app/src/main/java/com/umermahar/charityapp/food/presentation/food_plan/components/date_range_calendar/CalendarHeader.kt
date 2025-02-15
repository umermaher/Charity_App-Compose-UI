package com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.umermahar.charityapp.R
import com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.model.DaySelected
import com.umermahar.charityapp.food.presentation.food_plan.components.date_range_calendar.model.DaySelectedEmpty

@Composable
fun CalendarHeader(
    pagerState: PagerState,
    calendarYear: CalendarYear,
    from: DaySelected,
    to: DaySelected,
    onPreviousMonthButtonClicked: () -> Unit,
    onNextMonthButtonClicked: () -> Unit,
) {

    val rangeText = remember(from, to){
        if(from != DaySelectedEmpty && to != DaySelectedEmpty) {
            "${from.day} ${from.month.name.take(3)} - ${to.day} ${to.month.name.take(3)}"
        } else "-"
    }

    val monthName = remember(pagerState.currentPage) {
        calendarYear[pagerState.currentPage].name
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
    ) {
        IconButton(
            onClick = {
                onPreviousMonthButtonClicked()
            },
            enabled = pagerState.currentPage > 0
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = stringResource(id = R.string.back)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = monthName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = rangeText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.typography.bodySmall.color.copy(0.5f)
            )

        }

        IconButton(
            onClick = {
                onNextMonthButtonClicked()
            },
            enabled = pagerState.currentPage < calendarYear.lastIndex
        ) {
            Icon(
                modifier = Modifier
                    .rotate(180f),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = stringResource(id = R.string.back)
            )
        }
    }
}