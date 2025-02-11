package com.umermahar.charityapp.food.presentation.billing_details

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
    val shouldShowAddCardBottomSheet: Boolean = false
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
    data object OnCheckoutSwiped : BillingDetailsActions
    data class OnPaymentTypeClick(val paymentType: PaymentType) : BillingDetailsActions
}

sealed interface BillingDetailsEvent {
    data object PopBackStack : BillingDetailsEvent
    data class Navigate(val route: Any) : BillingDetailsEvent
}