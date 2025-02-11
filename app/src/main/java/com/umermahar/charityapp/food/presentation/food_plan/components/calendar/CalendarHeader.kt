package com.umermahar.charityapp.food.presentation.food_plan.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.umermahar.charityapp.R
import com.umermahar.charityapp.core.presentation.utils.getDisplayName
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarHeader(
    yearMonth: YearMonth,
    startDayOfMonth: Int?,
    endDayOfMonth: Int?,
    onPreviousMonthButtonClicked: (YearMonth) -> Unit,
    onNextMonthButtonClicked: (YearMonth) -> Unit,
) {

    val rangeText = remember(startDayOfMonth, endDayOfMonth) {
        if(startDayOfMonth != null && endDayOfMonth != null) {
            "${yearMonth.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())} $startDayOfMonth" +
                    " - ${yearMonth.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())} $endDayOfMonth"
        } else "-"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
    ) {
        IconButton(
            onClick = {
                onPreviousMonthButtonClicked(
                    yearMonth.minusMonths(1)
                )
            }
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
                text = yearMonth.getDisplayName(),
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
                onNextMonthButtonClicked(
                    yearMonth.plusMonths(1)
                )
            }
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