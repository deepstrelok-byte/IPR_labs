package view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import animations.animated
import view.theme.colors.darkColorScheme
import view.theme.colors.lightColorScheme

@Composable
fun AppTheme(typography: Typography, content: @Composable () -> Unit) {

    val colors = if (isSystemInDarkTheme()) darkColorScheme
    else lightColorScheme

    MaterialExpressiveTheme(
        colorScheme = colors.animated(),
        motionScheme = MotionScheme.expressive(),
        typography = typography
    ) {
        content()
    }
}