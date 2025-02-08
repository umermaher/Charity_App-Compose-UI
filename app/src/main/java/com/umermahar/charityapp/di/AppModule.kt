package com.umermahar.charityapp.di

import com.umermahar.charityapp.choose_category.ChooseCategoryViewModel
import com.umermahar.charityapp.food.FoodViewModel
import com.umermahar.charityapp.food_plan.FoodPlanViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::ChooseCategoryViewModel)
    viewModelOf(::FoodViewModel)
    viewModelOf(::FoodPlanViewModel)
}