package com.umermahar.charityapp.food.presentation.billing_details

import androidx.compose.ui.text.input.TextFieldValue
import com.umermahar.charityapp.R
import com.umermahar.charityapp.food.presentation.food.models.Meal

data class BillingDetailsState(
    val meal: Meal? = null,
    val selectedPaymentType: PaymentType = PaymentType.CREDIT,
    val paymentTypes: List<PaymentTypeInfo> = listOf(
        PaymentTypeInfo(
            type = PaymentType.CREDIT,
            titleRes = R.string.credit_card,
        ),
        PaymentTypeInfo(
            type = PaymentType.PAYPAL,
            titleRes = R.string.paypal,
        ),
        PaymentTypeInfo(
            type = PaymentType.GOOGLE_PAY,
            titleRes = R.string.google_pay,
        ),
    ),
    val addCardState: AddCardState = AddCardState(),
    val shouldShowAddCardBottomSheet: Boolean = false,
)

data class AddCardState(
    val name: String = "",
    val expiryDate: TextFieldValue = TextFieldValue(),
    val cvv: String = "",
    val cardNumber: List<String> = (1..4).map { "" },
    val cardNumberFocusedIndex: Int? = null,
)

enum class PaymentType {
    CREDIT, PAYPAL, GOOGLE_PAY
}

data class PaymentTypeInfo(
    val type: PaymentType,
    val titleRes: Int,
)

sealed interface BillingDetailsActions {
    data object OnBackButtonClick : BillingDetailsActions
    data object OnCheckoutButtonSwiped : BillingDetailsActions
    data class OnPaymentTypeClick(val paymentType: PaymentType) : BillingDetailsActions
    data object ToggleAddCardBottomSheet : BillingDetailsActions
}

sealed interface AddCardActions {
    data object OnPayNowButtonSwiped : AddCardActions
    data class OnNameChange(val value: String) : AddCardActions
    data class OnExpiryDateChange(val value: TextFieldValue) : AddCardActions
    data class OnCvvChange(val value: String) : AddCardActions
    data class OnCardNumberChangeFieldFocused(val index: Int) : AddCardActions
    data class OnEnterCardNumber(val digits: String, val index: Int) : AddCardActions
    data object OnKeyboardBack : AddCardActions
}

sealed interface BillingDetailsEvent {
    data object PopBackStack : BillingDetailsEvent
    data class Navigate(val route: Any) : BillingDetailsEvent
}