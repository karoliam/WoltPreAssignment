package com.karoliinamultas.woltpreassignment.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.karoliinamultas.woltpreassignment.R

// Omnes
val omnesFont = FontFamily(
    Font(R.font.omnes_bold)
)

// Roboto
val robotoFont = FontFamily(
    Font(R.font.roboto_bold, FontWeight.W700),
    Font(R.font.roboto_regular, FontWeight.W400)
)

// Typography
val Typography = Typography(
    titleSmall = TextStyle(
        fontFamily = robotoFont,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.13.sp
    ),
    titleMedium = TextStyle(
        fontFamily = omnesFont,
        fontSize = 24.sp,
        lineHeight = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily = omnesFont,
        fontSize = 32.sp,
        lineHeight = 36.sp
    ),
    bodySmall = TextStyle(
        fontFamily = robotoFont,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    )
)

