package widgets.glass

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.Dp
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeInputScale
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import view.consts.Paddings


object GlassCardFunctions {
    @Suppress("ComposableNaming")
    @Composable
    fun getContentColor(
        tint: Color? = null
    ) : Color {
        return tint?.let { contentColorFor(lerp(tint, colorScheme.background, .5f)) }
            ?: colorScheme.onBackground
    }


    fun getHazeTintColor(tint: Color?, containerColor: Color, containerColorAlpha: Float = .4f): Color {
        return tint ?: containerColor.copy(containerColorAlpha)
    }
    fun getBorderColor(tint: Color?, tintColorAlpha: Float = .6f, containerColor: Color, containerColorAlpha: Float = .4f): Color {
        return tint?.copy(alpha = tintColorAlpha) ?: containerColor.copy(alpha = containerColorAlpha)
    }
}

@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalHazeApi::class)
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    shape: Shape = shapes.extraLarge,
    tint: Color? = null,
    hazeTint: Color = GlassCardFunctions.getHazeTintColor(tint, containerColor = colorScheme.primaryContainer),
    borderColor: Color = GlassCardFunctions.getBorderColor(tint, containerColor = colorScheme.primaryContainer),
    contentColor: Color = GlassCardFunctions.getContentColor(tint),
    contentPadding: PaddingValues = PaddingValues(Paddings.medium),
    isReversedProgressive: Boolean = false,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit
) {


    Surface(
        modifier = Modifier.clip(shape).then(modifier).clip(shape)
            .hazeEffect(hazeState, HazeMaterials.ultraThick(colorScheme.background)) {
                this.noiseFactor = 0f
                this.tints =
                        listOf(HazeTint(hazeTint))

                this.inputScale = HazeInputScale.Fixed(.4f)
                // TODO: what about to remove progressive?
                this.progressive =
                    HazeProgressive.verticalGradient(
                        startIntensity = if (isReversedProgressive) .8f else .5f,
                        endIntensity = if (isReversedProgressive) .5f else .8f
                    )

            },
        shape = shape,
        color = Color.Transparent,
        contentColor = contentColor,
        border = BorderStroke(
            Dp.Hairline,
            color = borderColor
        )
    ) {
        Box(Modifier.padding(contentPadding), contentAlignment = contentAlignment) {

            content()
        }
    }
}