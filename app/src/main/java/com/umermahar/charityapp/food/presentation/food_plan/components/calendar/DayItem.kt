package com.umermahar.charityapp.food.presentation.food_plan.components.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umermahar.charityapp.R

@Composable
fun DayItem(day: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 12.dp),
            text = day,
            fontFamily = FontFamily(
                androidx.compose.ui.text.font.Font(R.font.poppins_medium)
            ),
            fontSize = 16.sp,
        )
    }
}