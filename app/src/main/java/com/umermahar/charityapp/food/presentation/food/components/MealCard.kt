package com.umermahar.charityapp.food.presentation.food.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umermahar.charityapp.R
import com.umermahar.charityapp.food.presentation.food.models.Meal

@Composable
fun MealCard(
    modifier: Modifier = Modifier,
    meal: Meal,
    onClick: (Meal) -> Unit
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if(meal.id % 2 == 0) {
                MaterialTheme.colorScheme.primary
            } else MaterialTheme.colorScheme.tertiary
        ),
        shape = RoundedCornerShape(30.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(top = 30.dp, start = 30.dp, end = 30.dp, bottom = 20.dp)
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${meal.count} Meal",
                    style = MaterialTheme.typography.headlineSmall,
                )

                Spacer(modifier = Modifier.width(59.dp))

                Image(
                    painter = painterResource(id = R.drawable.product_veg),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "$${meal.pricePerDayUSD.toInt()}",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = FontFamily(
                            Font(
                                resId = R.font.poppins_bold,
                            )
                        )
                    ),
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    modifier = Modifier
                        .padding(bottom = 4.dp),
                    text = "/a day",
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            FilledTonalButton(
                onClick = {
                    onClick(meal)
                },
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            vertical = 2.dp,
                            horizontal = 3.dp
                        ),
                    text = stringResource(R.string.view_details),
                    fontFamily = FontFamily(
                        Font(R.font.poppins_semibold)
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun MealCardPreview() {
    MealCard(
        meal = Meal(
            id = 1,
            count = 1,
            pricePerDayUSD = 12f
        ),
        onClick = {}
    )
}