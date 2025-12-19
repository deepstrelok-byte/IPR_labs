package utils

import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.basicMarquee
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color


fun Modifier.fastBackground(color: Color) = this.drawBehind {
    drawRect(color)
}


fun Modifier.defaultMarquee(enabled: Boolean = true) = this.basicMarquee(
    iterations = if (enabled) Int.MAX_VALUE else 0,
    animationMode = MarqueeAnimationMode.Immediately,
    repeatDelayMillis = 3000,
    spacing = MarqueeSpacing.fractionOfContainer(.1f)
)