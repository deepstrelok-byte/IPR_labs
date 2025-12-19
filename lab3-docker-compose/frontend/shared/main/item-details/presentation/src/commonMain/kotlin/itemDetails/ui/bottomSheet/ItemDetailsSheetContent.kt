package itemDetails.ui.bottomSheet

import alertsManager.AlertState
import alertsManager.AlertsManager
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import common.UserInfoSection
import common.itemDetailsTransition.LocalItemDetailsAnimator
import common.itemDetailsTransition.LocalTransitionHazeState
import itemDetails.components.ItemDetailsComponent
import itemDetails.ui.ItemDetailsDefaults
import itemDetails.ui.bottomSheet.sections.DetailedInfoSection
import itemDetails.ui.bottomSheet.sections.HugeButtonsSection
import itemDetails.ui.bottomSheet.sections.QuickInfoSection
import network.NetworkState
import utils.SpacerV
import view.consts.Paddings

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BoxScope.ItemDetailsSheetContent(
    sharedTransitionScope: SharedTransitionScope,
    component: ItemDetailsComponent
) {

    val safeContentBottomPadding =
        WindowInsets.safeContent.asPaddingValues().calculateBottomPadding()

    val hazeState = LocalTransitionHazeState.current
    val itemDetailsAnimator = LocalItemDetailsAnimator.current


    val density = LocalDensity.current

    val gapHeightPx = remember { with(density) { ItemDetailsDefaults.gapHeight.toPx() } }

    val recipientId by component.recipientId.collectAsState()

    val isOwner = component.isOwner
    val isRecipient = component.currentId == recipientId

    val itemQuickInfo by component.itemQuickInfo.collectAsState()


    val buttons = rememberButtons(
        isOwner = isOwner,
        recipientId = recipientId,
        component = component,
        closeSheet = { updateShareCare ->
            itemDetailsAnimator.onBackSuccessful(
                onCompletion = {
                    itemDetailsAnimator.onBackClicked()
                    updateShareCare()
                }
            )
        })



    CustomBottomSheet(
        modifier = with(sharedTransitionScope) {
            Modifier.align(Alignment.BottomCenter)
                .renderInSharedTransitionScopeOverlay(1f)
        },
        hazeState = hazeState,
        pagerState = itemDetailsAnimator.pagerState,
        sheetState = itemDetailsAnimator.sheetState,
        height = itemDetailsAnimator.sheetHeight,
        onDrag = { offset ->
            itemDetailsAnimator.onSheetDrag {
                val newBackProgress =
                    with(density) { (offset - gapHeightPx) / (itemDetailsAnimator.sheetHeightPx - gapHeightPx) }
                newBackProgress.coerceIn(0f, 1f)
            }
        },
        scrollState = itemDetailsAnimator.scrollState
    ) { topPadding ->
        Column(
            modifier = Modifier.padding(horizontal = Paddings.listHorizontalPadding)
                .verticalScroll(itemDetailsAnimator.scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SpacerV(topPadding)
            QuickInfoSection(
                title = component.title,
                location = component.location,
                category = component.category,
                sheetState = itemDetailsAnimator.sheetState
            )
            SpacerV(Paddings.semiMedium)

            HugeButtonsSection(buttons)

            DetailedInfoSection(
                deliveryTypes = component.deliveryTypes,
                description = component.description
            )

            SpacerV(Paddings.semiMedium)

            val data = itemQuickInfo.data

            UserInfoSection(
                onProfileClick = component::onProfileClick,
                onReportClick = { AlertsManager.push(AlertState.SnackBar("MVP")) },
                isMe = isOwner,
                isOwner = isOwner && recipientId != null,
                smallSectionTitlePadding = PaddingValues(start = Paddings.ultraSmall),
                isLoading = itemQuickInfo.isLoading(),
                error = (itemQuickInfo as? NetworkState.Error)?.prettyPrint,
                name = data?.opponentName ?: "",
                given = data?.opponentDonated ?: 0,
                taken = data?.opponentReceived ?: 0,
                organizationName = data?.opponentOrganizationName,
                isVerified = data?.opponentIsVerified == true,
                onErrorClick = { component.fetchItemQuickInfo() }
            )


            SpacerV(safeContentBottomPadding + Paddings.small)
        }
    }
}