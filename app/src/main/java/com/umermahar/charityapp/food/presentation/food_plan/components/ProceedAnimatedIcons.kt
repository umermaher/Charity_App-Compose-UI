package com.umermahar.charityapp.food.presentation.food_plan.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.umermahar.charityapp.R
import kotlinx.coroutines.delay

@Composable
fun ProceedAnimatedIcons() {
    val colors = listOf(
        Color.White.copy(alpha = 0.2f), // First icon starts dim
        Color.White.copy(alpha = 0.5f), // Second icon medium brightness
        Color.White                      // Third icon fully white
    )

    var index by remember { mutableIntStateOf(0) }

    // Animating colors in the correct order (1 -> 2 -> 3)
    val animatedColors = List(3) { i ->
        animateColorAsState(
            targetValue = colors[(index - i + colors.size) % colors.size], // This ensures left-to-right transition
            animationSpec = tween(durationMillis = 500),
            label = "IconColorAnimation"
        )
    }

    // Trigger animation every 500ms
    LaunchedEffect(Unit) {
        while (true) {
            delay(300) // Controls speed of color shift
            index = (index + 1) % colors.size // Move forward (left to right)
        }
    }

    // Display Icons
    Row {
        repeat(3) { i ->
            Icon(
                modifier = Modifier.rotate(180f),
                painter = painterResource(R.drawable.ic_back),
                contentDescription = null,
                tint = animatedColors[i].value // Apply animated color
            )
        }
    }
}

