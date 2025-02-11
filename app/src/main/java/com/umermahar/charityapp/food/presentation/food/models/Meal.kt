package com.umermahar.charityapp.food.presentation.food.models

import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    val id: Int,
    val count: Int,
    val pricePerDayUSD: Float,
)
