package com.toquemedia.seedfy.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
val PrincipalColor = Color(0xFF633B48)
val TextPlaceholderColor = Color(0xFF888888)
val backgroundLightColor = Color(0xFFEEEEEE)
val backgroundDarkColor = Color(0xFF131111)
val cosmicParadiseGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF633B48),
        Color(0xFF9B3A6A),
        Color(0xFFD94A8A),
        Color(0xFFFFA3C4)
    ),
    start = androidx.compose.ui.geometry.Offset(0f, 0f),
    end = androidx.compose.ui.geometry.Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY) // Diagonal
)

