package com.umermahar.charityapp.food.presentation.billing_details.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.umermahar.charityapp.R
import com.umermahar.charityapp.core.presentation.utils.compose.SwipableButton
import com.umermahar.charityapp.food.presentation.billing_details.AddCardActions
import com.umermahar.charityapp.food.presentation.billing_details.AddCardState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardBottomSheet(
    sheetState: SheetState,
    state: AddCardState,
    pricePerDayUSD: Float,
    onAction: (AddCardActions) -> Unit,
    onDismissRequest: () -> Unit,
) {

    val labelStyle = MaterialTheme.typography.bodyMedium.copy(
        color = Color(0x8058595B),
        fontWeight = FontWeight.Light
    )
    val textBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val textStyle = MaterialTheme.typography.bodyMedium.copy(
        color = Color(0xFF58595B)
    )
    val centeredTextStyle = textStyle.copy(
        textAlign = TextAlign.Center
    )

    val cardFocusRequesters = remember {
        (1..4).map { FocusRequester() }
    }

    val expiryDateFocusRequester = remember {
        FocusRequester()
    }

    val cvvFocusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(state.cardNumberFocusedIndex) {
        state.cardNumberFocusedIndex?.let { index ->
            cardFocusRequesters.getOrNull(index)?.requestFocus()
        }
    }

    LaunchedEffect(state.cardNumber) {
        val allNumbersEntered = state.cardNumber.all { it.length == 4 }
        Log.d("AddCardBottomSheet", "allNumbersEntered: $allNumbersEntered")
        if(allNumbersEntered) {
            cardFocusRequesters.forEach {
                it.freeFocus()
            }
            expiryDateFocusRequester.requestFocus()
        }
    }

    var isExpiryDateFieldFocused by remember {
        mutableStateOf(false)
    }

    var isCvvFieldFocused by remember {
        mutableStateOf(false)
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .width(60.dp)
                    .height(4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.extraLarge
                    )
            )
        },
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
        ) {
            Text(
                text = stringResource(R.string.name_on_card),
                style = labelStyle
            )

            Spacer(modifier = Modifier.height(10.dp))

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(textBackgroundColor)
                    .padding(
                        vertical = 15.dp,
                        horizontal = 20.dp
                    ),
                value = state.name,
                onValueChange = {
                    onAction(AddCardActions.OnNameChange(it))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                ),
                textStyle = textStyle,
                decorationBox = { innerTextField ->
                    // Placeholder
                    Box(modifier = Modifier.weight(1f)) {
                        if (state.name.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.enter_name),
                                style = textStyle
                            )
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(21.dp))

            Text(
                text = stringResource(R.string.card_number),
                style = labelStyle
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                state.cardNumber.forEachIndexed { index, number ->
                    CardInputField(
                        digits = number,
                        focusRequester = cardFocusRequesters[index],
                        onFocusChange = { isFocused ->
                            if (isFocused) {
                                onAction(AddCardActions.OnCardNumberChangeFieldFocused(index))
                            }
                        },
                        onDigitsChange = { newDigits ->
                            onAction(AddCardActions.OnEnterCardNumber(newDigits, index))
                        },
                        onKeyboardBack = {
                            onAction(AddCardActions.OnKeyboardBack)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        textStyle = centeredTextStyle,
                    )
                }
            }

            Spacer(modifier = Modifier.height(21.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.expiry_date),
                        style = labelStyle
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .background(textBackgroundColor)
                            .padding(
                                vertical = 15.dp,
                                horizontal = 20.dp
                            )
                            .focusRequester(expiryDateFocusRequester)
                            .onFocusEvent {
                                isExpiryDateFieldFocused = it.isFocused
                            },
                        value = state.expiryDate,
                        onValueChange = {
                            onAction(AddCardActions.OnExpiryDateChange(it))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        textStyle = centeredTextStyle,
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            // Placeholder
                            Box(modifier = Modifier.weight(1f)) {
                                if (state.expiryDate.text.isEmpty() && !isExpiryDateFieldFocused) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        text = "-",
                                        style = centeredTextStyle
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.cvv),
                        style = labelStyle
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .background(textBackgroundColor)
                            .padding(
                                vertical = 15.dp,
                                horizontal = 20.dp
                            )
                            .focusRequester(cvvFocusRequester)
                            .onFocusEvent {
                                isCvvFieldFocused = it.isFocused
                            },
                        value = state.cvv,
                        singleLine = true,
                        textStyle = centeredTextStyle,
                        onValueChange = {
                            onAction(AddCardActions.OnCvvChange(it))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        decorationBox = { innerTextField ->
                            // Placeholder
                            Box(modifier = Modifier.weight(1f)) {
                                if (state.cvv.isEmpty() && !isCvvFieldFocused) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        text = "-",
                                        style = centeredTextStyle
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            SwipableButton(
                modifier = Modifier
                    .fillMaxWidth(),
                price = "$${pricePerDayUSD.toInt()}",
                buttonText = stringResource(R.string.pay_now),
                onSwiped = {
                    onAction(AddCardActions.OnPayNowButtonSwiped)
                }
            )
        }
    }
}
