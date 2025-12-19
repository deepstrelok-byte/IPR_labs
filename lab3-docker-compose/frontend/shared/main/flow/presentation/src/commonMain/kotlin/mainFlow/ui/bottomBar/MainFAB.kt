package mainFlow.ui.bottomBar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Handshake
import androidx.compose.material.icons.rounded.LibraryAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import careshare.shared.main.flow.presentation.generated.resources.Res
import careshare.shared.main.flow.presentation.generated.resources.fab_find_help
import careshare.shared.main.flow.presentation.generated.resources.fab_share_care
import dev.chrisbanes.haze.HazeState
import foundation.ShapeByInteractionDefaults
import foundation.shapeByInteraction
import kotlinx.coroutines.delay
import utils.SpacerH
import utils.value
import view.consts.Paddings
import widgets.glass.GlassCard

@Composable
internal fun MainFAB(
    hazeState: HazeState,
    isVerified: Boolean,
    isFindHelp: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var isAnimating by remember { mutableStateOf(false) }


    LaunchedEffect(isFindHelp) {
        isAnimating = true
        delay(400)
        isAnimating = false
    }

    val isDarkTheme = isSystemInDarkTheme()

    val animatedTint by animateColorAsState(
        (if (isFindHelp) {
            colorScheme.secondaryContainer
        } else colorScheme.tertiaryContainer
                )
            .copy(
                alpha = if (isFindHelp && !isVerified) .1f else {
                    if (isDarkTheme) .7f else .45f
                }
            ),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    val shape by shapeByInteraction(
        isPressed = isPressed,
        shapeDp = animateDpAsState(
            if (isAnimating) ShapeByInteractionDefaults.largeIncreasedDpShape else ShapeByInteractionDefaults.extraLargeDpShape,
            animationSpec = ShapeByInteractionDefaults.shapeAnimationSpec
        ).value
    )
    GlassCard(
        hazeState = hazeState,
        tint = animatedTint,
//        contentColor = Color.White,
        shape = RoundedCornerShape(shape),
        modifier = Modifier.clickable(interactionSource = interactionSource) {
            onClick()
        }
    ) {

        AnimatedContent(
            isFindHelp,
            transitionSpec = { fadeIn().togetherWith(fadeOut()) }
        ) { isFindHelpMode ->
            var shouldShowIcon: Boolean? by remember { mutableStateOf(null) }
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (shouldShowIcon != false) {
                    Icon(
                        if (isFindHelpMode) Icons.Rounded.LibraryAdd else Icons.Rounded.Handshake,
                        contentDescription = null
                    )

                    SpacerH(Paddings.semiSmall)
                }

                Text(
                    (if (isFindHelpMode) Res.string.fab_find_help else Res.string.fab_share_care).value,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    autoSize = TextAutoSize.StepBased(
                        maxFontSize = typography.titleMedium.fontSize
                    ),
                    onTextLayout = { textLayout ->
                        if (shouldShowIcon == null) {
                            shouldShowIcon = !textLayout.hasVisualOverflow
                        }
                    },
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }


}
