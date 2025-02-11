package com.umermahar.charityapp.di

import com.umermahar.charityapp.food.presentation.billing_details.BillingDetailsViewModel
import com.umermahar.charityapp.food.presentation.choose_category.ChooseCategoryViewModel
import com.umermahar.charityapp.food.presentation.food.FoodViewModel
import com.umermahar.charityapp.food.presentation.food_plan.FoodPlanViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::ChooseCategoryViewModel)
    viewModelOf(::FoodViewModel)
    viewModelOf(::FoodPlanViewModel)
    viewModelOf(::BillingDetailsViewModel)
}