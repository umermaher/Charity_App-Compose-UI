package com.umermahar.charityapp.food.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umermahar.charityapp.R
import com.umermahar.charityapp.food.models.FeatureItem

@Composable
fun FeatureCard(
    modifier: Modifier = Modifier,
    feature: FeatureItem
) {
    Card(
        modifier = modifier,
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFFF5F6F9)
//        ),
        shape = RoundedCornerShape(30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp, bottom = 17.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(42.dp),
                painter = painterResource(id = feature.iconRes),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(feature.textRes),
                fontFamily = FontFamily(
                    Font(R.font.poppins_semibold)
                ),
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}