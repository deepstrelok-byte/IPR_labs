package common.itemCard

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import common.itemDetailsTransition.LocalTransitionHazeState
import dev.chrisbanes.haze.hazeSource
import foundation.AsyncImage
import network.getImageLink

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ItemImage(
    imagePath: String,
    modifier: Modifier,
    key: String?,
    animatedContentScope: AnimatedContentScope?,
    detailedItemKey: String?
) {

    val imageLink = getImageLink(imagePath)
    val isAnimating = key != null && detailedItemKey == key
    val hazeState = if (isAnimating) LocalTransitionHazeState.current else null

    val isSharedElement = animatedContentScope != null && key != null

    AsyncImage(
        link = imageLink,
        modifier = modifier
            .then(
                if (isSharedElement)
                    Modifier.sharedElement(
                        rememberSharedContentState(
                            key
                        ),
                        animatedVisibilityScope = animatedContentScope,
                        renderInOverlayDuringTransition = isAnimating

                    )
                else Modifier
            )
            .clip(ItemCardDefaults.imageShape)
            .then(
                if (hazeState != null) Modifier.hazeSource(hazeState)
                else Modifier
            ),
        contentDescription = null, // TODO
        key = key
    )
}