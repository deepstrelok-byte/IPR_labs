package common.itemDetailsTransition

import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import itemDetails.ui.ItemDetailsDefaults
import itemDetails.ui.bottomSheet.SheetValue
import itemDetails.ui.bottomSheet.getCustomSheetState

@Suppress("ComposableNaming")
@Composable
fun rememberItemDetailsAnimator(
    detailedItemId: String?,
    detailedItemKey: String?,
    imagesCount: Int,
    onBackClicked: (String?) -> Unit
): ItemDetailsAnimator {
    val density = LocalDensity.current

    val containerSize = LocalWindowInfo.current.containerSize
    val imageHeightPx = remember(containerSize.width) {
        (containerSize.width * ItemDetailsDefaults.aspectRatio).coerceAtMost(containerSize.height * .66f)
    }
    val topPaddingPx = WindowInsets.safeContent.getTop(density)
    val sheetHeightPx =
        remember(
            imageHeightPx,
            topPaddingPx
        ) { containerSize.height - imageHeightPx - topPaddingPx + with(density) { ItemDetailsDefaults.gapHeight.roundToPx() } }


    val sheetHeight = remember(sheetHeightPx) { with(density) { sheetHeightPx.toDp() } }
    val imageHeight = remember(imageHeightPx) { with(density) { imageHeightPx.toDp() } }

    val seekableTransitionState = remember(detailedItemId) {
        SeekableTransitionState(SheetValue.Collapsed)
    }
    val transition = rememberTransition(transitionState = seekableTransitionState)
    val coroutineScope = rememberCoroutineScope()


    return remember(detailedItemId) {
        val manager = ItemDetailsAnimator(
            detailedItemKey = detailedItemKey,
            transition = transition,
            seekableTransitionState = seekableTransitionState,
            sheetState = getCustomSheetState(heightPx = sheetHeightPx),
            sheetHeight = sheetHeight,
            sheetHeightPx = sheetHeightPx,
            coroutineScope = coroutineScope,
            imageHeight = imageHeight,
            onBackClicked = { onBackClicked(detailedItemId) },
            pagerState = PagerState { imagesCount },
            scrollState = ScrollState(0)
        )
        manager.animateOpen()
        manager
    }
}