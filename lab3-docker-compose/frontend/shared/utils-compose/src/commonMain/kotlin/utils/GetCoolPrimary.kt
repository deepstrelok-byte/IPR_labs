package utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

@Composable
fun getCoolPrimary(fraction: Float = .6f): Color {
    val isDark = isSystemInDarkTheme()
    val stopColor = if (isDark) colorScheme.onBackground else colorScheme.background

    return lerp(colorScheme.primaryContainer, stopColor, fraction)
}