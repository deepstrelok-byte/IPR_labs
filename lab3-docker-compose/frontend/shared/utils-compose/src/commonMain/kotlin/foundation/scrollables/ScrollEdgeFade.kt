package foundation.scrollables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ScrollEdgeShadowHeight {
    val small = 80.dp
    val big = 110.dp
}

@Composable
fun BottomScrollEdgeFade(
    modifier: Modifier = Modifier,
    solidHeight: Dp = 0.dp,
    shadowHeight: Dp = ScrollEdgeShadowHeight.small,
    isVisible: Boolean = true
) = ScrollEdgeFade(
    modifier = modifier,
    solidHeight = solidHeight,
    isVisible = isVisible,
    shadowHeight = shadowHeight,
    isReversed = true
)

@Composable
fun ScrollEdgeFade(
    modifier: Modifier = Modifier,
    solidHeight: Dp = 0.dp,
    shadowHeight: Dp = ScrollEdgeShadowHeight.big,
    isVisible: Boolean = true,
    isReversed: Boolean = false,
    color: Color = colorScheme.background
) {
    val density = LocalDensity.current
    val (solidHeightPx, shadowHeightPx) = with(density) {
        listOf(solidHeight.toPx(), shadowHeight.toPx())
    }

    AnimatedVisibility(
        modifier = modifier, visible = isVisible,
        enter = fadeIn(tween(600)),
        exit = fadeOut(tween(800))
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(solidHeight + shadowHeight)
        ) {
            val totalHeight = size.height

            drawRect(
                brush = Brush.verticalGradient(
                    startY = if (isReversed) totalHeight else 0f,
                    endY = if (isReversed) 0f else totalHeight,
                    colorStops =
                        arrayOf(
                            0f to color,
                            (solidHeightPx / totalHeight) to color.copy(.9f),
                            ((solidHeightPx + shadowHeightPx / 2) / totalHeight) to color.copy(.5f),
                            1f to Transparent
                        )
                ),
                topLeft = Offset.Zero,
                size = size
            )
        }
    }
}