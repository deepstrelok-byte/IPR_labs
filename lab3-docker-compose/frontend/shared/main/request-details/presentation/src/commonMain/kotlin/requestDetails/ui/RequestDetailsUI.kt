package requestDetails.ui

import alertsManager.AlertState
import alertsManager.AlertsManager
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import common.UserInfoSection
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import foundation.DefaultDialog
import network.NetworkState
import requestDetails.components.RequestDetailsComponent
import requestDetails.ui.sections.ButtonSection
import requestDetails.ui.sections.DeliveryTypesSection
import requestDetails.ui.sections.TextFieldsSection
import utils.SpacerV
import view.consts.Paddings
import widgets.sections.LocationSection

@OptIn(
    ExperimentalHazeMaterialsApi::class,
    ExperimentalSharedTransitionApi::class, ExperimentalComposeUiApi::class
)//, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.RequestDetailsUI(
    component: RequestDetailsComponent
) {

    val category by component.category.collectAsState()
    val deliveryTypes by component.deliveryTypes.collectAsState()

    val createRequestResult by component.createOrEditRequestResult.collectAsState()
    val deleteRequestResult by component.deleteRequestResult.collectAsState()

    val isEditing = !component.isCreating

    val dismissable = !createRequestResult.isLoading() || !deleteRequestResult.isLoading()

    val requestQuickInfo by component.requestQuickInfo.collectAsState()

    DefaultDialog(
        onDismissRequest = component.onBackClick,
        dismissable = dismissable
    ) {
        Column(
            Modifier
                .padding(top = Paddings.semiMedium, bottom = Paddings.medium)
                .verticalScroll(rememberScrollState())
        ) {
            TextFieldsSection(
                requestTextState = component.requestText,
                category = category,
                isCreationMode = component.isEditable,
                isLoading = createRequestResult.isLoading()
            ) { component.updateCategory(it) }
            SpacerV(Paddings.semiMedium)

            DeliveryTypesSection(
                deliveryTypes = deliveryTypes,
                isEditable = component.isEditable
            ) { component.updateDeliveryType(it) }

            SpacerV(Paddings.semiMedium)
            LocationSection(modifier = Modifier.fillMaxWidth(), location = component.location)


            SpacerV(Paddings.semiMedium)
            ButtonSection(
                isEditable = component.isEditable,
                isEditing = isEditing,
                requestText = component.requestText.text.toString(),
                category = category,
                deliveryTypes = deliveryTypes,
                initialText = component.initialText,
                initialDeliveryTypes = component.initialDeliveryTypes,
                initialCategory = component.initialCategory,
                isLoading = createRequestResult.isLoading(),
                createOrEditRequest = component::createOrEditRequest,
                onAcceptClick = { component.onAcceptClick(requestQuickInfo) },
                onDeleteClick = component::deleteRequest,
                isDeleteLoading = deleteRequestResult.isLoading()
            )

            if (!component.isEditable) {
                SpacerV(Paddings.semiMedium)
                Column(
                    Modifier.fillMaxWidth().padding(horizontal = Paddings.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val data = requestQuickInfo.data

                    UserInfoSection(
                        isMe = component.isEditable,
                        onProfileClick = component::onProfileClick,
                        onReportClick = { AlertsManager.push(AlertState.SnackBar("MVP")) },
                        isOwner = true,
                        isLoading = requestQuickInfo.isLoading(),
                        error = (requestQuickInfo as? NetworkState.Error)?.prettyPrint,
                        name = data?.opponentName ?: "",
                        given = data?.opponentDonated ?: 0,
                        taken = data?.opponentReceived ?: 0,
                        organizationName = data?.opponentOrganizationName,
                        isVerified = data?.opponentIsVerified == true,
                        onErrorClick = { component.fetchRequestQuickInfo() }
                    )
                }
            }
        }
    }

}