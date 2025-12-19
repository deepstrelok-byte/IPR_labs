package mainFlow.ui

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.dismiss
import common.itemDetailsTransition.LocalItemDetailsAnimator
import common.itemDetailsTransition.LocalTransitionHazeState
import common.itemDetailsTransition.rememberItemDetailsAnimator
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import itemDetails.components.ItemDetailsComponent
import itemDetails.ui.ItemDetailsUI
import mainFlow.components.MainFlowComponent
import requestDetails.components.RequestDetailsComponent
import requestDetails.ui.RequestDetailsUI

@Composable
fun SharedTransitionScope.MainFlowScreen(
    component: MainFlowComponent
) {
    val detailsSlot by component.detailsSlot.subscribeAsState()
    val details = detailsSlot.child?.instance

    val itemDetailsAnimator = rememberItemDetailsAnimator(
        detailedItemId = details?.id,
        detailedItemKey = details?.key,
        imagesCount = if (details is ItemDetailsComponent) details.images.size else 0
    ) {
        // иначе он был уже удалён
        // получаем из itemDetailsSlot, т.к. находимся в remember (старое значение)
        if (it == detailsSlot.child?.instance?.id) {
            component.detailsNav.dismiss()
        }
    }


    val hazeState = rememberHazeState() //outHazeState
    CompositionLocalProvider(
        LocalTransitionHazeState provides hazeState,
        LocalItemDetailsAnimator provides itemDetailsAnimator
    ) {

        MainFlowContent(
            component = component,
            modifier = Modifier.hazeSource(hazeState)
        )
        if (details != null) {
            when (details) {
                is ItemDetailsComponent ->
                    ItemDetailsUI(
                        details
                    )

                is RequestDetailsComponent ->
                    RequestDetailsUI(details)
            }
        }

    }
}