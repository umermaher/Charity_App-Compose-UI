package com.umermahar.charityapp.food.presentation.food_plan.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umermahar.charityapp.R
import com.umermahar.charityapp.core.presentation.utils.compose.GeneralTopBar
import com.umermahar.charityapp.food.presentation.food.models.Meal
import com.umermahar.charityapp.food.presentation.food_plan.FoodPlanActions
import com.umermahar.charityapp.ui.theme.AppTheme

@Composable
fun FoodPlanHeader(
    modifier: Modifier = Modifier,
    meal: Meal,
    onAction: (FoodPlanActions) -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(
            bottomStart = 30.dp,
            bottomEnd = 30.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
        ) {
            GeneralTopBar(
                onBackButtonClick = {
                    onAction(FoodPlanActions.OnBackButtonClick)
                },
                title = stringResource(R.string.food_plan),
                actionIcons = {
                    IconButton(
                        onClick = {}
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_notification_badged),
                            contentDescription = null
                        )
                    }
                }
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
            ) {
                Image(
                    modifier = Modifier
                        .height(120.dp),
                    painter = painterResource(id = R.drawable.img_food_plan),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(30.dp))

                Column {

                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${meal.count} Meal",
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                modifier = Modifier
                                    .rotate(-90f),
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = null
                            )
                        }
                    }

//                    Spacer(modifier = Modifier.height(11.dp))

                    Text(
                        text = "1 Veg Sabji, 1 Panner Sabji,\n" +
                                "4 Roti, 1 Sweet, Salad and\n" +
                                "Buttermilk.",
                        fontFamily = FontFamily(
                            Font(R.font.poppins_medium)
                        ),
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun FoodPlanHeaderPreview() {
    AppTheme {
        FoodPlanHeader(
            meal = Meal(
                id = 1,
                count = 1,
                pricePerDayUSD = 1f
            ),
            onAction = {}
        )
    }
}