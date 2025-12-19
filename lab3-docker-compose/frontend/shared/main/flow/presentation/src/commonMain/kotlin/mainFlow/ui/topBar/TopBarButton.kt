package mainFlow.ui.topBar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalFocusManager
import dev.chrisbanes.haze.HazeState
import widgets.glass.GlassCard

@Composable
internal fun TopBarButton(
    hazeState: HazeState,
    isCloseButton: Boolean,
    onClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    GlassCard(
        modifier = Modifier.aspectRatio(1f).clickable {
            focusManager.clearFocus()
            onClick()
        },
        hazeState = hazeState,
        shape = CircleShape,
        isReversedProgressive = true,
        tint = colorScheme.tertiaryContainer.copy(alpha = .3f),
        borderColor = colorScheme.tertiaryContainer.copy(alpha = .15f)
    ) {
        Box(Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
            AnimatedContent(
                if (isCloseButton) Icons.Rounded.Close
                else Icons.Rounded.Person,
                modifier = Modifier.matchParentSize().scale(1.2f),
                transitionSpec = {
                    (fadeIn(animationSpec = tween(220)) +
                            scaleIn(
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessLow
                                )
                            ))
                        .togetherWith(
                            fadeOut(animationSpec = tween(220)) +
                                    scaleOut(
                                        animationSpec = spring(
                                            stiffness = Spring.StiffnessLow
                                        )
                                    )
                        )
                }
            ) { icon ->
                Icon(
                    icon,
                    contentDescription = null
                )
            }
        }
    }
}