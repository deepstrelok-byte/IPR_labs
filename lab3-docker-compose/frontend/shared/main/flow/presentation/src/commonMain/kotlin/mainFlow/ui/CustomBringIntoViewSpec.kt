package mainFlow.ui

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.BringIntoViewSpec


class CustomBringIntoViewSpec(
    private val topShadowWholePaddingPx: Float,
    private  val bottomShadowWholePaddingPx: Float
) : BringIntoViewSpec {
    @Deprecated("Animation spec customization is no longer supported.")
    override val scrollAnimationSpec: AnimationSpec<Float>
        get() = spring(stiffness = Spring.StiffnessVeryLow)

    override fun calculateScrollDistance(
        offset: Float,
        size: Float,
        containerSize: Float
    ): Float {
        val top = topShadowWholePaddingPx
        val bottom = containerSize - bottomShadowWholePaddingPx

        val finalOffset = if (offset + size > bottom) {
            bottom - size
        } else if (offset < top) {
            top
        } else {
            offset
        }
        return offset - finalOffset
    }
}