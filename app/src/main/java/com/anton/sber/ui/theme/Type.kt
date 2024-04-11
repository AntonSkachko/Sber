package com.anton.sber.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.anton.sber.R

// Set of Material typography styles to start with
val Typography: Typography
    get() = Typography(
        displayLarge = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        displayMedium = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = Roboto,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp
        )
    )

val Roboto = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_regular),
    Font(R.font.roboto_light)
)