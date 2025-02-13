@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.umermahar.charityapp.food.presentation.thank_you

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umermahar.charityapp.PAY_NOW_EXPLODE_BOUNDS_KEY
import com.umermahar.charityapp.R
import com.umermahar.charityapp.food.presentation.food_plan.components.ProceedAnimatedArrowIcons

@Composable
fun SharedTransitionScope.ThankYouScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    ThankYouScreenContent(animatedVisibilityScope)
}

@Composable
fun SharedTransitionScope.ThankYouScreenContent(
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Scaffold(

    ) { pd ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd)
                .background(MaterialTheme.colorScheme.secondary)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = PAY_NOW_EXPLODE_BOUNDS_KEY
                    ),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.weight(0.15f))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.hand_shake_img),
                    contentDescription = null,
//                    modifier = Modifier
//                        .weight(1f),
//                    contentScale = ContentScale.FillHeight
                )

                Spacer(modifier = Modifier.height(26.dp))

                Text(
                    text = stringResource(R.string.thank_you),
                    style = MaterialTheme.typography.displayMedium.copy(
                        color = MaterialTheme.colorScheme.onSecondary
                    ),
                )
                Text(
                    text = stringResource(R.string.for_your_order),
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = MaterialTheme.colorScheme.onSecondary
                    ),
                )


                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = stringResource(R.string.glad_to_serve_message),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(
                        Font(R.font.poppins_light)
                    ),
                    color = Color.White
                )
            }

            Box(modifier = Modifier.weight(0.1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {}
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 10.dp),
                    text = stringResource(R.string.track_order),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.width(10.dp))
                ProceedAnimatedArrowIcons(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {}
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 10.dp),
                    text = stringResource(R.string.track_order),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSecondary
                    ),
                )
                Spacer(modifier = Modifier.width(10.dp))
                ProceedAnimatedArrowIcons(
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }

            Box(modifier = Modifier.weight(0.1276f))
        }
    }
}
