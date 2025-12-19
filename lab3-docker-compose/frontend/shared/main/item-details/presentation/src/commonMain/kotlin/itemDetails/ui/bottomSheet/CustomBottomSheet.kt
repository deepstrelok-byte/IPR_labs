package itemDetails.ui.bottomSheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeInputScale
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import itemDetails.ui.ItemDetailsDefaults
import platform.Platform
import platform.currentPlatform
import utils.SpacerV
import view.consts.Paddings


@OptIn(
    ExperimentalHazeMaterialsApi::class, ExperimentalHazeApi::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun CustomBottomSheet(
    sheetState: AnchoredDraggableState<SheetValue>,
    scrollState: ScrollState,
    height: Dp,
    pagerState: PagerState,
    hazeState: HazeState,
    modifier: Modifier,
    onDrag: (Float) -> Unit,
    content: @Composable ColumnScope.(Dp) -> Unit
) {
    val offset = sheetState.requireOffset()

    val isDarkTheme = isSystemInDarkTheme()
    val density = LocalDensity.current


    val dragInteractionSource = remember { MutableInteractionSource() }
    val dragPressed = dragInteractionSource.collectIsPressedAsState().value
    val dragDragged = dragInteractionSource.collectIsDraggedAsState().value


    val isScrolled = scrollState.canScrollBackward

    val endIntensity = if (isDarkTheme) .8f else .7f
    val hazeAnimationSpec = remember { tween<Float>(700) }
    val startIntensity by animateFloatAsState(
        if (dragPressed || isScrolled) endIntensity - .45f
        else if (offset != 0f || dragDragged) endIntensity
        else .0f,
        animationSpec = hazeAnimationSpec
    )
    val adaptiveInputScale by animateFloatAsState(
        if (offset != 0f || dragDragged) {
            if (currentPlatform == Platform.IOS) .5f
            else .3f
        } else .8f,
        animationSpec = hazeAnimationSpec
    )
    val progressiveEndY =
        with(density) { ItemDetailsDefaults.gapHeight.roundToPx() } * adaptiveInputScale

    LaunchedEffect(offset) {
        onDrag(offset)
    }
    Box(modifier
        .height(height)
        .fillMaxWidth()
        .offset {
            IntOffset(y = offset.toInt(), x = 0)
        }) {
        Column(
            modifier = Modifier.fillMaxSize()
                .pointerInput(Unit) {} // Prohibit Background scrolling
                .clip(shapes.extraExtraLarge)
                // HAZE
                .hazeEffect(
                    state = hazeState,
                    style = HazeMaterials.thick(colorScheme.background)
                ) {
                    progressive = HazeProgressive.verticalGradient(
                        endY = progressiveEndY,  // remove fade line, when change inputScale
                        startIntensity = startIntensity,
                        endIntensity = endIntensity,
                        preferPerformance = false
                    )
                    inputScale = HazeInputScale.Fixed(adaptiveInputScale)
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content(Paddings.semiSmall + 10.dp + Paddings.semiMedium)
        }
        Column {
            AnimatedVisibility(!isScrolled) {
                SpacerV(Paddings.semiSmall)
                DragHandle(
                    startIntensity = startIntensity,
                    sheetState = sheetState,
                    dragInteractionSource = dragInteractionSource,
                    pagerState = pagerState,
                    isStable = offset == 0f && !dragPressed && !dragDragged,
                )
            }
        }
    }
}