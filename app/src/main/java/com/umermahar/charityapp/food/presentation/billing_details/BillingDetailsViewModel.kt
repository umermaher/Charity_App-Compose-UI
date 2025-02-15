package com.umermahar.charityapp.food.presentation.billing_details

import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umermahar.charityapp.ThankYouScreen
import com.umermahar.charityapp.food.presentation.food.models.Meal
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BillingDetailsViewModel: ViewModel() {

    private val _state = MutableStateFlow(BillingDetailsState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<BillingDetailsEvent>()
    val event = eventChannel.receiveAsFlow()

    private val _swipeComplete = MutableStateFlow(false)

    fun setMeal(meal: Meal) {
        _state.update { it.copy(meal = meal) }
    }

    fun onAction(action: BillingDetailsActions) {
        when (action) {

            BillingDetailsActions.OnBackButtonClick -> viewModelScope.launch {
                eventChannel.send(
                    BillingDetailsEvent.PopBackStack
                )
            }

            BillingDetailsActions.OnCheckoutButtonSwiped -> onSwipeComplete()

            is BillingDetailsActions.OnPaymentTypeClick -> {
                if(_state.value.selectedPaymentType == action.paymentType) return
                _state.update {
                    it.copy(
                        selectedPaymentType = action.paymentType
                    )
                }
            }

            BillingDetailsActions.ToggleAddCardBottomSheet -> _state.update {
                it.copy(
                    shouldShowAddCardBottomSheet = !it.shouldShowAddCardBottomSheet
                )
            }
        }
    }

    fun onAction(action: AddCardActions) {
        when (action) {

            is AddCardActions.OnCvvChange -> {
                if(action.value.length > 3) {
                    return
                }

                _state.update {
                    it.copy(addCardState = it.addCardState.copy(cvv = action.value))
                }
            }

            is AddCardActions.OnExpiryDateChange -> _state.update {
                val formattedExpiryDate = formatExpiryDate(action.value)
                it.copy(
                    addCardState = it.addCardState.copy(
                        expiryDate = formattedExpiryDate
                    )
                )
            }

            is AddCardActions.OnNameChange -> _state.update {
                it.copy(
                    addCardState = it.addCardState.copy(
                        name = action.value
                    )
                )
            }

            AddCardActions.OnPayNowButtonSwiped -> viewModelScope.launch {
                onAction(BillingDetailsActions.ToggleAddCardBottomSheet)
                eventChannel.send(
                    BillingDetailsEvent.Navigate(
                        route = ThankYouScreen
                    )
                )
            }

            is AddCardActions.OnCardNumberChangeFieldFocused -> _state.update { it.copy(
                addCardState = it.addCardState.copy(
                    cardNumberFocusedIndex = action.index
                )
            ) }
            is AddCardActions.OnEnterCardNumber -> enterNumber(action.digits, action.index)
            AddCardActions.OnKeyboardBack -> {
                val previousIndex = getPreviousFocusedIndex(
                    _state.value.addCardState.cardNumberFocusedIndex
                )
                _state.update { it.copy(
                    addCardState = it.addCardState.copy(
                        cardNumberFocusedIndex = previousIndex
                    )
                ) }
            }
        }
    }

    private fun formatExpiryDate(input: TextFieldValue): TextFieldValue {
        val digitsOnly = input.text.filter { it.isDigit() } // Extract only numeric characters
        val formattedText = StringBuilder()

        if (digitsOnly.isEmpty()) return TextFieldValue("", selection = TextRange(0))

        // Process the month (First 2 digits)
        val firstDigit = digitsOnly[0]
        if (firstDigit !in '0'..'1') return TextFieldValue("", selection = TextRange(0)) // Month should start with 0 or 1
        formattedText.append(firstDigit)

        if (digitsOnly.length >= 2) {
            val secondDigit = digitsOnly[1]
            val isValidSecondDigit =
                (firstDigit == '0' && secondDigit in '1'..'9') ||
                        (firstDigit == '1' && secondDigit in '0'..'2')

            if (!isValidSecondDigit) {
                return TextFieldValue(formattedText.toString(), selection = TextRange(formattedText.length))
            }

            formattedText.append(secondDigit)
        }

        // Append " / " after a valid month
        if (digitsOnly.length > 2) {
            formattedText.append(" / ")
        }

        // Process the year (Last 2 digits)
        if (digitsOnly.length >= 3) {
            val firstYearDigit = digitsOnly[2]
            if (firstYearDigit < '2') { // Year should be 25 or greater
                return TextFieldValue(formattedText.toString(), selection = TextRange(formattedText.length))
            }
            formattedText.append(firstYearDigit)

            if (digitsOnly.length >= 4) {
                val isThirdDigitValid = digitsOnly[2] != '2' || digitsOnly[3] >= '5'
                if (isThirdDigitValid) {
                    formattedText.append(digitsOnly[3])
                }
            }
        }

        return TextFieldValue(formattedText.toString(), selection = TextRange(formattedText.length))
    }

    /**
     * Number entered on Otp Field
     * **/
    private fun enterNumber(fourDigits: String, index: Int) {
        val newCardNumber = state.value.addCardState.cardNumber.mapIndexed { currentIndex, currentNumber ->
            if(currentIndex == index) {
                fourDigits
            } else {
                currentNumber
            }
        }
        val wasNumberRemoved = fourDigits.isEmpty()
        val isFieldComplete = fourDigits.length == 4 // Check if 4 digits are entered

        _state.update { it.copy(
            addCardState = it.addCardState.copy(
                cardNumber = newCardNumber,
                cardNumberFocusedIndex = when {
                    isFieldComplete && index < newCardNumber.size - 1 -> {
                        // Move to next field only if 4 digits entered
                        index + 1
                    }
                    wasNumberRemoved && index > 0 -> {
                        // Move focus back if user deletes all digits in a field
                        index - 1
                    }
                    else -> it.addCardState.cardNumberFocusedIndex // Keep current focus
                }
            )
        ) }
    }

    private fun getPreviousFocusedIndex(currentFocusedIndex: Int?): Int? {
        return currentFocusedIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun onSwipeComplete() {
        if (_swipeComplete.value) return // Prevent multiple triggers

        _swipeComplete.value = true
        viewModelScope.launch {
            _state.update {
                it.copy(
                    shouldShowAddCardBottomSheet = true
                )
            }

            delay(500) // Debounce for 500ms (adjust as needed)
            _swipeComplete.value = false // Reset after debounce time
        }
    }
}