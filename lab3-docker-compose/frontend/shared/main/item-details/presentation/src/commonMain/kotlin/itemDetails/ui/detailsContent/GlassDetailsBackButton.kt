package itemDetails.ui.detailsContent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.HazeState
import widgets.glass.BackGlassButton

@Composable
internal fun GlassDetailsBackButton(
    isVisible: Boolean,
    modifier: Modifier,
    hazeState: HazeState,
    onClick: () -> Unit
) {

    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier,
        enter = fadeIn() + scaleIn(
            spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = Spring.DampingRatioMediumBouncy
            )
        ),
        exit = fadeOut() + scaleOut()
    ) {
        BackGlassButton(
            hazeState = hazeState,
            onClick = onClick
        )
    }
}