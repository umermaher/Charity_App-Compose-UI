package com.umermahar.charityapp.choose_category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umermahar.charityapp.R
import com.umermahar.charityapp.choose_category.components.ChooseCategory
import com.umermahar.charityapp.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChooseCategoryScreen(
    viewModel: ChooseCategoryViewModel = koinViewModel(),
    navigate: (Any) -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is ChooseCategoryEvent.Navigate -> navigate(event.route)
            }
        }
    }

    ChooseCategoryScreenContent(
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ChooseCategoryScreenContent(
    onEvent: (ChooseCategoryActions) -> Unit
) {
    Scaffold { pd ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd)
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.47f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hand_shake_img),
                        contentDescription = null,
                        modifier = Modifier
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(0.53f)
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.lets_help_together),
                        style = MaterialTheme.typography.displaySmall.copy(
                            color = Color.White
                        ),
                    )

                    Text(
                        text = stringResource(R.string.in_this_pandemic),
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = Color.White
                        ),
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = stringResource(R.string.choose_category_msg),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(
                            Font(R.font.poppins_light)
                        ),
                        color = Color.White
                    )
                }
            }

            ChooseCategory(
                modifier = Modifier
                    .height(233.dp)
                    .fillMaxWidth(),
                onCategorySelected = {
                    onEvent(ChooseCategoryActions.OnCategorySelected(it))
                }
            )
        }
    }
}

@Preview
@Composable
fun ChooseCategoryScreenPreview() {
    AppTheme {
        ChooseCategoryScreenContent(
            onEvent = {}
        )
    }
}