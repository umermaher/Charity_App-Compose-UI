package com.umermahar.charityapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umermahar.charityapp.R

val bodyFontFamily = FontFamily(
    Font(
        resId = R.font.poppins_regular,
        weight = FontWeight.Normal
    )
)

val displayFontFamily = FontFamily(
    Font(
        resId = R.font.poppins_regular,
        weight = FontWeight.Bold
    )
)


// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
    displayMedium = baseline.displayMedium.copy(
        fontFamily = FontFamily(
            Font(
                resId = R.font.philosopher_bold,
            )
        ),
        fontSize = 40.sp
    ),
    displaySmall = baseline.displaySmall.copy(
        fontFamily = FontFamily(
            Font(
                resId = R.font.philosopher_regular,

            )
        ),
        fontSize = 36.sp
    ),
    headlineLarge = baseline.headlineLarge.copy(
        fontFamily = FontFamily(
            Font(
                resId = R.font.philosopher_bold,
            )
        )
    ),
    headlineMedium = baseline.headlineMedium.copy(
        fontFamily = FontFamily(
            Font(
                resId = R.font.philosopher_bold,
            )
        ),
        fontSize = 30.sp
    ),
    headlineSmall = baseline.headlineSmall.copy(
        fontFamily = FontFamily(
            Font(
                resId = R.font.philosopher_bold,
            )
        ),
        color = onSurfaceLight
    ),
    titleLarge = baseline.titleLarge.copy(
        fontFamily = FontFamily(
            Font(
                resId = R.font.poppins_semibold,
            )
        ),
        fontSize = 20.sp
    ),
    titleMedium = baseline.titleMedium.copy(
        fontFamily = FontFamily(
            Font(
                resId = R.font.poppins_bold,
            )
        ),
    ),
    titleSmall = baseline.titleSmall.copy(
        fontFamily = FontFamily(
            Font(
                resId = R.font.poppins_semibold,
            )
        ),
    ),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
    bodyMedium = baseline.bodyMedium.copy(
        fontFamily = FontFamily(
            Font(
                resId = R.font.poppins_medium,
            )
        ),
        lineHeight = 20.sp
    ),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
)