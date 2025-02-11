package com.umermahar.charityapp.food.presentation.choose_category.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.umermahar.charityapp.R
import com.umermahar.charityapp.food.presentation.choose_category.CategoryType
import com.umermahar.charityapp.food.presentation.choose_category.ChooseCategoryScreen
import com.umermahar.charityapp.ui.theme.AppTheme

@Composable
fun ChooseCategory(
    modifier: Modifier = Modifier,
    onCategorySelected: (CategoryType) -> Unit
) {
    Column(
        modifier = modifier
            .drawBehind {
                val path  = Path().apply {
                    val width = size.width
                    val height = size.height
                    val cornerRadius = 40.dp.toPx()
                    val incline = 30.dp.toPx()
                    moveTo(0f, cornerRadius)
                    arcTo(
                        rect = Rect(0f, incline, cornerRadius * 2, cornerRadius * 2),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = 73f,
                        forceMoveTo = false
                    )

//                    // Top-center curve
//                    arcTo(
//                        rect = Rect(
//                            (width / 2) - cornerRadius,
//                            -cornerRadius,
//                            (width / 2) + cornerRadius,
//                            cornerRadius
//                        ),
//                        startAngleDegrees = -180f,
//                        sweepAngleDegrees = 180f,
//                        forceMoveTo = false
//                    )
                    lineTo((width / 2), 0f)

                    arcTo(
                        rect = Rect(width - cornerRadius * 2, incline, width, cornerRadius * 2),
                        startAngleDegrees = 287f,
                        sweepAngleDegrees = 77f,
                        forceMoveTo = false
                    )
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }

                drawPath(
                    path = path,
                    color = Color.White
                )
            }
            .padding(top = 30.dp, start = 35.dp, end = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            text = stringResource(R.string.choose_your_category),
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color(0xFF282846)
            ),
            fontWeight = FontWeight.ExtraBold,
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(35.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            CategoryItem(
                modifier = Modifier
                    .weight(1f),
                text = stringResource(R.string.medical),
                icon = R.drawable.img_medical
            )
            CategoryItem(
                modifier = Modifier
                    .weight(1f)
                    .clickable { 
                        onCategorySelected(CategoryType.FOOD)
                    },
                text = stringResource(R.string.food),
                icon = R.drawable.img_food
            )
            CategoryItem(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.donate),
                icon = R.drawable.img_donate
            )
        }

    }
}

@Preview
@Composable
fun ChooseCategoryScreenPreview() {
    AppTheme {
        ChooseCategory(
            modifier = Modifier
                .height(233.dp)
                .fillMaxWidth(),
            onCategorySelected = {}
        )
    }
}