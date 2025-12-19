package requestDetails.components

import androidx.compose.foundation.text.input.TextFieldState
import common.detailsInterfaces.DetailsComponent
import entity.ItemQuickInfo
import kotlinx.coroutines.flow.StateFlow
import logic.enums.DeliveryType
import logic.enums.ItemCategory
import network.NetworkState

interface RequestDetailsComponent: DetailsComponent {

    fun onProfileClick()
    val requestQuickInfo: StateFlow<NetworkState<ItemQuickInfo>>
    fun fetchRequestQuickInfo()

    val onBackClick: () -> Unit

    val onAcceptClick: (NetworkState<ItemQuickInfo>) -> Unit

    val isEditable: Boolean
    val isCreating: Boolean

    val location: String

    val initialText: String
    val initialCategory: ItemCategory?
    val initialDeliveryTypes: List<DeliveryType>

    val requestText: TextFieldState
    val category: StateFlow<ItemCategory?>
    val deliveryTypes: StateFlow<List<DeliveryType>>

    val createOrEditRequestResult: StateFlow<NetworkState<Unit>>
    val deleteRequestResult: StateFlow<NetworkState<Unit>>

    fun updateDeliveryType(deliveryType: DeliveryType)
    fun updateCategory(category: ItemCategory)

    fun createOrEditRequest()
    fun deleteRequest()

}