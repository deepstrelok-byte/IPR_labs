package widgets.glass

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.chrisbanes.haze.HazeState
import view.consts.Paddings

@Composable
fun BackGlassButton(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = modifier
            .clickable {
                onClick()
            },
        hazeState = hazeState,
        shape = CircleShape,
        contentPadding = PaddingValues(Paddings.semiMedium),
        contentAlignment = Alignment.Center,
        tint = Color.Transparent,
        hazeTint = GlassCardFunctions.getHazeTintColor(
            tint = null,
            containerColor = colorScheme.primaryContainer,
            containerColorAlpha = .4f
        ),
        borderColor = GlassCardFunctions.getBorderColor(
            tint = null,
            containerColor = colorScheme.primaryContainer,
            containerColorAlpha = .1f
        ),
        contentColor = Color.White
    ) {
        Icon(Icons.AutoMirrored.Rounded.ArrowBackIos, contentDescription = null)
    }
}