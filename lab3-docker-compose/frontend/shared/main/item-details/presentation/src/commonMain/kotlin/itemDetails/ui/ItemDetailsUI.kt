package itemDetails.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.PredictiveBackHandler
import common.itemDetailsTransition.LocalItemDetailsAnimator
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import itemDetails.components.ItemDetailsComponent
import itemDetails.ui.bottomSheet.ItemDetailsSheetContent
import itemDetails.ui.detailsContent.ItemDetailsContent
import kotlin.coroutines.cancellation.CancellationException


@OptIn(
    ExperimentalHazeMaterialsApi::class,
    ExperimentalSharedTransitionApi::class, ExperimentalComposeUiApi::class
)//, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ItemDetailsUI(
    component: ItemDetailsComponent
) {
    val itemDetailsAnimator = LocalItemDetailsAnimator.current

    PredictiveBackHandler { progress ->
        try {
            progress.collect { backEvent ->
                runCatching {
                    itemDetailsAnimator.onBackProgress(backEvent.progress)
                }
            }
            itemDetailsAnimator.onBackSuccessful()
        } catch (e: CancellationException) {
            itemDetailsAnimator.onBackFailure()
            throw e
        }
    }
    ItemDetailsContent(
        component = component,
        sheet = {
            ItemDetailsSheetContent(
                sharedTransitionScope = this@ItemDetailsUI,
                component = component
            )
        }
    )

}