package com.umermahar.charityapp

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.umermahar.charityapp.food.presentation.food.models.Meal
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {

    val mealType = object: NavType<Meal>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): Meal? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Meal {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Meal): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Meal) {
            bundle.putString(key, Json.encodeToString(value))
        }

    }
}