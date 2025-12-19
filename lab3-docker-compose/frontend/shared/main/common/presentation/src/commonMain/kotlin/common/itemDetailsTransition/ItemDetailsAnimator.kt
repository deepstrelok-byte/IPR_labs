package common.itemDetailsTransition

import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import itemDetails.ui.bottomSheet.SheetValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class ItemDetailsAnimator(
    val detailedItemKey: String?,
    val transition: Transition<SheetValue>,
    val seekableTransitionState: SeekableTransitionState<SheetValue>,
    val sheetState: AnchoredDraggableState<SheetValue>,
    val sheetHeight: Dp,
    val imageHeight: Dp,
    val sheetHeightPx: Float,
    val onBackClicked: () -> Unit,
    val coroutineScope: CoroutineScope,
    val pagerState: PagerState,
    val scrollState: ScrollState
) {
    var isInitialized = false
        private set

    var isBackGesture = false
        private set
    var isClosing = false
        private set


    // Никаких анимаций, всё стоит на месте в фулловом виде
    var isStableDetailed by mutableStateOf(false)
        private set

    fun onBackProgress(progress: Float) {

        coroutineScope.launch {
            isStableDetailed = false
            isBackGesture = true
            async {
                seekableTransitionState.seekTo(
                    progress,
                    targetState = SheetValue.Collapsed
                )
            }
            async(NonCancellable) {
                sheetState.anchoredDrag {
                    this.dragTo(progress * sheetHeightPx)
                }
            }
        }

    }

    fun onBackSuccessful(onlyAnimation: Boolean = false, onCompletion: () -> Unit = {}) {
        coroutineScope.launch {
            isStableDetailed = false
            isBackGesture = false
            isClosing = true
            val imageAnimation =
                async { seekableTransitionState.animateTo(SheetValue.Collapsed) }
            val sheetAnimation = async { sheetState.animateTo(SheetValue.Collapsed, tween(700)) }
// with await doesn't work
            imageAnimation.join()
            sheetAnimation.join()
            if (!onlyAnimation) {
                onBackClicked()
            }

        }.invokeOnCompletion {
            onCompletion()
        }
    }


    fun onBackFailure() {
        coroutineScope.launch {
            isBackGesture = false
            isStableDetailed = true
            async { seekableTransitionState.snapTo(SheetValue.Expanded) }
            async { sheetState.animateTo(SheetValue.Expanded) }
        }
    }

    fun animateOpen() {
        coroutineScope.launch {
            val imageAnimation = async { seekableTransitionState.animateTo(SheetValue.Expanded) }
            val sheetAnimation = async { sheetState.animateTo(SheetValue.Expanded) }
            imageAnimation.join()
            sheetAnimation.join()
            isInitialized = true
            isStableDetailed = true
        }
    }

    fun onSheetDrag(progress: () -> Float) {
        if (isInitialized && !isClosing && !isBackGesture) {
            coroutineScope.launch {
                val p = progress()
                seekableTransitionState.seekTo(p, SheetValue.Collapsed)
                isStableDetailed = p == 0f
                if (p == 1f) {
                    onBackClicked()
                }
            }
        }
    }
}