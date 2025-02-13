package com.umermahar.charityapp.core.presentation.utils.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umermahar.charityapp.R
import com.umermahar.charityapp.food.presentation.food_plan.components.ProceedAnimatedArrowIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SwipableButton(
    modifier: Modifier = Modifier,
    price: String,
    buttonText: String,
    scope: CoroutineScope = rememberCoroutineScope(),
    onSwiped: () -> Unit
) {

    var swipeableWidth by remember {
        mutableIntStateOf(0)
    }
    var swipeableHeight by remember {
        mutableIntStateOf(0)
    }

    // Track button expansion
    val buttonWidthFactor = remember { Animatable(0.66f) } // Starts at 66% of parent width

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .onSizeChanged {
                swipeableHeight = it.height
                swipeableWidth = it.width
            }

    ) {
        // Price Label (Static)
        Text(
            text = price,
            fontSize = 24.sp,
            fontFamily = FontFamily(
                Font(R.font.poppins_semibold)
            ),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(
                    vertical = 16.dp,
                    horizontal = 31.dp
                )
        )

        // Swipeable "Book Now" Button (Expands but Stays Fixed on Left)
        Box(
            modifier = Modifier
                .size(
                    width = with(LocalDensity.current) { (swipeableWidth * buttonWidthFactor.value).toDp() },
                    height = with(LocalDensity.current) { swipeableHeight.toDp() }
                )
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            scope.launch {
                                buttonWidthFactor.animateTo(0.66f) // ✅ Ensures smooth reset
                            }
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                // Expand width dynamically towards the right
                                val expansionFactor = (buttonWidthFactor.value + (dragAmount / swipeableWidth))
                                    .coerceIn(0.66f, 1f)

                                buttonWidthFactor.snapTo(expansionFactor)

                                if (buttonWidthFactor.value >= 1f) {
                                    buttonWidthFactor.snapTo(0.66f) // ✅ Reset after completion
                                    onSwiped() // ✅ Trigger completion when fully expanded
                                }
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(
                        vertical = 16.dp,
                    ),
                    text = buttonText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSecondary
                )

                Spacer(modifier = Modifier.width(14.dp))

                ProceedAnimatedArrowIcons()
            }
        }
    }
}
