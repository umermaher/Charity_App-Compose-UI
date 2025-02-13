@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.umermahar.charityapp.food.presentation.billing_details

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.umermahar.charityapp.PAY_NOW_EXPLODE_BOUNDS_KEY
import com.umermahar.charityapp.R
import com.umermahar.charityapp.core.presentation.utils.compose.GeneralTopBar
import com.umermahar.charityapp.core.presentation.utils.compose.SwipableButton
import com.umermahar.charityapp.food.presentation.billing_details.components.AddCardBottomSheet
import com.umermahar.charityapp.food.presentation.billing_details.components.PaymentTypeCard
import com.umermahar.charityapp.food.presentation.food.models.Meal
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SharedTransitionScope.BillingDetailsScreen(
    viewModel: BillingDetailsViewModel = koinViewModel(),
    meal: Meal,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigate: (Any) -> Unit,
    popBackStack: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setMeal(meal)
    }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is BillingDetailsEvent.Navigate -> navigate(event.route)
                BillingDetailsEvent.PopBackStack -> popBackStack()
            }
        }
    }

    BillingDetailsContent(
        state = state,
        animatedVisibilityScope = animatedVisibilityScope,
        onAction = viewModel::onAction,
        onCardAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.BillingDetailsContent(
    state: BillingDetailsState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onAction: (BillingDetailsActions) -> Unit,
    onCardAction: (AddCardActions) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            GeneralTopBar(
                onBackButtonClick = {
                    onAction(BillingDetailsActions.OnBackButtonClick)
                },
                title = stringResource(R.string.billing_details),
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

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.42f)
                        .padding(horizontal = 30.dp)
                        .clip(RoundedCornerShape(30.dp))
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Map")
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.secondary,
                            )
                            .padding(horizontal = 20.dp, vertical = 15.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = "2461 West Drive\n" +
                                    "Chicago, IL 60605",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSecondary,
                            ),
                        )

                        IconButton(
                            onClick = {

                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit),
                                contentDescription = stringResource(R.string.edit),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Column(
                    modifier = Modifier
                        .weight(0.58f),
                ) {
                    state.paymentTypes.forEach { paymentType ->
                        PaymentTypeCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            text = stringResource(paymentType.titleRes),
                            isSelected = paymentType.type == state.selectedPaymentType,
                            onPaymentTypeClick = {
                                onAction(
                                    BillingDetailsActions.OnPaymentTypeClick(
                                        paymentType.type
                                    )
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Box(
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                ) {
                    SwipableButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    key = PAY_NOW_EXPLODE_BOUNDS_KEY
                                ),
                                animatedVisibilityScope = animatedVisibilityScope
                            ),
                        price = "$${state.meal?.pricePerDayUSD?.toInt()}",
                        buttonText = stringResource(R.string.checkout),
                        onSwiped = {
                            onAction(BillingDetailsActions.OnCheckoutButtonSwiped)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

    if(state.shouldShowAddCardBottomSheet) {
        AddCardBottomSheet(
            sheetState = sheetState,
            state = state.addCardState,
            pricePerDayUSD = state.meal?.pricePerDayUSD ?: 0f,
            onAction = { action ->
                scope.launch {
                    if(action is AddCardActions.OnPayNowButtonSwiped) {
                        sheetState.hide()
                    }
                    onCardAction(action)
                }
            },
            onDismissRequest = {
                onAction(BillingDetailsActions.ToggleAddCardBottomSheet)
            }
        )
    }
}