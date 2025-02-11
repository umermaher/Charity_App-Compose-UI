package com.umermahar.charityapp.food.presentation.billing_details.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umermahar.charityapp.R

@Composable
fun PaymentTypeCard(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onPaymentTypeClick: () -> Unit
) {

    val color by animateColorAsState(
        targetValue = if(isSelected) {
            MaterialTheme.colorScheme.background
        } else MaterialTheme.colorScheme.surfaceVariant,
        label = "color animation"
    )

    Card(
        modifier = modifier
            .padding(horizontal = 30.dp, vertical = 15.dp)
            .then(
                if(isSelected) {
                    Modifier
                        .shadow(
                            elevation = 30.dp,
                            shape = RectangleShape,
                            ambientColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                            spotColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                        )
                } else Modifier
            ),
        onClick = {
            onPaymentTypeClick()
        },
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = text,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                fontSize = 16.sp
            )

            Image(
                painter = painterResource(
                    id = if(isSelected) {
                        R.drawable.ic_radio_checked
                    } else R.drawable.ic_radio_unchecked
                ),
                contentDescription = stringResource(R.string.payment_type)
            )
        }
    }
}