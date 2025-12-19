package itemDetails.ui.bottomSheet

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import utils.SpacerH
import view.consts.Paddings
import view.consts.Sizes

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DragHandle(
    startIntensity: Float,
    sheetState: AnchoredDraggableState<SheetValue>,
    dragInteractionSource: MutableInteractionSource,
    isStable: Boolean,
    pagerState: PagerState,
) {

    val transitionState = remember { MutableTransitionState(false) }
    val transition = rememberTransition(transitionState)

    var isPagerWasShown by remember { mutableStateOf(true) }

    LaunchedEffect(pagerState.targetPage, isStable) {
        if (!isStable) {
            transitionState.targetState = false
        } else if (isPagerWasShown || pagerState.targetPage != pagerState.currentPage) {
            transitionState.targetState = true
            isPagerWasShown = true
            delay(3000)
            isPagerWasShown = false
            transitionState.targetState = false
        }
    }

    Box(
        Modifier.padding(top = Paddings.semiMedium)
            .anchoredDraggable(
                sheetState,
                orientation = Orientation.Vertical,
                interactionSource = dragInteractionSource
            )
            .clickable(dragInteractionSource, null) {},

        ) {
        transition.Crossfade { isPager ->
            Box(Modifier.height(10.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                if (isPager) {
                    PagerDragHandle(pagerState)
                } else {
                    EmptyDragHandle(startIntensity)
                }
            }

        }
    }
}

@Composable
private fun PagerDragHandle(
    pagerState: PagerState
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(10.dp)) {
        for (page in 0 until pagerState.pageCount) {
            PagerPoint(page == pagerState.currentPage)
            if (page != pagerState.pageCount - 1) {
                SpacerH(Paddings.semiSmall)
            }
        }
    }
}

@Composable
private fun PagerPoint(
    isActive: Boolean
) {
    val size by animateDpAsState(if (isActive) 10.dp else 5.dp)
    val color by animateColorAsState(
        if (isActive) colorScheme.primaryFixed
        else colorScheme.onBackground.copy(
            alpha = .5f
        )
    )
    Box(
        Modifier
            .clip(CircleShape)
            .size(size)
            .background(color)
    )
}


@Composable
private fun EmptyDragHandle(
    startIntensity: Float
) {
    Box(
        Modifier.size(Sizes.dragHandleSize)
            .clip(shapes.medium)
            .background(
                colorScheme.onBackground.copy(
                    alpha = (startIntensity + .6f).coerceAtMost(1f)
                )
            )
    )
}