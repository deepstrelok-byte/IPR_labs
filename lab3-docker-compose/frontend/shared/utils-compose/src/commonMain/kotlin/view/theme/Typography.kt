package view.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle

@Composable
fun scaledTypography(scale: Float) =  with(typography) {
    val scale by animateFloatAsState(scale)
    this.copy(
        displayLarge = displayLarge.scale(scale),
        displayMedium = displayMedium.scale(scale),
        displaySmall = displaySmall.scale(scale),
        headlineLarge = headlineLarge.scale(scale),
        headlineMedium = headlineMedium.scale(scale),
        headlineSmall = headlineSmall.scale(scale),
        titleLarge = titleLarge.scale(scale),
        titleMedium = titleMedium.scale(scale),
        titleSmall = titleSmall.scale(scale),
        bodyLarge = bodyLarge.scale(scale),
        bodyMedium = bodyMedium.scale(scale),
        bodySmall = bodySmall.scale(scale),
        labelLarge = labelLarge.scale(scale),
        labelMedium = labelMedium.scale(scale),
        labelSmall = labelSmall.scale(scale),
    )
}

private fun TextStyle.scale(scale: Float) = this.copy(fontSize = this.fontSize * scale, lineHeight = this.lineHeight * scale)